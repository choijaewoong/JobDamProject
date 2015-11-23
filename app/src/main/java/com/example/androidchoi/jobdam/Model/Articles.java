package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Choi on 2015-11-18.
 */
public class Articles implements Serializable{

    private String _id;
    @SerializedName("board")
    private Article article;
    public Article getArticle() { return article; }
    public void setArticle(Articles articles) {
        this.article.setArticle(articles.getArticle());
        _id = articles.getId();
    }
    public String getId() { return _id; }
    public Articles(){
        article = new Article();
    }
}
