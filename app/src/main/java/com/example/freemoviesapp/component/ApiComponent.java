package com.example.freemoviesapp.component;

import com.example.freemoviesapp.module.ApiClientModule;
import com.example.freemoviesapp.module.AppModule;
import com.example.freemoviesapp.repository.MovieRepository;
import com.example.freemoviesapp.view.MovieActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiClientModule.class, AppModule.class})
public interface ApiComponent {
    void inject(MovieActivity movieActivity);
    void injectMovie(MovieRepository movieRepository);
}