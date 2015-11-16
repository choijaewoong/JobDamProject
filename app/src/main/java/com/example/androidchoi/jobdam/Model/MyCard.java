package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class MyCard implements Serializable {

    public static final String CARD_ITEM = "cardItem";
    public static final String CARD_NEW = "cardNew";
    public static final String CARDPOSITION = "cardposition";

    private String id;
    @SerializedName("user_id")
    private String userId;
    private String title;
    private String content;
    private String category;
    private int[] mImageResources;
    private Date startDate;
    private Date endDate;

    public void setData(String title, String content){
        this.title = title;
        this.content = content;
    }
    public void setCarditem(MyCard cardItem){
        title = cardItem.getTitle();
        content = cardItem.getContent();
    }

    public String getId(){ return id; }
    public String getUserId() { return userId;}
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public void setTitle(String title) {
//        try {
//            this.title = URLEncoder.encode(title,"euc-kr");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        this.title = title;
    }
    public void setContent(String content) { this.content = content; }

    public MyCard(){
        id = UUID.randomUUID().toString();
        userId = User.USER_NAME;
        title = "안녕하세요 제목입니다.";
        content = "1. 코드 최적화는 가장 나중에\n" +
                "2. 수레바퀴를 다시 만들지 말라.\n" +
                "  (만들어져 있는건 굳이 만들지 말자)\n" +
                "   (gemFile,  JQuery)" +
                "1. 코드 최적화는 가장 나중에\n"+
                "1. 코드 최적화는 가장 나중에\n" +
                "2. 수레바퀴를 다시 만들지 말라.\n" +
                "  (만들어져 있는건 굳이 만들지 말자)\n" +
                "   (gemFile,  JQuery)" +
                "1. 코드 최적화는 가장 나중에\n"+
                "1. 코드 최적화는 가장 나중에\n" +
                "2. 수레바퀴를 다시 만들지 말라.\n" +
                "  (만들어져 있는건 굳이 만들지 말자)\n" +
                "   (gemFile,  JQuery)" +
                "1. 코드 최적화는 가장 나중에\n"+
                "1. 코드 최적화는 가장 나중에\n" +
                "2. 수레바퀴를 다시 만들지 말라.\n" +
                "  (만들어져 있는건 굳이 만들지 말자)\n" +
                "   (gemFile,  JQuery)" +
                "1. 코드 최적화는 가장 나중에\n"+
                "1. 코드 최적화는 가장 나중에\n" +
                "2. 수레바퀴를 다시 만들지 말라.\n" +
                "  (만들어져 있는건 굳이 만들지 말자)\n" +
                "   (gemFile,  JQuery)" +
                "1. 코드 최적화는 가장 나중에\n";

    }
}
