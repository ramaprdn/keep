package com.example.ramapradana.keep.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ramapradana.keep.Friend;
import com.example.ramapradana.keep.R;

import java.util.List;

/**
 * Created by Evan Saragih on 11/19/2018.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {

    Context mContext;
    List<Friend> mData;
    Dialog myDialog;

    public FriendAdapter(Context mContext, List<Friend> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_friend, viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(v);

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.detail_friend);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        vHolder.item_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.detail_name);
                TextView dialog_email_tv = (TextView) myDialog.findViewById(R.id.detail_email);
                ImageView dialog_foto_img = (ImageView) myDialog.findViewById(R.id.fotoprofil);
                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());
                dialog_email_tv.setText(mData.get(vHolder.getAdapterPosition()).getEmail());
                dialog_foto_img.setImageResource(mData.get(vHolder.getAdapterPosition()).getPhoto());
                //Toast.makeText(mContext,"Test Click"+String.valueOf(vHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                myDialog.show();
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_name.setText(mData.get(i).getName());
        myViewHolder.tv_email.setText(mData.get(i).getEmail());
        myViewHolder.img.setImageResource(mData.get(i).getPhoto());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout item_friend;
        private TextView tv_name;
        private TextView tv_email;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_friend = (RelativeLayout) itemView.findViewById(R.id.friend_item_id);
            tv_name = (TextView) itemView.findViewById(R.id.nama);
            tv_email = (TextView) itemView.findViewById(R.id.email);
            img = (ImageView) itemView.findViewById(R.id.foto);
        }
    }
}
