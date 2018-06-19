package com.wasltec.ahmadalghamdi.moviesapp.utilts;

import com.wasltec.ahmadalghamdi.moviesapp.model.Geners;
import com.wasltec.ahmadalghamdi.moviesapp.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String pageConst = "page";
    public static final String total_resultsConst = "total_results";
    public static final String total_pagesConst = "total_pages";
    public static final String resultsConst = "results";
    public static final String vote_countConst = "vote_count";
    public static final String idConst = "id";
    public static final String videoConst = "video";
    public static final String vote_averageConst = "vote_average";
    public static final String titleConst = "title";
    public static final String popularityConst = "popularity";
    public static final String poster_pathConst = "poster_path";
    public static final String original_languageConst = "original_language";
    public static final String original_titleConst = "original_title";
    public static final String genre_idsConst = "genre_ids";
    public static final String backdrop_pathConst = "backdrop_path";
    public static final String adultConst = "adult";
    public static final String overviewConst = "overview";
    public static final String release_dateConst = "release_date";

    public static ArrayList<Movie> parseMoviesJson(String json, ArrayList<Movie> list) {

        Movie movie = null;
        Geners geners = null;
        ArrayList<Geners> ids = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            String page = jsonObject.optString(pageConst);
            String total_results = jsonObject.optString(total_resultsConst);
            String total_pages = jsonObject.optString(total_pagesConst);

            JSONArray jsonArray = jsonObject.optJSONArray(resultsConst);
            JSONObject object = null;
            for (int i = 0 ; i < jsonArray.length() ; i++){
                object = jsonArray.getJSONObject(i);

                String vote_count = object.optString(vote_countConst);
                String id = object.optString(idConst);
                Boolean video = object.optBoolean(videoConst);
                String vote_average = object.optString(vote_averageConst);
                String title = object.optString(titleConst);
                String popularity = object.optString(popularityConst);
                String poster_path = object.optString(poster_pathConst);
                String original_language = object.optString(original_languageConst);
                String original_title = object.optString(original_titleConst);
                String backdrop_path = object.optString(backdrop_pathConst);
                Boolean adult = object.optBoolean(adultConst);
                String overview = object.optString(overviewConst);
                String release_date = object.optString(release_dateConst);

                JSONArray jsonIds = object.optJSONArray(genre_idsConst);
                for (int x = 0 ; x < jsonIds.length() ; x++){
                    geners = new Geners(jsonIds.optString(x));
                    ids.add(geners);
                }

                movie = new Movie(vote_count, id, video, vote_average, title, popularity, poster_path, original_language,
                        original_title, ids, backdrop_path, adult, overview, release_date);

                list.add(movie);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

}
