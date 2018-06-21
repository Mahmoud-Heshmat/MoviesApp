package com.wasltec.ahmadalghamdi.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.wasltec.ahmadalghamdi.moviesapp.Executors.AppExecutors;
import com.wasltec.ahmadalghamdi.moviesapp.api.Singleton;
import com.wasltec.ahmadalghamdi.moviesapp.api.URLS;
import com.wasltec.ahmadalghamdi.moviesapp.database.AppDatabase;
import com.wasltec.ahmadalghamdi.moviesapp.database.FavoriteEntry;
import com.wasltec.ahmadalghamdi.moviesapp.model.Favorite;
import com.wasltec.ahmadalghamdi.moviesapp.model.Movie;
import com.wasltec.ahmadalghamdi.moviesapp.model.Video;
import com.wasltec.ahmadalghamdi.moviesapp.utilts.JsonUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.posterImage) ImageView posterImage_txt ;
    @BindView(R.id.movieTitle) TextView title_txt ;
    @BindView(R.id.releaseDate) TextView releaseDate_txt ;
    @BindView(R.id.rate) TextView rate_txt ;
    @BindView(R.id.overview) TextView overview_txt ;
    @BindView(R.id.reviews) Button reviews ;
    @BindView(R.id.recycle_view) RecyclerView recyclerView;


    private VideoAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Context context;
    ArrayList<Video> list = new ArrayList<>();

    Movie movie;
    String id, path, title, relaseDate, vote, overview;

    private AppDatabase appDatabase;

    MenuItem favItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        context = this;

        appDatabase = AppDatabase.getsInstance(getApplicationContext());

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        reviews.setOnClickListener(this);

        movie = getIntent().getParcelableExtra("movie");
        if (movie != null){
            id = movie.getId();

            Log.d("responseTag", "  "+ id);

            path = URLS.posterPath + movie.getPoster_path();
            title = movie.getTitle();
            relaseDate = movie.getRelease_date();
            vote = movie.getVote_average();
            overview = movie.getOverview();

            populateUI();
        }

        getVideos();
    }

    private void populateUI() {
        if (path != null){
            Picasso.with(this)
                    .load(path)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(posterImage_txt);
        }

        if (title != null || title.isEmpty()){
            title_txt.setText(title);
        }

        if (relaseDate != null){
            releaseDate_txt.setText(relaseDate);
        }

        if (vote != null){
            String ratePercentage = vote + " /10";
            rate_txt.setText(ratePercentage);
        }

        if (overview != null){
            overview_txt.setText(overview);
        }

    }

    public void getVideos(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.getVideosURL(id), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseVideos", response);
                if (!response.isEmpty()){
                    list = JsonUtils.parseVideosJson(response);

                    adapter = new VideoAdapter(context, list);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reviews:
                Intent intent = new Intent(context, ReviewsActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.detail_menu, menu);
        favItem = menu.findItem(R.id.favorite);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final FavoriteEntry favoriteEntry =  appDatabase.favoriteDAO().loadFavoriteMovie(movie.getId());
                if (favoriteEntry != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            favItem.setIcon(R.drawable.ic_favorite_black_24dp);
                        }
                    });
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                checkFavorite(item);
                return true;
        }
        return false;
    }

    private void checkFavorite(final MenuItem item) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final FavoriteEntry favoriteEntry =  appDatabase.favoriteDAO().loadFavoriteMovie(movie.getId());
                if (favoriteEntry != null){
                    deleteFromFavorite(item, favoriteEntry);
                }else{
                    addToFavorite(item);
                }
            }
        });
    }

    private void addToFavorite(final MenuItem item) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                if (movie != null) {
                    FavoriteEntry favoriteEntry = new FavoriteEntry(movie.getId(), movie.getTitle(), date,
                            getByteArrayImage(), movie.getOverview(), movie.getVote_average(), movie.getRelease_date());
                    appDatabase.favoriteDAO().insertFavorite(favoriteEntry);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            item.setIcon(R.drawable.ic_favorite_black_24dp);
                        }
                    });
                }
            }
        });
    }

    private void deleteFromFavorite(final MenuItem item, final FavoriteEntry favoriteEntry) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.favoriteDAO().deleteFavorite(favoriteEntry);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                    }
                });
            }
        });
    }

    private byte[] getByteArrayImage(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable)posterImage_txt.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] photo = baos.toByteArray();
        return photo;
    }


}
