package com.example.ramapradana.keep.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ramapradana.keep.Friend;
import com.example.ramapradana.keep.R;
import com.example.ramapradana.keep.data.remote.model.User;
import com.example.ramapradana.keep.data.remote.model.UserItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan Saragih on 11/19/2018.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {

    Context mContext;
    List<UserItem> mData;
    Dialog myDialog;
    OnClickListener onClickListener;

   public interface OnClickListener{
       void onClick(int pos);
   }

    public FriendAdapter(Context mContext, List<UserItem> friends) {
        this.mContext = mContext;
        this.mData = friends;

        Log.d("FRIENDS IN ADAPTER", friends.toString());
    }

    public void setData(List<UserItem> userItemList){
       this.mData = userItemList;
        Log.d("FRIENDS IN SET DATA", userItemList.toString());
       notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
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

//                TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.detail_name);
//                TextView dialog_email_tv = (TextView) myDialog.findViewById(R.id.detail_email);
//                ImageView dialog_foto_img = (ImageView) myDialog.findViewById(R.id.fotoprofil);
//
//                dialog_name_tv.setText(friend.getUserName());
//                dialog_email_tv.setText(friend.getUserEmail());
//                dialog_foto_img.setImageResource(R.drawable.chelseaislan);
//                //Toast.makeText(mContext,"Test Click"+String.valueOf(vHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
//                myDialog.show();
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_name.setText(mData.get(i).getUserName());
        myViewHolder.tv_email.setText(mData.get(i).getUserEmail());
    }

    @Override
    public int getItemCount() {
       return mData.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout item_friend;
        private TextView tv_name;
        private TextView tv_email;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_friend = (RelativeLayout) itemView.findViewById(R.id.friend_item_id);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            img = (ImageView) itemView.findViewById(R.id.foto);

            if (onClickListener != null){
                itemView.setOnClickListener(v -> {
                    onClickListener.onClick(getAdapterPosition());
                });
            }
        }
    }
}
