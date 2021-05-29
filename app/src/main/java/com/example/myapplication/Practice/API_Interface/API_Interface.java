package com.example.myapplication.Practice.API_Interface;

import com.example.myapplication.Practice.PostModel.Post_Example;
import com.example.myapplication.Practice.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Interface {


    @GET("/public-api/users")
    Call<Example> getAllUser(@Query("page") String page);

    @GET("/public-api/posts")
    Call<Post_Example> getSpecific_User_Post(@Query("user_id") String id);


}
