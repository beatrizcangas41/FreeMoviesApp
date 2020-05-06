package com.example.freemoviesapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.freemoviesapp.R;
import com.example.freemoviesapp.databinding.ActivityDetailseBinding;
import com.example.freemoviesapp.model.Movie;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailseBinding detailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailse);
        Intent intent = getIntent();
        if (intent != null) {
            Movie movie = intent.getParcelableExtra("Movie");
            detailsBinding.setMovie(movie);
        }
    }
}
