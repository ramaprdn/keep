package com.example.ramapradana.keep.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramapradana.keep.CreateNoteActivity;
import com.example.ramapradana.keep.EventFileDialog;
import com.example.ramapradana.keep.R;
import com.example.ramapradana.keep.data.remote.model.FileItem;

import java.util.ArrayList;
import java.util.List;

public class EventFileAdapter extends RecyclerView.Adapter<EventFileAdapter.ViewHolder> {
    private List<FileItem> fileList = new ArrayList<>();
    private Context context;
    private FragmentManager fragmentManager;
    private static final String DOWNLOAD_BASE_URL = "https://stark-cove-43258.herokuapp.com/api/download/";
    private DownloadManager downloadManager;



    public EventFileAdapter(Context context, FragmentManager fragmentManager){
        this.context = context;
        this.fragmentManager = fragmentManager;
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

        viewHolder.itemFile.setOnLongClickListener((View.OnLongClickListener) v -> {
            EventFileDialog eventFileDialog = new EventFileDialog(fileItem);
            eventFileDialog.show(fragmentManager, "file menu");
            return true;
        });

        String format = fileItem.getEventfileFormat();
        if(format.equals("doc") || format.equals("docx") || format.equals("txt")){
            viewHolder.ivLogo.setImageResource(R.drawable.doc);
        }else if(format.equals("png") || format.equals("jpg") || format.equals("jpeg") || format.equals("bmp")){
            viewHolder.ivLogo.setImageResource(R.drawable.image);
        }else if(format.equals("ppt") || format.equals("pptx")){
            viewHolder.ivLogo.setImageResource(R.drawable.delete_file);
        }else if(format.equals("pdf")){
            viewHolder.ivLogo.setImageResource(R.drawable.pdf);
        }else if(format.equals("mp3")){
            viewHolder.ivLogo.setImageResource(R.drawable.music_file);
        }else if(format.equals("mp4") || format.equals("mkv")){
            viewHolder.ivLogo.setImageResource(R.drawable.video_file);
        }else if(format.equals("rar") || format.equals("zip")){
            viewHolder.ivLogo.setImageResource(R.drawable.rar);
        }else if(format.equals("note")){
            viewHolder.ivLogo.setImageResource(R.drawable.noted);
        }
        else{
            viewHolder.ivLogo.setImageResource(R.drawable.delete_file);
        }


        viewHolder.itemFile.setOnClickListener((v) -> {
            if (fileItem.getEventfileFormat().equals("note")) {
                Intent intent = new Intent(context, CreateNoteActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("file", fileItem);
                context.startActivity(intent);
            }else{
                //download the file
                downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(DOWNLOAD_BASE_URL + fileItem.getEventfileId());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);

                Toast.makeText(context, "Downloading " + fileItem.getEventfileTitle(), Toast.LENGTH_SHORT).show();
            }
        });

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
