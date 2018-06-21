package com.wasltec.ahmadalghamdi.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wasltec.ahmadalghamdi.moviesapp.EndlessRecycleView.EndlessRecyclerViewScrollListener;
import com.wasltec.ahmadalghamdi.moviesapp.api.Singleton;
import com.wasltec.ahmadalghamdi.moviesapp.api.URLS;
import com.wasltec.ahmadalghamdi.moviesapp.model.Movie;
import com.wasltec.ahmadalghamdi.moviesapp.utilts.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.recycle_view) RecyclerView recyclerView;

    private MoviesAdapter adapter;
    GridLayoutManager gridLayoutManager;

    Context context;
    ArrayList<Movie> list = new ArrayList<>();

    //RecycleView LoadMore
    private static int page = 1;
    EndlessRecyclerViewScrollListener scrollListener;

    //URL
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        View parentLayout = findViewById(android.R.id.content);

        context = this;

        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (isOnline()) {
            scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    page++;
                    getMovies(page, true);
                }
            };
            // Adds the scroll listener to RecyclerView
            recyclerView.addOnScrollListener(scrollListener);

            getMovies(page, true);
        }else{
            Snackbar.make(parentLayout, R.string.network_connection, Snackbar.LENGTH_LONG).show();
        }
    }

    public void getMovies(final int page, Boolean isPopular){

        if (!isPopular){
            url = URLS.getTopRatedURL(page);
        }else{
            url = URLS.getMoviesURL(page);
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                if (!response.isEmpty()){
                    list = JsonUtils.parseMoviesJson(response, list);

                    if (page == 1){
                        adapter = new MoviesAdapter(context, list);
                        recyclerView.setAdapter(adapter);
                    }else if(page > 1){
                        adapter.update_data(list);
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", error.toString());
            }
        });
        Singleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.home_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_rated:
                page = 1 ;
                list.clear();
                getMovies(page, false);
                return true;
            case R.id.most_popular:
                page = 1 ;
                list.clear();
                getMovies(page, true);
                return true;
            case R.id.favorite:
                startActivity(new Intent(context, FavoriteActivity.class));
                return true;
        }
        return false;
    }
}