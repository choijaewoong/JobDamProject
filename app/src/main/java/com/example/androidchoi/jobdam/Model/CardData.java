package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class CardData implements Serializable {

    public static final String CARDITEM = "cardItem";

    String mTitle;
    String mContent;
    int mCategoryIndex;
    int[] mImageResources;
    Date mStartDate;
    Date mEndDate;

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public CardData(){
        mTitle = "카드 제목";
        mContent = "카드 내용";
    }
}
