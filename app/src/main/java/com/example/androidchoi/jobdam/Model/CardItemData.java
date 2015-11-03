package com.example.androidchoi.jobdam.Model;

import java.util.Date;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class CardItemData {

    String mTitle;
    String mContent;
    int mCategoryIndex;
    int[] mImageResources;
    Date mStartDate;
    Date mEndDate;

    public String getmTitle() {
        return mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public CardItemData(){
        mTitle = "카드 제목";
        mContent = "카드 내용";
    }
}