package com.example.ramapradana.keep;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ramapradana.keep.data.local.database.DatabaseHelper;
import com.example.ramapradana.keep.data.remote.model.PostApiResponse;
import com.example.ramapradana.keep.data.remote.model.SearchUserResponse;
import com.example.ramapradana.keep.data.remote.model.User;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFriendActivity extends AppCompatActivity {
    private ImageView btnSearch;
    private TextInputEditText tvUsername;
    private TextView tvNotFound;
    private LinearLayout llFriend;
    private ProgressDialog progressDialog;
    private TextView tvName;
    private User friendResult;
    private MaterialButton btnAddFriend;
    private DatabaseHelper db;

    Call<SearchUserResponse> call;
    Call<PostApiResponse> addFriend;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);

        tvUsername = findViewById(R.id.et_search);
        tvName = findViewById(R.id.tv_name);
        btnSearch = findViewById(R.id.btn_search);
        tvNotFound = findViewById(R.id.tv_not_found);
        llFriend = findViewById(R.id.ll_friend);
        btnAddFriend = findViewById(R.id.btn_add_friend);

        db = new DatabaseHelper(this);

        btnAddFriend.setOnClickListener(v -> {
            progressDialog.setMessage("adding...");
            progressDialog.show();


            addFriend = KeepApiClient.getKeepApiService()
                    .addFriend(friendResult.getUserId(), token);

            addFriend.enqueue(new Callback<PostApiResponse>() {
                @Override
                public void onResponse(Call<PostApiResponse> call, Response<PostApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().isStatus()){
                            boolean inserted = db.insertFriend(friendResult.getUserId(), friendResult.getUserUsername(), friendResult.getUserName(), friendResult.getUserEmail());
                            if(inserted){
                                Toast.makeText(getApplicationContext(), "Successfully adding " + friendResult.getUserUsername(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Failed to add " + friendResult.getUserUsername(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Server error!", Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.hide();
                }

                @Override
                public void onFailure(Call<PostApiResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Could not add friend right now!", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            });
        });

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Search Friend");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        SharedPreferences sharedPreferences = getSharedPreferences("credential", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("access_token", "");

        btnSearch.setOnClickListener((v) -> {
            String username = tvUsername.getText().toString();
            if(!username.equals("")){
                progressDialog = new ProgressDialog(SearchFriendActivity.this);
                progressDialog.setMessage("searching...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                call = KeepApiClient.getKeepApiService()
                        .searchUser(username, token);
                call.enqueue(new Callback<SearchUserResponse>() {
                    @Override
                    public void onResponse(Call<SearchUserResponse> call, Response<SearchUserResponse> response) {
                        if (response.isSuccessful()){
                            if(response.body().isStatus()){
                                friendResult = response.body().getUser();
                                tvName.setText(response.body().getUser().getUserName());
                                tvNotFound.setVisibility(View.INVISIBLE);
                                llFriend.setVisibility(View.VISIBLE);

                            }else{
                                llFriend.setVisibility(View.INVISIBLE);
                                tvNotFound.setVisibility(View.VISIBLE);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.hide();
                    }

                    @Override
                    public void onFailure(Call<SearchUserResponse> call, Throwable t) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Could not search friend!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                tvUsername.setError("Please input your friend's username!");
            }
        });

    }
}
