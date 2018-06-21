package com.wasltec.ahmadalghamdi.moviesapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wasltec.ahmadalghamdi.moviesapp.model.Favorite;
import com.wasltec.ahmadalghamdi.moviesapp.model.Movie;

import java.util.List;

@Dao
public interface FavoriteDAO {

    @Query("SELECT * FROM Favorite ORDER BY id DESC")
    LiveData<List<FavoriteEntry>> loadAllFavorites();

    @Query("SELECT * FROM Favorite WHERE movieId = :movieID")
    FavoriteEntry loadFavoriteMovie(String movieID);

    @Insert
    void insertFavorite(FavoriteEntry favoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(FavoriteEntry favoriteEntry);

    @Delete
    void deleteFavorite(FavoriteEntry favoriteEntry);

}
