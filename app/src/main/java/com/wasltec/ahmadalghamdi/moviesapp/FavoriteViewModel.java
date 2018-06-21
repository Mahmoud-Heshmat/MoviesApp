package com.wasltec.ahmadalghamdi.moviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wasltec.ahmadalghamdi.moviesapp.database.AppDatabase;
import com.wasltec.ahmadalghamdi.moviesapp.database.FavoriteEntry;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel{

    // Constant for logging
    private static final String TAG = FavoriteViewModel.class.getSimpleName();

    private LiveData<List<FavoriteEntry>> list;


    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        list = database.favoriteDAO().loadAllFavorites();
    }

    public LiveData<List<FavoriteEntry>> getFavorites() {
        return list;
    }
}
