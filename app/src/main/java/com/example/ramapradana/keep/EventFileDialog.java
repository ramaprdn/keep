package com.example.ramapradana.keep;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ramapradana.keep.data.local.database.DatabaseHelper;
import com.example.ramapradana.keep.data.remote.model.FileItem;
import com.example.ramapradana.keep.data.remote.model.PostApiResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class EventFileDialog extends AppCompatDialogFragment {
    private MaterialButton btnDelete;
    private FileItem file;
    private Call<PostApiResponse> call;
    private DatabaseHelper db;

    @SuppressLint("ValidFragment")
    public EventFileDialog(FileItem file){
        this.file = file;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getActivity(), "Product Sans Regular.ttf", true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_eventfile, null);

        db = new DatabaseHelper(getContext());

        btnDelete = view.findViewById(R.id.btn_delete);

        btnDelete.setOnClickListener((v) -> {
            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
            deleteDialog.setTitle("Warning!")
                    .setMessage("Are you sure you want to delete '"+ file.getEventfileTitle() +"'?")
                    .setCancelable(false)
                    .setNegativeButton("cancel", (dialog, which) -> {
                    })
                    .setPositiveButton("Delete", (dialog, which) -> {
                        ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("deleting...");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);

                        call = KeepApiClient.getKeepApiService()
                                .deleteFileOrNote(file.getEventfileId(), sharedPreferences.getString("access_token", ""));
                        call.enqueue(new Callback<PostApiResponse>() {
                            @Override
                            public void onResponse(Call<PostApiResponse> call, Response<PostApiResponse> response) {
                                progressDialog.hide();
                                if (response.isSuccessful() && response.code() == 200){
                                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                                    if (response.body().isStatus()){
                                        db.deleteFileOrNote(new String[] {String.valueOf(file.getEventfileId())});
                                    }
                                }else{
                                    Toast.makeText(getContext(), "Error deleting file. ", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PostApiResponse> call, Throwable t) {
                                progressDialog.hide();
                            }
                        });
                    });

            AlertDialog alert = deleteDialog.create();
            alert.show( );
        });

        builder.setView(view).setTitle(file.getEventfileTitle());
        return builder.create();

    }
}

