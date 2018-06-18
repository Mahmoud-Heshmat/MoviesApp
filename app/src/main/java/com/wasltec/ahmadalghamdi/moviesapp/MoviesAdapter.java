package com.wasltec.ahmadalghamdi.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wasltec.ahmadalghamdi.moviesapp.api.URLS;
import com.wasltec.ahmadalghamdi.moviesapp.model.Movie;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Movie> items;

    public MoviesAdapter(Context context, ArrayList<Movie> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_movie, parent, false);
        return new MoviesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, final int position) {

        final Movie movie = items.get(position);
        final String posterPath = URLS.posterPath + movie.getPoster_path();

        Picasso.with(context)
                .load(posterPath)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error_black_24dp)
                .into(holder.posterImage);

        holder.posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("path", posterPath);
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("releaseDate", movie.getRelease_date());
                intent.putExtra("vote", movie.getVote_average());
                intent.putExtra("overview", movie.getOverview());
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

        private ImageView posterImage;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImage =  itemView.findViewById(R.id.posterImage);
        }
    }

}