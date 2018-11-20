package com.example.ramapradana.keep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan Saragih on 11/19/2018.
 */

public class FriendFragment extends android.support.v4.app.Fragment {

    View v;
    private RecyclerView rvFriend;
    private List<Friend> listFriend;

    public FriendFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_friend, container, false);
        rvFriend = (RecyclerView) v.findViewById(R.id.friend_recyclerview);
        FriendAdapter friendAdapter = new FriendAdapter(getContext(),listFriend);
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
}
