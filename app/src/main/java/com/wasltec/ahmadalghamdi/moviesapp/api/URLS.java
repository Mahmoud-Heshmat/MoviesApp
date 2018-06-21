package com.wasltec.ahmadalghamdi.moviesapp.api;

import com.wasltec.ahmadalghamdi.moviesapp.BuildConfig;

public class URLS {

    static String apiKey = BuildConfig.API_KEY;

    public static String getMoviesURL(int page){
        return "http://api.themoviedb.org/3/movie/popular?api_key="+apiKey+"&page="+page+"";
    }

    public static String getTopRatedURL(int page){
        return "http://api.themoviedb.org/3/movie/top_rated?api_key="+apiKey+"&page="+page+"";
    }

    public static String getVideosURL(String videoID){
        return "http://api.themoviedb.org/3/movie/"+videoID+"/videos?api_key="+apiKey+"";
    }

    public static String getReviewsURL(String movieID, int page){
        return "http://api.themoviedb.org/3/movie/"+movieID+"/reviews?api_key="+apiKey+"&page="+page+"";
    }

    public static final String posterPath = "http://image.tmdb.org/t/p/w185/";
}
