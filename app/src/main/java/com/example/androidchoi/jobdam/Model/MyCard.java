package com.example.androidchoi.jobdam.Model;

import com.example.androidchoi.jobdam.Calendar.CurrentTime;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class MyCard implements Serializable {

    public static final String CARD_ITEM = "cardItem";
    public static final String CARD_NEW = "cardNew";

    @SerializedName("user_id")
    private String userId;
    private String title;
    private String content;
    private int category;
    @SerializedName("tag")
    private ArrayList<String> tags = new ArrayList<String>();
//    private int[] mImageResources;
    private String startDate;
    private String endDate;
    private CurrentTime writeDate;

    public void setData(String title, String content){
        this.title = title;
        this.content = content;
    }
    public void setCarditem(MyCard cardItem){
        title = cardItem.getTitle();
        content = cardItem.getContent();
    }

    public String getUserId() { return userId;}
    public String getTitle() {  return title; }
    public String getContent() {  return content;}
    public ArrayList<String> getTags() { return tags; }
    public int getCategory() { return category; }
    public String getWriteDate() {
        return writeDate.getYear() + ". " + writeDate.getMonth() + ". " + writeDate.getDayOfMonth(); }
    public void addTag(String tag){ tags.add(tag);}
    public void removeTag(int index){ tags.remove(index);}
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) { this.content = content; }
    public void setCategory(int category){ this.category = category; }
    public void setStartDate(String startDate){ this.startDate = startDate; }
    public void setEndDate(String endDate){ this.endDate = endDate; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public MyCard(){
        userId = User.getInstance().getUserId();
        category = 0;
        writeDate = new CurrentTime();
        startDate = writeDate.getYear() + "년 " + writeDate.getMonth() + "월 " + writeDate.getDayOfMonth() + "일";
        endDate = writeDate.getYear() + "년 " + writeDate.getMonth() + "월 " + writeDate.getDayOfMonth() + "일";
    }
    public MyCard(String test){
        title = test;
        content = test;
        userId = User.getInstance().getUserId();
        category = 1;
        writeDate = new CurrentTime();
        startDate = writeDate.getYear() + "년 " + writeDate.getMonth() + "월 " + writeDate.getDayOfMonth() + "일";
        endDate = writeDate.getYear() + "년 " + writeDate.getMonth() + "월 " + writeDate.getDayOfMonth() + "일";
    }
}
