package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobData implements Serializable {

    public static final String JOBITEM = "jobItem";

    int mLogoResourceId;
    String mCorporation;
    String mJobTitle;

    public void setJobTitle(String mJobTitle) {
        this.mJobTitle = mJobTitle;
    }

    public String getCorporation() {
        return mCorporation;
    }

    public String getJobTitle() {
        return mJobTitle;
    }

    String mQualification;
    String mConditions;
    String mSiteUrl;
    Date mStart;
    Date mEnd;

    public JobData() {
        mLogoResourceId = android.R.mipmap.sym_def_app_icon;
        mCorporation ="기업 이름";
        mJobTitle = "채용 정보 제목";
        mSiteUrl = "www.google.com";
    }
}
