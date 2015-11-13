package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class CardData implements Serializable {

    public static final String CARD_ITEM = "cardItem";
    public static final String CARD_NEW = "cardNew";
    public static final String CARDPOSITION = "cardposition";

    private int id;
    private int userId;
    private String title;
    private String content;
    private String category;
    private int[] mImageResources;
    private Date startDate;
    private Date endDate;

    public void setCarditem(CardData cardItem){
        title = cardItem.getTitle();
        content = cardItem.getContent();
    }

    public int getId() { return id; }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }

    public CardData(){
        id = 0;
        title = id + "";
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
