package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class CardData implements Serializable {

    public static final String CARDITEM = "cardItem";
    public static final String CARDPOSITION = "cardposition";

    String mTitle;
    String mContent;
    int mCategoryIndex;
    int[] mImageResources;
    Date mStartDate;
    Date mEndDate;

    public void setCarditem(CardData cardItem){
        mTitle = cardItem.getTitle();
        mContent = cardItem.getContent();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setTitle(String title) { mTitle = title; }

    public void setContent(String content) { mContent = content; }

    public CardData(){
        mTitle = "Android Studio";
        mContent = "1. 코드 최적화는 가장 나중에\n" +
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
