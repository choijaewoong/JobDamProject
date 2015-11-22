package com.example.androidchoi.jobdam.Model;

import com.example.androidchoi.jobdam.Calendar.CurrentTime;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
    private String writeDate;
    private String[] tag;
//    private int[] mImageResources;
    private String startDate;
    private String endDate;

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
    public String getContent() {
        return content;
    }
    public int getCategory() { return category; }
    public String getWriteDate() { return writeDate; }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) { this.content = content; }
    public void setCategory(int category){ this.category = category; }
    public void setStartDate(String startDate){ this.startDate = startDate; }
    public void setEndDate(String endDate){ this.endDate = endDate; }

    public MyCard(){
        userId = User.USER_NAME;
        category = 0;
        CurrentTime currentTime = new CurrentTime();
        writeDate = currentTime.getYear() + ". " + currentTime.getMonth() + ". " + currentTime.getDayOfMonth();
    }
}
