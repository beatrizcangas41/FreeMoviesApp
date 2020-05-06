package com.example.freemoviesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.freemoviesapp.model.ResponseMovie;
import com.example.freemoviesapp.repository.MovieRepository;
import com.example.freemoviesapp.util.Connection;
import com.example.freemoviesapp.util.Constant;

import java.util.HashMap;
import java.util.Map;

public class MovieViewModel extends AndroidViewModel {
    public MutableLiveData<ResponseMovie> movieList = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
//    public MutableLiveData<Boolean> showLoadingProg = new MutableLiveData<>();
    private MovieRepository movieRepository = new MovieRepository();
    private boolean isConnected;

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMovies() {
//        showLoadingProg.setValue(true);
        isConnected = Connection.isNetworkAvailable(getApplication());
        if (isConnected) {
//            showLoadingProg.setValue(false);
            Map<String, String> map = new HashMap<>();
            map.put(Constant.Api.TOKEN_NAME, Constant.Api.TOKEN_VALUE);
            movieList = movieRepository.getMovies(map);
//            showLoadingProg.setValue(false);
        } else {
            errorMessage.setValue("No internet Connection");
//            showLoadingProg.setValue(false);
        }
    }
}