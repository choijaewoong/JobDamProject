package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-18.
 */
public class ArticleLab {
    @SerializedName("items")
    private ArrayList<Articles> mArticleList;
    public ArrayList<Articles> getArticleList() { return mArticleList; }

    public void modifyArticleData(Article article){
        Articles articles = new Articles();
        articles.setArticle(article);
        for(int i = 0; i<mArticleList.size(); i++){
            if(mArticleList.get(i).getArticle().getId().equals(article.getId())){
                mArticleList.set(i, articles);
                return;
            }
        }
        mArticleList.add(0,articles);
    }
}
