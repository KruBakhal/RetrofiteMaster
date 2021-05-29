package com.example.myapplication.Practice.API_Interface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myapplication.Constants.WebConstants.BASE_URL;

/**
 * Singelton class
 * base inttialize in application class
 */
public class API_Client {


    public static Retrofit retrofit;

    public static Retrofit getInstanceAPI() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit;
        } else {
            return retrofit;
        }
    }

    public static API_Interface getInstanceAPI2() {
        return getInstanceAPI().create(API_Interface.class);
    }

}
