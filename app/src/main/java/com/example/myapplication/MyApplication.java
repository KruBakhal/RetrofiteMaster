package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.Practice.API_Interface.API_Client;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        API_Client.getInstanceAPI();
    }

}
