package com.example.ramapradana.keep;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramapradana.keep.data.local.database.DatabaseHelper;
import com.example.ramapradana.keep.data.remote.model.EventsItem;
import com.example.ramapradana.keep.data.remote.model.PostApiResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class MenuEventDialog extends AppCompatDialogFragment {
    private EventsItem eventsItem;
    private MaterialButton btnEdit;
    private MaterialButton btnCreateNote;
    private MaterialButton btnUploadFile;
    private MaterialButton btnDelete;
    private Call<PostApiResponse> deleteEvent;
    private DatabaseHelper db;

    @SuppressLint("ValidFragment")
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
        btnCreateNote = view.findViewById(R.id.btn_create_note);
        btnUploadFile = view.findViewById(R.id.btn_upload_file);
        btnDelete = view.findViewById(R.id.btn_delete);

        db = new DatabaseHelper(getContext());

        builder.setView(view).setTitle(eventsItem.getEventName());

        //check read storage permission.
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

        btnEdit.setOnClickListener(v -> {
            EditEventDialog editEventDialog = new EditEventDialog(eventsItem);
            editEventDialog.show(getFragmentManager(), "edit");
            this.dismiss();
        });

        btnCreateNote.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CreateNoteActivity.class);
            intent.putExtra("event_id", eventsItem.getEventId());
            startActivity(intent);
            dismiss();
        });

        btnDelete.setOnClickListener((v) -> {
            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
            deleteDialog.setTitle("Warning")
                    .setMessage("Are you sure you want to delete '" + eventsItem.getEventName() + "' ?")
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    })
                    .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setMessage("deleting...");
                            progressDialog.setCancelable(false);
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);

                            deleteEvent = KeepApiClient.getKeepApiService()
                                    .deleteEvent(eventsItem.getEventId(), sharedPreferences.getString("access_token", ""));
                            deleteEvent.enqueue(new Callback<PostApiResponse>() {
                                @Override
                                public void onResponse(Call<PostApiResponse> call, Response<PostApiResponse> response) {
                                    if (response.isSuccessful() && response.code() == 200){
                                        if (response.body().isStatus()){
                                            db.deleteEvent(new String[] {String.valueOf(eventsItem.getEventId())});
                                        }
                                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "Could not delete event. Try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                    dismiss();
                                    progressDialog.hide();
                                }

                                @Override
                                public void onFailure(Call<PostApiResponse> call, Throwable t) {
                                    Toast.makeText(getContext(), "Could not connect to server", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                    progressDialog.hide();
                                }
                            });
                        }
                    });

            AlertDialog alert = deleteDialog.create();
            alert.show();
        });

//        btnUploadFileClicked();

        return builder.create();
    }

//    private void btnUploadFileClicked() {
//        btnUploadFile.setOnClickListener((v) -> {
//            Intent intent = FileUtil
//        });
//    }

}
