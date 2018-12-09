package com.example.shaffz.mvvmgettest;

import android.app.Application;


public class MvvmApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

      /*  DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);*/


    }
}
