package com.example.ramapradana.keep;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.anwarshahriar.calligrapher.Calligrapher;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends android.support.v4.app.Fragment {

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), "Product Sans Regular.ttf", true);

        return inflater.inflate(R.layout.fragment_event, container, false);

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
