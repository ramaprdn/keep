package com.example.ramapradana.keep;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramapradana.keep.data.remote.model.EventsItem;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MenuEventDialog extends AppCompatDialogFragment {
    private EventsItem eventsItem;
    private MaterialButton btnEdit;

    public MenuEventDialog(EventsItem eventsItem){
        this.eventsItem = eventsItem;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getActivity(), "Product Sans Regular.ttf", true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_event_menu, null);

        btnEdit = view.findViewById(R.id.btn_edit);

        builder.setView(view);


        btnEdit.setOnClickListener(v -> {
            EditEventDialog editEventDialog = new EditEventDialog(eventsItem);
            editEventDialog.show(getFragmentManager(), "edit");
            this.dismiss();
        });
        return builder.create();
    }
}
