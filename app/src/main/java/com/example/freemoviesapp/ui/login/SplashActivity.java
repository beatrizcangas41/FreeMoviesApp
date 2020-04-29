package com.example.freemoviesapp.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.freemoviesapp.R;

public class SplashActivity extends Activity {
    private Handler mWaitHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run () {
                try {
                    final Intent mainIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(mainIntent);
                    finish();

                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        },5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}