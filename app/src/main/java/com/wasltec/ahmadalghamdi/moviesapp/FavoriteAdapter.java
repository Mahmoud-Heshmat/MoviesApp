package com.wasltec.ahmadalghamdi.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wasltec.ahmadalghamdi.moviesapp.database.FavoriteEntry;
import com.wasltec.ahmadalghamdi.moviesapp.model.Favorite;


import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private Context context;
    private List<FavoriteEntry> items = new ArrayList<>();

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_favorite, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, final int position) {

        final FavoriteEntry favorite = items.get(position);
        holder.movieName.setText(favorite.getName());
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

    public void update_data(List<FavoriteEntry> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<FavoriteEntry> getTasks() {
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView movieName;

        public ViewHolder(View itemView) {
            super(itemView);
            movieName =  itemView.findViewById(R.id.movieName);
        }
    }

}