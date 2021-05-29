package com.example.myapplication.Practice.PostModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post_Example {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("data")
    @Expose
    private List<PostData> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<PostData> getData() {
        return data;
    }

    public void setData(List<PostData> data) {
        this.data = data;
    }

}