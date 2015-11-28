package com.example.androidchoi.jobdam.Model;

/**
 * Created by Choi on 2015-11-28.
 */
public class NoticeDateData implements NoticeData {

    public String getDate() {
        return date;
    }
    private String date;

    public NoticeDateData(String date) {
        this.date = date;
    }
}
