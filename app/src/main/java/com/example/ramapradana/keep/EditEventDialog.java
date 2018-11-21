package com.example.ramapradana.keep;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.ramapradana.keep.data.remote.model.EventsItem;
import com.example.ramapradana.keep.data.remote.model.PostApiResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEventDialog extends AppCompatDialogFragment {
    private EventsItem eventsItem;
    private TextInputEditText tvName;
    private MaterialButton btnCancel;
    private MaterialButton btnSave;
    private Call<PostApiResponse> call;

    public EditEventDialog(EventsItem eventsItem){
        this.eventsItem = eventsItem;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_event, null);

        tvName = view.findViewById(R.id.tv_name);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSave = view.findViewById(R.id.btn_save);

        tvName.setText(eventsItem.getEventName());
        btnCancel.setOnClickListener((v -> {
            this.dismiss();
        }));

        btnSave.setOnClickListener((v -> {

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("updating...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            String editedName = tvName.getText().toString();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);
            String accessToken = sharedPreferences.getString("access_token", "");

            call = KeepApiClient.getKeepApiService().postUpdateEvent(eventsItem.getEventId(), editedName, accessToken);
            call.enqueue(new Callback<PostApiResponse>() {
                @Override
                public void onResponse(Call<PostApiResponse> call, Response<PostApiResponse> response) {
                    dismiss();
                    progressDialog.hide();
                    if (response.isSuccessful()){
                        if (response.body().isStatus()){
                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Could not update event.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PostApiResponse> call, Throwable t) {

                }
            });
        }));

        builder.setView(view);

        return builder.create();
    }
}
