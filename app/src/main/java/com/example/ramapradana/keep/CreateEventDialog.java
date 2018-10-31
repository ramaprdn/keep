package com.example.ramapradana.keep;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ramapradana.keep.data.remote.model.PostApiResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventDialog extends AppCompatDialogFragment {
    TextInputEditText tvName;
    private Call<PostApiResponse> call;
    private FrameLayout fmEvent;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_event_dialog, null);

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

                    call = KeepApiClient.getKeepApiServiceWithToken(token).postCreateNewEvent(name);
                    call.enqueue(new Callback<PostApiResponse>() {
                        @Override
                        public void onResponse(Call<PostApiResponse> call, Response<PostApiResponse> response) {
                            progressDialog.hide();
                            if (response.isSuccessful()){
                                if(response.body().isStatus()){
                                    //request is valid and success
                                    Snackbar.make(fmEvent, response.body().getMsg() + " " + response.code(), Snackbar.LENGTH_LONG).show();
                                }else{
                                    Snackbar.make(fmEvent, response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                                }
                            }else{
                                Snackbar.make(fmEvent, "could not connect to server.", Snackbar.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<PostApiResponse> call, Throwable t) {
                            progressDialog.hide();
                            Snackbar.make(fmEvent, "Ops.. No internet connection.", Snackbar.LENGTH_LONG).show();
                        }
                    });
                });

        return builder.create();
    }
}
