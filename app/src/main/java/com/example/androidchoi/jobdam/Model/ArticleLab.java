package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-18.
 */
public class ArticleLab {

    private int total_count;
    @SerializedName("items")
    private ArrayList<Articles> mArticleList;
    public int getTotalCount(){
        return total_count;
    }
    public ArrayList<Articles> getArticleList() { return mArticleList; }

}
