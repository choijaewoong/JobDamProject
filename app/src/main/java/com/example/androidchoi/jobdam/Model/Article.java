package com.example.androidchoi.jobdam.Model;


import java.io.Serializable;

/**
 * Created by Choi on 2015-11-18.
 */
public class Article implements Serializable {

    private int timestamp;
    private int emotionIndex;
    private boolean likebool;
    private String content;
    private int like_cnt;

    public String getContent() { return content; }
    public void setContent(String content) {
        this.content = content;
    }
}
