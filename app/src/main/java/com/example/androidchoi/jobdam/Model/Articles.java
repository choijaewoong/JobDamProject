package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2015-11-18.
 */
public class Articles {

    @SerializedName("board")
    private Article article;
    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }
}
