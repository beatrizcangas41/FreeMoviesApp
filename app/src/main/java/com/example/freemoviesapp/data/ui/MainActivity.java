package com.example.freemoviesapp.data.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.freemoviesapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String displayName;
    //... other data fields that may be accessible to the UI

    public MainActivity(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
