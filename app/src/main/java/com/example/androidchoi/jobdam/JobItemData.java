package com.example.androidchoi.jobdam;

import java.util.Date;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobItemData {
    int mLogoResourceId;
    String mTitle;
    String mSiteUrl;
    Date mStart;
    Date mEnd;

    public JobItemData() {
        mLogoResourceId = android.R.mipmap.sym_def_app_icon;
        mTitle = "채용 기업";
        mSiteUrl = "www.google.com";
    }
}
