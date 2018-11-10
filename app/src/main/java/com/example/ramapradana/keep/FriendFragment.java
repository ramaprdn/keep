package com.example.ramapradana.keep;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import me.anwarshahriar.calligrapher.Calligrapher;


public class FriendFragment extends android.support.v4.app.Fragment {
    private RelativeLayout rtfform;
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), "Product Sans Regular.ttf", true);

        View rootView = inflater.inflate(R.layout.fragment_friend, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rtfform = (RelativeLayout)view.findViewById(R.id.form);

        rtfform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailFriendActivity.class);
                getActivity().startActivity(intent);
            }
        });


    }
}
