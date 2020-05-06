package com.example.freemoviesapp.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.freemoviesapp.api.ApiInterface;
import com.example.freemoviesapp.app.MyApplication;
import com.example.freemoviesapp.model.ResponseMovie;

import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.freemoviesapp.view.MovieActivity.progressDialog;

public class MovieRepository {
    @Inject
    Retrofit retrofit;
    @Inject
    Application application;
    private static MovieRepository repository;
    private ApiInterface apiInterface ;

    public static MovieRepository getInstance(){
        if (repository == null){
            repository = new MovieRepository();
        }
        return repository;
    }

    public MovieRepository(){
        MyApplication.getComponent().injectMovie(this);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public MutableLiveData<ResponseMovie> getMovies(Map<String , String> stringMap){
        MutableLiveData<ResponseMovie> responseMovieMutableLiveData = new MutableLiveData<>();
        apiInterface.getMovies(stringMap).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMovie> call, @NonNull Response<ResponseMovie> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    responseMovieMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseMovie> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                responseMovieMutableLiveData.setValue(null);
            }
        });
        return responseMovieMutableLiveData;
    }
}