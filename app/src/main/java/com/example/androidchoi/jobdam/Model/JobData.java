package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobData implements Serializable {

    public static final String JOBITEM = "jobItem";

    int mLogoResourceId;
    String mCorporation; // 기업 이름
    String mJobTitle; // 채용 공고 제목

    public void setJobTitle(String mJobTitle) {
        this.mJobTitle = mJobTitle;
    }

    public String getCorporation() {
        return mCorporation;
    }

    public String getJobTitle() {
        return mJobTitle;
    }

    String mQualification; //지원 자격
    String mConditions; //근무 조건
    String mSiteUrl; // 사이트 주소
    Date mStart; // 시작일
    Date mEnd; // 마감일

    public JobData() {
        mLogoResourceId = android.R.mipmap.sym_def_app_icon;
        mCorporation ="기업 이름";
        mJobTitle = "채용 정보 제목";
        mSiteUrl = "www.google.com";
    }

    public void setData(){}
}
