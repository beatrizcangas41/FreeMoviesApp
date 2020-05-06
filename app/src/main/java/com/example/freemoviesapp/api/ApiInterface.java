package com.example.freemoviesapp.api;

import com.example.freemoviesapp.model.ResponseMovie;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("movie/popular")
    Call<ResponseMovie> getMovies(@QueryMap Map<String , String> queryParameters);
}
