package com.example.androidchoi.jobdam.Model;

import android.text.Spanned;

/**
 * Created by Choi on 2015-11-28.
 */
public class NoticeContentData implements NoticeData{

    private int announcementType;
    private Spanned content;
    private String date;
    private boolean isRead;

    public int getAnnouncementType() {
        return announcementType;
    }
    public String getDate() {
        return date;
    }
    public Spanned getContent() {
        return content;
    }
    public boolean isRead() {
        return isRead;
    }

    public NoticeContentData(int announcementType, Spanned content, String date, boolean isRead) {
        this.announcementType = announcementType;
        this.content = content;
        this.date = date;
        this.isRead = isRead;
    }
//
//    public void setData(int type, String content, String date, boolean check){
//        announcementType = type;
//        this.content = content;
//        this.date = date;
//        isRead = check;
//    }
}
