package com.example.freemoviesapp.app;

import android.app.Application;

import com.example.freemoviesapp.component.ApiComponent;
import com.example.freemoviesapp.component.DaggerApiComponent;
import com.example.freemoviesapp.module.ApiClientModule;
import com.example.freemoviesapp.module.AppModule;
import com.example.freemoviesapp.util.Constant;

public class MyApplication extends Application {

    public static ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiClientModule(new ApiClientModule(Constant.Api.BASE_URL))
                .build();
    }

    public static ApiComponent getComponent() {
        return mApiComponent;
    }
}