package com.example.ramapradana.keep;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramapradana.keep.adapter.FriendAdapter;
import com.example.ramapradana.keep.data.local.database.DatabaseHelper;
import com.example.ramapradana.keep.data.remote.model.FriendsResponse;
import com.example.ramapradana.keep.data.remote.model.UserItem;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Evan Saragih on 11/19/2018.
 */

public class FriendFragment extends android.support.v4.app.Fragment {

    View v;
    private RecyclerView rvFriend;
    private List<Friend> listFriend;
    private RelativeLayout rlAddFriend;
    private Call<FriendsResponse> getFriend;
    private String token;
    private List<UserItem> friends = new ArrayList<>();
    private DatabaseHelper db;
    private FriendAdapter friendAdapter;
    private TextView tvName;
    private TextView tvEmail;
    private RelativeLayout rvUserProfile;
    private Dialog userDialog;
    private TextView tvUserNameDialog;
    private TextView tvUserEmailDialog;
    private Button btnLogout;

    public FriendFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_friend, container, false);

        rvFriend = (RecyclerView) v.findViewById(R.id.rv_friend_list);
        rlAddFriend = v.findViewById(R.id.rv_add_friend);
        tvName = v.findViewById(R.id.tv_name);
        tvEmail = v.findViewById(R.id.tv_email);
        rvUserProfile = v.findViewById(R.id.rv_user_profile);

        userDialog = new Dialog(getContext());
        userDialog.setContentView(R.layout.detail_friend);
        tvUserNameDialog = userDialog.findViewById(R.id.tv_user_name);
        tvUserEmailDialog = userDialog.findViewById(R.id.tv_user_email);
        btnLogout = userDialog.findViewById(R.id.btn_logout);

//        friends = new ArrayList<>();

        db = new DatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);
        this.token = sharedPreferences.getString("access_token", "");
        tvName.setText(sharedPreferences.getString("name", ""));
        tvEmail.setText(sharedPreferences.getString("email", ""));

        tvUserNameDialog.setText(sharedPreferences.getString("name", ""));
        tvUserEmailDialog.setText(sharedPreferences.getString("email", ""));

        rlAddFriend.setOnClickListener(v1 -> {
            Intent intent = new Intent(getContext(), SearchFriendActivity.class);
            startActivity(intent);
        });

        showProfile();
        //load data
        loadFromLocal();
        if(this.isNetworkConnected()){
            loadFromCloud();
        }

        return v;

    }

    private void showProfile() {
        rvUserProfile.setOnClickListener(v1 -> {
            userDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            userDialog.show();

            btnLogout.setOnClickListener(v2 -> {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();

                if(db == null){
                    db = new DatabaseHelper(getContext());
                }
                db.clean();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
                getActivity().finish();

            });
        });
    }


    public void setAdapter(List<UserItem> userItems){
        friendAdapter = new FriendAdapter(getContext(), userItems);
        friendAdapter.setData(userItems);

//        friendsAdapter = new FriendsAdapter(getContext(), friends);
        Log.d("FRIEND IN SET ADAPTER", userItems.toString());
        rvFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFriend.setAdapter(friendAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadFromCloud(){
        getFriend = KeepApiClient.getKeepApiService()
                .getFriend(this.token);

        getFriend.enqueue(new Callback<FriendsResponse>() {
            @Override
            public void onResponse(Call<FriendsResponse> call, Response<FriendsResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isStatus()){
//                        friends.clear();
                        friends.addAll(response.body().getUser());
                        Log.d("RESPONSE FRIENDS", response.body().getUser().toString());

                        Log.d("FRIEND", friends.toString());
                        db.synchronizeFriendData(friends);
                        setAdapter(response.body().getUser());
                        Log.d("FRIEND ADAPTER COUNT", friendAdapter.getItemCount() + "");
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Could not get friends", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FriendsResponse> call, Throwable t) {
                loadFromLocal();
            }
        });
    }

    public void loadFromLocal(){
        friends.clear();
        Cursor result = db.getAllFriend();

        while(result.moveToNext()){
            UserItem userItem = new UserItem();
            userItem.setUserId(result.getInt(0));
            userItem.setUserUsername(result.getString(1));
            userItem.setUserName(result.getString(2));
            userItem.setUserEmail(result.getString(3));

            friends.add(userItem);
        }

        if (result.getCount() > 0){
            setAdapter(friends);
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
