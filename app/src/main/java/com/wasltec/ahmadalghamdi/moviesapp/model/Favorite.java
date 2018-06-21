package com.wasltec.ahmadalghamdi.moviesapp.model;


import java.util.Date;

public class Favorite {

    private int id;
    private String movieId;
    private String name;
    private Date date;


    public Favorite(int id, String movieId, String name, Date date){
        this.id = id;
        this.movieId = movieId;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
