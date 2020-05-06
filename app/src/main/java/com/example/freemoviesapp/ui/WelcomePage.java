package com.example.freemoviesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.freemoviesapp.R;

public class WelcomePage extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);


        new Handler().postDelayed(() -> {
            final Intent mainIntent = new Intent(WelcomePage.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }, 3000);
    }
}