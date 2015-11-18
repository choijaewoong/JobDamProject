package com.example.androidchoi.jobdam.Model;


import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Choi on 2015-11-18.
 */
public class Article implements Serializable {

    private String board_id;
    private String user_id;
    private String timestamp;
    private int emotionIndex;
    private boolean likebool;
    private String content;
    private int like_cnt;

    public Article() {
        board_id = UUID.randomUUID().toString();
    }

    public String getId(){return board_id;}
    public String getContent() { return content; }
    public void setContent(String content) {
        this.content = content;
    }

    public void setArticle(String user_id,
                           int emotionIndex,
                           boolean likebool,
                           String content,
                           int like_cnt){
        this.user_id = user_id;
        this.emotionIndex = emotionIndex;
        this.likebool = likebool;
        this.content = content;
        this.like_cnt = like_cnt;
    }
}
