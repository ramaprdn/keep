package com.example.ramapradana.keep.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ramapradana.keep.EventDetailActivity;
import com.example.ramapradana.keep.MenuEventDialog;
import com.example.ramapradana.keep.R;
import com.example.ramapradana.keep.data.remote.model.EventsItem;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private List<EventsItem> dataset = new ArrayList<>();
    private android.support.v4.app.FragmentManager fragmentManager;
    private Context context;

    public EventsAdapter(android.support.v4.app.FragmentManager fragmentManager, Context context){
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_event, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        EventsItem eventsItem = dataset.get(i);

        String title = "";
        if(eventsItem.getUserEventCount() > 1){
            title = eventsItem.getEventName() + " ("+ eventsItem.getUserEventCount() +")";
        }else{
            title = eventsItem.getEventName();
        }
        viewHolder.tvEventName.setText(title);
        viewHolder.tvDateAndFileCount.setText(String.valueOf(eventsItem.getCreatedAt()) + " - " + String.valueOf(eventsItem.getEventFileCount()) + " files");

        viewHolder.llItem.setOnLongClickListener(v -> {
            MenuEventDialog menuEventDialog = new MenuEventDialog(eventsItem);
            menuEventDialog.show(fragmentManager,"event menu");
            return true;
        });

        viewHolder.llItem.setOnClickListener((v) -> {
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra("event", eventsItem);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setData(List<EventsItem> events){
        this.dataset = events;
        notifyDataSetChanged();
    }

    public void addData(List<EventsItem> events){
        this.dataset.addAll(events);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvEventName;
        TextView tvDateAndFileCount;
        LinearLayout llItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEventName = itemView.findViewById(R.id.tv_event_name);
            tvDateAndFileCount = itemView.findViewById(R.id.tv_event_date_and_file);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
