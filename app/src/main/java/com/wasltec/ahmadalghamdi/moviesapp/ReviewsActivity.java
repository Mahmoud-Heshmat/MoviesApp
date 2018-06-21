package com.wasltec.ahmadalghamdi.moviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.wasltec.ahmadalghamdi.moviesapp.model.Review;
import com.wasltec.ahmadalghamdi.moviesapp.utilts.JsonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsActivity extends AppCompatActivity {

    @BindView(R.id.recycle_view) RecyclerView recyclerView;
    @BindView(R.id.no_reviews) TextView noReviewsTxt;

    private ReviewsAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    Context context;
    String movieID;
    ArrayList<Review> list = new ArrayList<>();

    //RecycleView LoadMore
    private static int page = 1;
    EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);
        View parentLayout = findViewById(android.R.id.content);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            movieID = bundle.getString("id");

            context = this;

            linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);

            if (isOnline()) {
                scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        page++;
                        getMovies(page);
                    }
                };
                // Adds the scroll listener to RecyclerView
                recyclerView.addOnScrollListener(scrollListener);

                getMovies(page);
            }else{
                Snackbar.make(parentLayout, R.string.network_connection, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public void getMovies(final int page){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.getReviewsURL(movieID, page), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                if (!response.isEmpty()){
                    list = JsonUtils.parseReviewsJson(response, list);

                    if (list.isEmpty()){
                        noReviewsTxt.setVisibility(View.VISIBLE);
                    }else {
                        if (page == 1){
                            adapter = new ReviewsAdapter(context, list);
                            recyclerView.setAdapter(adapter);
                        }else if(page > 1){
                            adapter.update_data(list);
                        }
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
}
