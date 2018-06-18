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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wasltec.ahmadalghamdi.moviesapp.api.Singleton;
import com.wasltec.ahmadalghamdi.moviesapp.api.URLS;
import com.wasltec.ahmadalghamdi.moviesapp.model.Movie;
import com.wasltec.ahmadalghamdi.moviesapp.utilts.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private MoviesAdapter adapter;
    GridLayoutManager gridLayoutManager;

    Context context;
    ArrayList<Movie> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View parentLayout = findViewById(android.R.id.content);

        context = this;

        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView =  findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (isOnline()) {
            getMovies();
        }else{
            Snackbar.make(parentLayout, "Please check network Connection", Snackbar.LENGTH_LONG).show();
        }
    }

    public void getMovies(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.mainURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                if (!response.isEmpty()){
                    list = JsonUtils.parseMoviesJson(response);
                    adapter = new MoviesAdapter(context, list);
                    recyclerView.setAdapter(adapter);
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
                Collections.sort(list, new Comparator<Movie>() {
                    public int compare(Movie v1, Movie v2) {
                        return Integer.valueOf(v2.getVote_count()).compareTo(Integer.valueOf(v1.getVote_count()));
                    }
                });

                adapter = new MoviesAdapter(context, list);
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.most_popular:
                Collections.sort(list, new Comparator<Movie>() {
                    public int compare(Movie v1, Movie v2) {
                        return Double.valueOf(v2.getPopularity()).compareTo(Double.valueOf(v1.getPopularity()));
                    }
                });

                adapter = new MoviesAdapter(context, list);
                recyclerView.setAdapter(adapter);
                return true;
        }
        return false;
    }
}