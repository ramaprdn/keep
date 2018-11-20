package com.example.ramapradana.keep;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ramapradana.keep.data.local.database.DatabaseHelper;
import com.example.ramapradana.keep.data.remote.model.CreateEventResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventDialog extends AppCompatDialogFragment {
    TextInputEditText tvName;
    private Call<CreateEventResponse> call;
    private FrameLayout fmEvent;
    private DatabaseHelper db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_event, null);

        db = new DatabaseHelper(view.getContext());

        tvName = view.findViewById(R.id.tv_name);
        fmEvent = getActivity().findViewById(R.id.event_frame);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");

        builder.setView(view)
                .setTitle("Create New Event")
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = tvName.getText().toString();

                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("saving...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);

                    call = KeepApiClient.getKeepApiService().postCreateNewEvent(name, token);
                    call.enqueue(new Callback<CreateEventResponse>() {
                        @Override
                        public void onResponse(Call<CreateEventResponse> call, Response<CreateEventResponse> response) {
                            progressDialog.hide();
                            if (response.isSuccessful()){
                                if(response.body().isStatus()){
                                    //request is valid and success
                                    boolean isInserted = db.insertEvent(
                                            response.body().getEventId(),
                                            response.body().getEventName(),
                                            response.body().getCreatedAt(),
                                            response.body().getFileCount()
                                    );

                                    if(!isInserted){
                                        Snackbar.make(fmEvent, "failed to sync data but inserted in the cloud. Try to refresh this page.", Snackbar.LENGTH_LONG).show();
                                    }else{
                                        Snackbar.make(fmEvent, response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                                    }
                                    Intent intent = new Intent();
                                    intent.putExtra("result", true);
                                    getTargetFragment().onActivityResult(getTargetRequestCode(), 1, intent);
                                }else{
                                    Snackbar.make(fmEvent, response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                                }
                            }else{
                                Snackbar.make(fmEvent, "could not connect to server.", Snackbar.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<CreateEventResponse> call, Throwable t) {
                            progressDialog.hide();
                            Snackbar.make(fmEvent, "Ops.. No internet connection.", Snackbar.LENGTH_LONG).show();
                        }
                    });
                });

        return builder.create();
    }
}
