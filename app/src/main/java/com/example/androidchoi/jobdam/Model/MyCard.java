package com.example.androidchoi.jobdam.Model;

import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.MyApplication;
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
    private int jobCompetenceIndex;
    private int workCompetenceIndex;
    private int attitudeCompetenceIndex;
    private int category;
    @SerializedName("tag")
    private ArrayList<String> tags = new ArrayList<String>();
//    private int[] mImageResources;
//    private String startDate;
//    private String endDate;
    private String date;

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
    public int getJobCompetenceIndex(){ return jobCompetenceIndex; }
    public int getWorkCompetenceIndex(){ return workCompetenceIndex; }
    public int getAttitudeCompetenceIndex(){ return attitudeCompetenceIndex; }
    public ArrayList<String> getTags() { return tags; }
    public int getCategory() { return category; }
    public String getDate() { return date; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setJobCompetenceIndex(int index) { jobCompetenceIndex = index; }
    public void setWorkCompetenceIndex(int index) { workCompetenceIndex = index; }
    public void setAttitudeCompetenceIndex(int index) { attitudeCompetenceIndex = index; }
    public void setCategory(int category){ this.category = category; }
    public boolean addTag(String tag){
        if(tags.size() > 8){
            Toast.makeText(MyApplication.getContext(), "더 이상 태그를 추가 할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        for(int i=0; i<tags.size(); i++){
            if(tags.get(i).equals(tag)){
                return false;  // 이미 등록된 태그인 경우 추가하지 않고 false를 리턴
            }
        }
        tags.add(tag);
        return true; // 태그 추가 후 true 리턴
    }
    public boolean removeTagText(String tag){
        for(int i=0; i<tags.size(); i++){
            if(tags.get(i).equals(tag)){
                tags.remove(i);
                return true; // 태그 삭제 후 true 리턴
            }
        }
        return false; // 등록되지 않은 tag인 경우 false 리턴
    }
//    public void setStartDate(String startDate){ this.startDate = startDate; }
//    public void setEndDate(String endDate){ this.endDate = endDate; }
//    public String getStartDate() { return startDate; }
//    public String getEndDate() { return endDate; }
    public MyCard(){
        userId = User.getInstance().getUserId();
        category = 0;
        CurrentTime currentTime = new CurrentTime();
        date = currentTime.getYear() + ". " + currentTime.getMonth() + ". " + currentTime.getDayOfMonth();
        jobCompetenceIndex = 0;
        workCompetenceIndex = 0;
        attitudeCompetenceIndex = 0;
//        startDate = currentTime.getYear() + "년 " + currentTime.getMonth() + "월 " + currentTime.getDayOfMonth() + "일";
//        endDate = currentTime.getYear() + "년 " + currentTime.getMonth() + "월 " + currentTime.getDayOfMonth() + "일";
    }
}
