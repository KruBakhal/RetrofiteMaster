package com.example.myapplication.Ebizz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @SerializedName("shop_data")
    @Expose
    private List<ShopDatum> shopData = null;

    @SerializedName("page_token")
    @Expose
    private Integer pageToken;

    public List<ShopDatum> getShopData() {
        return shopData;
    }

    public void setShopData(List<ShopDatum> shopData) {
        this.shopData = shopData;
    }

   /* public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }*/

    public Integer getPageToken() {
        return pageToken;
    }

    public void setPageToken(Integer pageToken) {
        this.pageToken = pageToken;
    }

}