package com.example.ramapradana.keep;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.anwarshahriar.calligrapher.Calligrapher;


public class FriendFragment extends android.support.v4.app.Fragment {
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), "Product Sans Regular.ttf", true);

        View rootView = inflater.inflate(R.layout.fragment_friend, container, false);

        return rootView;
    }
}
