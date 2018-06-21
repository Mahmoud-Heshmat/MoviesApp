package com.wasltec.ahmadalghamdi.moviesapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wasltec.ahmadalghamdi.moviesapp.database.FavoriteEntry;
import com.wasltec.ahmadalghamdi.moviesapp.model.Favorite;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        holder.overView.setText(favorite.getOverView());
        String average = favorite.getAverageRate() + " /10" ;
        holder.rate.setText(average);
        holder.releaseDate.setText(favorite.getReleaseDate());
        holder.posterImage.setImageBitmap(ByteArrayToBitmap(favorite.getPosterImage()));
    }

    public Bitmap ByteArrayToBitmap(byte[] byteArray) {

        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        Log.d("responsePoster", "  " + bitmap.toString());
        return bitmap;
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

        private ImageView posterImage;
        private TextView movieName;
        private TextView releaseDate;
        private TextView rate;
        private TextView overView;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImage =  itemView.findViewById(R.id.posterImage);
            movieName =  itemView.findViewById(R.id.movieName);
            releaseDate =  itemView.findViewById(R.id.releaseDate);
            rate =  itemView.findViewById(R.id.rate);
            overView =  itemView.findViewById(R.id.overview);
        }
    }

}