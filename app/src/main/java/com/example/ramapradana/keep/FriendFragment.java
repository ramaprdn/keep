package com.example.ramapradana.keep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramapradana.keep.adapter.FriendAdapter;
import com.example.ramapradana.keep.data.local.database.DatabaseHelper;
import com.example.ramapradana.keep.data.remote.model.FriendsResponse;
import com.example.ramapradana.keep.data.remote.model.SearchUserResponse;
import com.example.ramapradana.keep.data.remote.model.User;
import com.example.ramapradana.keep.data.remote.model.UserItem;
import com.example.ramapradana.keep.data.remote.service.KeepApiClient;

import org.w3c.dom.Text;

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
    private List<UserItem> friends;
    private DatabaseHelper db;
    private FriendAdapter friendAdapter;
    private TextView tvName;
    private TextView tvEmail;

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

        friends = new ArrayList<>();

        db = new DatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);
        this.token = sharedPreferences.getString("access_token", "");
        tvName.setText(sharedPreferences.getString("name", ""));
        tvEmail.setText(sharedPreferences.getString("email", ""));

        rlAddFriend.setOnClickListener(v1 -> {
            Intent intent = new Intent(getContext(), SearchFriendActivity.class);
            startActivity(intent);
        });

        //load data
        if(this.isNetworkConnected()){
            loadFromCloud();
        }else{
            loadFromLocal();
        }
        //

        friendAdapter = new FriendAdapter(getContext());


        rvFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFriend.setAdapter(friendAdapter);
        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listFriend = new ArrayList<>();
        listFriend.add(new Friend("Joko Widodo", "jokowi@gmail.com", R.drawable.jokowi));
        listFriend.add(new Friend("Donald Trump", "donaldtrump@gmail.com", R.drawable.donaldtrump));
        listFriend.add(new Friend("Kim Jong Un", "kimjongun@gmail.com", R.drawable.kimjongun));
        listFriend.add(new Friend("Isyana Sarasvati", "isyana@gmail.com", R.drawable.isyanasarasvati));
        listFriend.add(new Friend("Raisa Andriana", "andrianaras@gmail.com", R.drawable.raisaandriana));
        listFriend.add(new Friend("Chelsea Islan", "chelsea@gmail.com", R.drawable.chelseaislan));

    }

    public void loadFromCloud(){
        getFriend = KeepApiClient.getKeepApiService()
                .getFriend(this.token);

        getFriend.enqueue(new Callback<FriendsResponse>() {
            @Override
            public void onResponse(Call<FriendsResponse> call, Response<FriendsResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isStatus()){
                        friends.clear();
                        friends = response.body().getUser();
                        db.synchronizeFriendData(friends);
                        friendAdapter.setData(friends);
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
            friendAdapter.setData(friends);
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
