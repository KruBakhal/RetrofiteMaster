package com.example.myapplication.Ebizz.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Category {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name_eng")
    @Expose
    private String nameEng;
    @SerializedName("name_arb")
    @Expose
    private String nameArb;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Category(String all) {
        nameEng = all;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameArb() {
        return nameArb;
    }

    public void setNameArb(String nameArb) {
        this.nameArb = nameArb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}