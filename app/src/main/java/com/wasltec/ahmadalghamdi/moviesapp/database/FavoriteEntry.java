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
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] posterImage;
    private String overView;
    private String averageRate;
    private String releaseDate;

    @Ignore
    public FavoriteEntry(String movieId, String name, Date addedDate, byte[] posterImage, String overView,
                         String averageRate, String releaseDate){

        this.movieId = movieId;
        this.name = name;
        this.addedDate = addedDate;
        this.posterImage = posterImage;
        this.overView = overView;
        this.averageRate = averageRate;
        this.releaseDate = releaseDate;
    }

    public FavoriteEntry(int id, String movieId, String name, Date addedDate, byte[] posterImage, String overView,
                         String averageRate, String releaseDate){
        this.id = id;
        this.movieId = movieId;
        this.name = name;
        this.addedDate = addedDate;
        this.posterImage = posterImage;
        this.overView = overView;
        this.averageRate = averageRate;
        this.releaseDate = releaseDate;
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

    public byte[] getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(byte[] posterImage) {
        this.posterImage = posterImage;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(String averageRate) {
        this.averageRate = averageRate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
