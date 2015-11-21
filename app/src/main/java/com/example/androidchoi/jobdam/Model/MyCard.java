package com.example.androidchoi.jobdam.Model;

import com.example.androidchoi.jobdam.Calendar.CurrentTime;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class MyCard implements Serializable {

    public static final String CARD_ITEM = "cardItem";
    public static final String CARD_NEW = "cardNew";
    public static final String CARDPOSITION = "cardposition";

    @SerializedName("card_id")
    private String cardId;
    @SerializedName("user_id")
    private String userId;
    private String title;
    private String content;
    private int category;
    private String writeDate;
//    private int[] mImageResources;
    private int startDate;
    private int endDate;

    public void setData(String title, String content){
        this.title = title;
        this.content = content;
    }
    public void setCarditem(MyCard cardItem){
        title = cardItem.getTitle();
        content = cardItem.getContent();
    }

    public String getId(){ return cardId; }
    public String getUserId() { return userId;}
    public String getTitle() {  return title; }
    public String getContent() {
        return content;
    }
    public int getCategory() { return category; }
    public String getWriteDate() { return writeDate; }

    public void setTitle(String title) {
//        try {
//            this.title = URLEncoder.encode(title,"euc-kr");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        this.title = title;
    }
    public void setContent(String content) { this.content = content; }
    public void setCategory(int category){ this.category = category; }

    public MyCard(){
        cardId = UUID.randomUUID().toString();
        userId = User.USER_NAME;
        CurrentTime currentTime = new CurrentTime();
        writeDate = currentTime.getYear() + ". " + currentTime.getMonth() + ". " + currentTime.getDayOfMonth();

//        title = "안녕하세요 제목입니다.";
//        content = "1. 코드 최적화는 가장 나중에\n" +
//                "2. 수레바퀴를 다시 만들지 말라.\n" +
//                "  (만들어져 있는건 굳이 만들지 말자)\n" +
//                "   (gemFile,  JQuery)" +
//                "1. 코드 최적화는 가장 나중에\n"+
//                "1. 코드 최적화는 가장 나중에\n" +
//                "2. 수레바퀴를 다시 만들지 말라.\n" +
//                "  (만들어져 있는건 굳이 만들지 말자)\n" +
//                "   (gemFile,  JQuery)" +
//                "1. 코드 최적화는 가장 나중에\n"+
//                "1. 코드 최적화는 가장 나중에\n" +
//                "2. 수레바퀴를 다시 만들지 말라.\n" +
//                "  (만들어져 있는건 굳이 만들지 말자)\n" +
//                "   (gemFile,  JQuery)" +
//                "1. 코드 최적화는 가장 나중에\n"+
//                "1. 코드 최적화는 가장 나중에\n" +
//                "2. 수레바퀴를 다시 만들지 말라.\n" +
//                "  (만들어져 있는건 굳이 만들지 말자)\n" +
//                "   (gemFile,  JQuery)" +
//                "1. 코드 최적화는 가장 나중에\n"+
//                "1. 코드 최적화는 가장 나중에\n" +
//                "2. 수레바퀴를 다시 만들지 말라.\n" +
//                "  (만들어져 있는건 굳이 만들지 말자)\n" +
//                "   (gemFile,  JQuery)" +
//                "1. 코드 최적화는 가장 나중에\n";
    }
}
