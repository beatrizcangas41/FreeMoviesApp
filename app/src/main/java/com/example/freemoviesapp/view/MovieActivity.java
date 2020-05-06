package com.example.freemoviesapp.view;

import android.app.Application;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.freemoviesapp.R;
import com.example.freemoviesapp.adapter.MovieAdapter;
import com.example.freemoviesapp.app.MyApplication;
import com.example.freemoviesapp.databinding.ActivityMainBinding;
import com.example.freemoviesapp.model.Movie;
import com.example.freemoviesapp.viewmodel.MovieViewModel;

import java.util.List;

import javax.inject.Inject;
import retrofit2.Retrofit;

public class MovieActivity extends AppCompatActivity {
    @Inject
    Retrofit retrofit;
    @Inject
    Application application;
    private ActivityMainBinding binding;
    private MovieViewModel movieViewModel;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ((MyApplication) getApplication()).getComponent().inject(this);
        binding.setLifecycleOwner(this);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        showLoadingBar();
        observableChanges();
    }

    private void observableChanges() {
        movieViewModel.movieList.observe(this, responseMovie -> Recycler(responseMovie.getMovies()));

        movieViewModel.errorMessage.observe(this, s -> Toast.makeText(MovieActivity.this, s, Toast.LENGTH_LONG).show());

//        movieViewModel.showLoadingProg.observe(this, show -> {
//            if (show)
//                showLoadingBar();
//            else
//                hideLoadingBar();
//        });
    }

    private void showLoadingBar() {
        progressDialog.show();
    }

    private void hideLoadingBar() {
        progressDialog.show();
    }

    private void Recycler(List<Movie> movies) {
        binding.setAdapter(new MovieAdapter(movies, MovieActivity.this));
    }
}