package com.wasltec.ahmadalghamdi.moviesapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Favorite")
public class FavoriteEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String movieId;
    private String name;
    @ColumnInfo(name = "addedDate")
    private Date addedDate;


    @Ignore
    public FavoriteEntry(String movieId, String name, Date addedDate){
        this.movieId = movieId;
        this.name = name;
        this.addedDate = addedDate;
    }

    public FavoriteEntry(int id, String movieId, String name, Date addedDate){
        this.id = id;
        this.movieId = movieId;
        this.name = name;
        this.addedDate = addedDate;
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

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}
