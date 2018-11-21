package com.example.ramapradana.keep;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ramapradana.keep.data.local.database.DatabaseHelper;
import com.example.ramapradana.keep.data.remote.model.EventsItem;
import com.example.ramapradana.keep.data.remote.model.EventsResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends android.support.v4.app.Fragment {
    private Call<EventsResponse> call;
    private RecyclerView rvEvent;
    private EventsAdapter eventAdapter;
    private SwipeRefreshLayout srEvent;
    private DatabaseHelper db;
    private View view;
    private List<EventsItem> eventsItemList = new ArrayList<>();

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), "Product Sans Regular.ttf", true);

        view = inflater.inflate(R.layout.fragment_event, container, false);
        rvEvent = view.findViewById(R.id.rv_event_item);
        srEvent = view.findViewById(R.id.sr_event);

        db = new DatabaseHelper(view.getContext());

        eventAdapter = new EventsAdapter(getFragmentManager(), getContext());

        if (this.isNetworkConnected()){
            this.loadEvent();
        }else{
            this.loadFromLocal();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rvEvent.setNestedScrollingEnabled(false);
        rvEvent.setLayoutManager(linearLayoutManager);
        rvEvent.setAdapter(eventAdapter);

        srEvent.setOnRefreshListener(() -> {
            eventsItemList = null;
            if (this.isNetworkConnected()){
                this.loadEvent();
            }else{
                this.loadFromLocal();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fabCreate = getView().findViewById(R.id.fab_create);
        fabCreate.setOnClickListener((v) -> {
            CreateEventDialog createEventDialog = new CreateEventDialog();
            createEventDialog.setTargetFragment(this, 1);
            createEventDialog.show(getFragmentManager(), "create event");
        });
    }

    public void loadEvent(){
        srEvent.setRefreshing(true);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");

        call = KeepApiClient.getKeepApiService().getEvent(token);
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().isStatus()){
                        db.deleteAllEventAndDetail("event");
                        eventsItemList = response.body().getEvents();
                        int eventCount = eventsItemList.size();

                        boolean inserted;
                        int unSync = 0;
                        for(int i = 0; i < eventCount; i++){
                            inserted = db.insertEvent(
                                    eventsItemList.get(i).getEventId(),
                                    eventsItemList.get(i).getEventName(),
                                    eventsItemList.get(i).getCreatedAt(),
                                    eventsItemList.get(i).getEventFileCount()
                            );

                            if(!inserted){
                                unSync++;
                            }
                        }

                        if (unSync > 0){
                            Snackbar.make(rvEvent, unSync + " unsync item. Try to refresh it.", Snackbar.LENGTH_LONG).show();
                        }

                        srEvent.setRefreshing(false);
//                        Snackbar.make(rvEvent, response.body().getEvents().toString(), Snackbar.LENGTH_LONG).show();
                    }else if(!response.body().isStatus()){
                        Snackbar.make(rvEvent, response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                        srEvent.setRefreshing(false);
                    }
                }
                loadFromLocal();
            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                Snackbar.make(rvEvent, "Cannot load event", Snackbar.LENGTH_LONG).show();
                srEvent.setRefreshing(false);
                loadFromLocal();
            }
        });
    }

    public void loadFromLocal(){
        eventsItemList = new ArrayList<>();
        Cursor result = db.getAllEvent();
        if (result.getCount() == 0){
            Snackbar.make(view, "There is no data found!", Snackbar.LENGTH_LONG).show();
            return;
        }

        while(result.moveToNext()){
            EventsItem event = new EventsItem();
            event.setEventId(result.getInt(0));
            event.setEventName(result.getString(1));
            event.setCreatedAt(result.getString(2));
            event.setEventFileCount(result.getInt(3));

            eventsItemList.add(event);
        }

        eventAdapter.setData(eventsItemList);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getBooleanExtra("result", false)){
            this.loadFromLocal();
        }
    }
}
