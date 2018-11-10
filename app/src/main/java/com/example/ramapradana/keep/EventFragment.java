package com.example.ramapradana.keep;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramapradana.keep.data.remote.model.EventsResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

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

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), "Product Sans Regular.ttf", true);

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        rvEvent = view.findViewById(R.id.rv_event_item);

        eventAdapter = new EventsAdapter();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");

        call = KeepApiClient.getKeepApiServiceWithToken(token).getEvent();
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().isStatus()){
                        eventAdapter.addData(response.body().getEvents());
//                        Snackbar.make(rvEvent, response.body().getEvents().toString(), Snackbar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                Snackbar.make(rvEvent, "Cannot load event", Snackbar.LENGTH_LONG).show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvEvent.setLayoutManager(linearLayoutManager);
        rvEvent.setAdapter(eventAdapter);




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fabCreate = getView().findViewById(R.id.fab_create);
        fabCreate.setOnClickListener((v) -> {
            CreateEventDialog createEventDialog = new CreateEventDialog();
            createEventDialog.show(getFragmentManager(), "create event");
        });
    }
}
