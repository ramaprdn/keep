package com.example.ramapradana.keep.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ramapradana.keep.InviteFriendActivity;
import com.example.ramapradana.keep.R;
import com.example.ramapradana.keep.data.remote.model.UserItem;

import java.util.ArrayList;
import java.util.List;

public class InviteFriendAdapter extends RecyclerView.Adapter<InviteFriendAdapter.ViewHolder>{
    private List<UserItem> friends;
    private List<UserItem> checkedFriends = new ArrayList<>();
    private Context context;

    public InviteFriendAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.invite_friend_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UserItem userItem = friends.get(i);

        viewHolder.tvName.setText(userItem.getUserName());
        viewHolder.tvEmail.setText(userItem.getUserEmail());

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemCLick(View v, int pos) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()){
                    checkedFriends.add(friends.get(pos));
                }else if(!checkBox.isChecked()){
                    checkedFriends.remove(friends.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public List<UserItem> getCheckedFriends() {
        return checkedFriends;
    }

    public void setData(List<UserItem> userItemList){
        this.friends = userItemList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CheckBox checkBox;
        private TextView tvName;
        private TextView tvEmail;
        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.chbk_tag);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);

            checkBox.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemCLick(v, getLayoutPosition());
        }
    }
}
