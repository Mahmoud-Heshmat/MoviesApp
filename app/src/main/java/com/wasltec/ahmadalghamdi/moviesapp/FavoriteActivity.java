package com.wasltec.ahmadalghamdi.moviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wasltec.ahmadalghamdi.moviesapp.Executors.AppExecutors;
import com.wasltec.ahmadalghamdi.moviesapp.api.Singleton;
import com.wasltec.ahmadalghamdi.moviesapp.api.URLS;
import com.wasltec.ahmadalghamdi.moviesapp.database.AppDatabase;
import com.wasltec.ahmadalghamdi.moviesapp.database.FavoriteEntry;
import com.wasltec.ahmadalghamdi.moviesapp.model.Favorite;
import com.wasltec.ahmadalghamdi.moviesapp.model.Movie;
import com.wasltec.ahmadalghamdi.moviesapp.utilts.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.recycle_view) RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Context context;

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        context = this;

        appDatabase = AppDatabase.getsInstance(getApplicationContext());

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new FavoriteAdapter(context);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<FavoriteEntry> favorites = adapter.getTasks();
                        appDatabase.favoriteDAO().deleteFavorite(favorites.get(position));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);

        getFavoriteMovies();

    }


    public void getFavoriteMovies(){
        FavoriteViewModel viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntry> favoriteEntries) {
                Log.d("responseLiveData", "Live data is updated");
                adapter.update_data(favoriteEntries);
            }
        });
    }
}
