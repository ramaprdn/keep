package com.example.ramapradana.keep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ramapradana.keep.data.remote.model.EventsItem;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private List<EventsItem> dataset = new ArrayList<>();

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

        viewHolder.tvEventName.setText(eventsItem.getEventName());
        viewHolder.tvDateAndFileCount.setText(String.valueOf(eventsItem.getCreatedAt()) + " - " + String.valueOf(eventsItem.getEventFileCount()) + " files");

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
