package com.example.ramapradana.keep;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ramapradana.keep.data.local.database.DatabaseHelper;
import com.example.ramapradana.keep.data.remote.model.CreateNoteResponse;
import com.example.ramapradana.keep.data.remote.model.FileItem;
import com.example.ramapradana.keep.data.remote.model.PostApiResponse;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNoteActivity extends AppCompatActivity {
    private int eventId;
    private TextInputEditText mTitle;
    private TextInputEditText mContent;
    private String title;
    private String content;
    private Call<CreateNoteResponse> call;
    private DatabaseHelper db;
    private FileItem fileItem;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        mTitle = findViewById(R.id.et_title);
        mContent = findViewById(R.id.et_notes);
        db = new DatabaseHelper(this);

        this.update = intent.getBooleanExtra("update", false);
        if (update){
            fileItem = (FileItem) intent.getSerializableExtra("file");
            mTitle.setText(fileItem.getEventfileTitle());
            mContent.setText(fileItem.getEventfileContent());
            toolbar.setTitle("Update Note");
        }else{
            this.eventId = intent.getIntExtra("event_id", 0);
            toolbar.setTitle("Create Note");
        }

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save){
            if (update){
                Toast.makeText(this, "update", Toast.LENGTH_LONG).show();
            }else{
                this.title = mTitle.getText().toString();
                this.content = mContent.getText().toString();

                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("saving note...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);

                SharedPreferences sharedPreferences = getSharedPreferences("credential", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("access_token", "");

                call = KeepApiClient.getKeepApiService()
                        .postCreateNote(token, this.title, this.eventId, this.content);
                call.enqueue(new Callback<CreateNoteResponse>() {
                    @Override
                    public void onResponse(Call<CreateNoteResponse> call, Response<CreateNoteResponse> response) {
                        progressDialog.hide();
                        if (response.isSuccessful()){
                            if (response.body().isStatus()){
                                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                boolean inserted = db.insertEventFile(
                                        response.body().getId(),
                                        eventId,
                                        title,
                                        content,
                                        response.body().getFormat(),
                                        response.body().getUploadBy(),
                                        response.body().getCreatedAt().getDate()
                                );

                                if (!inserted){
                                    Toast.makeText(getApplicationContext(), "Failed to sync data. Try to refresh it.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                Toast.makeText(getApplicationContext(), "Inserted.", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateNoteResponse> call, Throwable t) {
                        progressDialog.hide();

                        Toast.makeText(getApplicationContext(), "" + t, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
