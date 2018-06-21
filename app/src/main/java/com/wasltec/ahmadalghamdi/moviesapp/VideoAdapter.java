package com.wasltec.ahmadalghamdi.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wasltec.ahmadalghamdi.moviesapp.api.URLS;
import com.wasltec.ahmadalghamdi.moviesapp.model.Movie;
import com.wasltec.ahmadalghamdi.moviesapp.model.Video;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Video> items;

    public VideoAdapter(Context context, ArrayList<Video> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_video, parent, false);
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, final int position) {

        final Video video = items.get(position);
        String trailer = "Trailer " + (position + 1);
        holder.videoNum.setText(trailer);

        holder.playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayTrailerActivity.class);
                intent.putExtra("video", video);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageButton playVideo;
        private TextView videoNum;

        public ViewHolder(View itemView) {
            super(itemView);
            playVideo =  itemView.findViewById(R.id.playVideo);
            videoNum =  itemView.findViewById(R.id.videoNum);
        }
    }

}