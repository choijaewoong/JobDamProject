package com.example.androidchoi.jobdam.Model;

import com.begentgroup.xmlparser.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobData implements Serializable {

    public static final String JOBITEM = "jobItem";

    private int mLogoResourceId;
    private JobCompanyData company;
    private JobContentData position;
    @SerializedName("url")
    private String mSiteUrl; // 사이트 주소

    public JobCompanyData getCompany() {
        return company;
    }
    public JobContentData getPosition() {
        return position;
    }
    public String getSiteUrl() {
        return mSiteUrl;
    }

    @SerializedName("title")
    private String mJobTitle; // 채용 공고 제목
    @SerializedName("experience-level")
    private String mExperience;
    @SerializedName("required-education-level")
    private String mEducationLevel;
    private String location;
    private String keyword;
    private String salary;
    private StringBuilder mQualification = new StringBuilder(); //지원 자격
    private StringBuilder mConditions = new StringBuilder(); //근무 조건

    private Date mStart; // 시작일
    private Date mEnd; // 마감일

    public JobData() {
        mLogoResourceId = android.R.mipmap.sym_def_app_icon;
       mJobTitle = "채용 정보 제목";
        mSiteUrl = "www.google.com";
    }
}
