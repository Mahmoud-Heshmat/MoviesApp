package com.wasltec.ahmadalghamdi.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wasltec.ahmadalghamdi.moviesapp.api.URLS;
import com.wasltec.ahmadalghamdi.moviesapp.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.posterImage) ImageView posterImage_txt ;
    @BindView(R.id.movieTitle) TextView title_txt ;
    @BindView(R.id.releaseDate) TextView releaseDate_txt ;
    @BindView(R.id.rate) TextView rate_txt ;
    @BindView(R.id.overview) TextView overview_txt ;

    Movie movie;
    String path, title, relaseDate, vote, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //movie = getIntent().getParcelableExtra("Movie");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            path = bundle.getString("path");
            title = bundle.getString("title");
            relaseDate = bundle.getString("releaseDate");
            vote = bundle.getString("vote");
            overview = bundle.getString("overview");
        }
        populateUI();

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
            String ratePercentage = vote + "/10";
            rate_txt.setText(ratePercentage);
        }

        if (overview != null){
            overview_txt.setText(overview);
        }

    }
}
