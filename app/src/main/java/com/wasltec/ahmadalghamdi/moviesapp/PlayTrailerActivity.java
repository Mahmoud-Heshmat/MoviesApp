package com.wasltec.ahmadalghamdi.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.wasltec.ahmadalghamdi.moviesapp.api.URLS;
import com.wasltec.ahmadalghamdi.moviesapp.model.Video;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayTrailerActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{

    public static final String API_KEY = "AIzaSyBakR-Pq2LKOO4gUHrIm7CwMvjK69qIdIA";

    //@BindView(R.id.youtube_view) YouTubePlayerView youTubePlayerView;
    @BindView(R.id.movieTitle) TextView title_txt ;

    String key, title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_trailer);
        ButterKnife.bind(this);

        Video video = getIntent().getParcelableExtra("video");
        if (video != null){
            key = video.getKey();
            title = video.getName();

            populateUI();
        }

        YouTubePlayerSupportFragment youTubePlayerView =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerView.initialize(API_KEY, this);
    }

    private void populateUI() {

        if (title != null){
            title_txt.setText(title);
        }

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(key);
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getApplicationContext(),
                "onInitializationFailure()",
                Toast.LENGTH_SHORT).show();
    }
}
