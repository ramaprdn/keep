package com.example.ramapradana.keep.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramapradana.keep.CreateNoteActivity;
import com.example.ramapradana.keep.R;
import com.example.ramapradana.keep.data.remote.model.FileItem;

import java.util.ArrayList;
import java.util.List;

public class EventFileAdapter extends RecyclerView.Adapter<EventFileAdapter.ViewHolder> {
    private List<FileItem> fileList = new ArrayList<>();
    private Context context;

    public EventFileAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_eventfile, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FileItem fileItem = fileList.get(i);

        viewHolder.tvTitle.setText(fileItem.getEventfileTitle());
        viewHolder.tvContent.setText(fileItem.getEventfileContent());

        if (fileItem.getEventfileFormat().equals("note")){
            viewHolder.itemFile.setOnClickListener((v) -> {
                Intent intent = new Intent(context, CreateNoteActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("file", fileItem);
                context.startActivity(intent);
            });
        }

    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public void setData(List<FileItem> fileList){
        this.fileList = fileList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivLogo;
        TextView tvTitle;
        TextView tvContent;
        TextView tvDate;
        CardView itemFile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.iv_logo);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDate = itemView.findViewById(R.id.tv_date);
            itemFile = itemView.findViewById(R.id.item_file);
        }
    }
}
