package com.example.ramapradana.keep;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.Keep;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ramapradana.keep.adapter.InviteFriendAdapter;
import com.example.ramapradana.keep.data.local.database.DatabaseHelper;
import com.example.ramapradana.keep.data.remote.model.EventsItem;
import com.example.ramapradana.keep.data.remote.model.PostApiResponse;
import com.example.ramapradana.keep.data.remote.model.User;
import com.example.ramapradana.keep.data.remote.model.UserItem;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteFriendActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private List<UserItem> friends;
    private RecyclerView rvFriendList;
    private MaterialButton btnInvite;
    private EventsItem event;
    private Call<PostApiResponse> inviteFriends;
    private String token;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);

        db = new DatabaseHelper(this);
        rvFriendList = findViewById(R.id.rv_friend_list);
        btnInvite = findViewById(R.id.btn_invite);
        friends = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("credential", Context.MODE_PRIVATE);
        this.token = sharedPreferences.getString("access_token", "");

        progressDialog = new ProgressDialog(InviteFriendActivity.this);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Product Sans Regular.ttf", true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Invite Friends");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        Intent intent = getIntent();
        event = (EventsItem) intent.getSerializableExtra("eventItem");

        Cursor result = db.getAllFriend();
        if(result.getCount() > 0){
            while(result.moveToNext()){
                UserItem userItem = new UserItem();
                userItem.setUserId(result.getInt(0));
                userItem.setUserUsername(result.getString(1));
                userItem.setUserName(result.getString(2));
                userItem.setUserEmail(result.getString(3));

                friends.add(userItem);
            }
        }

        InviteFriendAdapter inviteFriendAdapter = new InviteFriendAdapter(this);
        inviteFriendAdapter.setData(this.friends);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setAutoMeasureEnabled(false);

        rvFriendList.setNestedScrollingEnabled(false);
        rvFriendList.setLayoutManager(linearLayoutManager);
        rvFriendList.setAdapter(inviteFriendAdapter);

        btnInvite.setOnClickListener(v -> {
            List<UserItem> checked = inviteFriendAdapter.getCheckedFriends();

            if (checked.size() > 0){
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Adding friends to this event!");
                progressDialog.show();

                List<Integer> friendId = new ArrayList<>();

                for(UserItem userItem : checked){
                    friendId.add(userItem.getUserId());
                }

                inviteFriends = KeepApiClient.getKeepApiService()
                        .inviteFriend(this.event.getEventId(), friendId, this.token);

                inviteFriends.enqueue(new Callback<PostApiResponse>() {
                    @Override
                    public void onResponse(Call<PostApiResponse> call, Response<PostApiResponse> response) {
                        progressDialog.hide();
                        if (response.isSuccessful() && response.code() == 200){
                            Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            if (response.body().isStatus()){
                                onBackPressed();
                                finish();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "server error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostApiResponse> call, Throwable t) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "could not add friend right now!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
