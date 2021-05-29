package com.example.myapplication.Ebizz.API;

import com.example.myapplication.Ebizz.model.Example;
import com.example.myapplication.Ebizz.model.ShopExample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://mandobk.com.sa/mandobk_admin_test/api/users/";

    /**
     * The return type is important here
     * The class structure that you've defined in Call<T>
     * should exactly match with your json response
     * If you are not using another api, and using the same as mine
     * then no need to worry, but if you have your own API, make sure
     * you change the return type appropriately
     **/
    @GET("shopcategory")
    Call<Example> getExample();

    @POST("shopByType")
    Call<ShopExample> getShopCatgory(@Query("shop_type") String shop_type, @Query("user_id") String user_id,
                                     @Query("latitude") String latitude,
                                     @Query("longitude") String longitude);


}