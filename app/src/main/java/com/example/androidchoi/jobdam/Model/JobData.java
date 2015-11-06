package com.example.androidchoi.jobdam.Model;

import com.begentgroup.xmlparser.SerializedName;

import java.io.Serializable;

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
    @SerializedName("opening-timestamp")
    private int mStart; // 시작일
    @SerializedName("expiration-timestamp")
    private int mEnd; // 마감일
    private String keyword;  //분류
    private String salary;   //연봉

    public JobCompanyData getCompany() {return company;}
    public JobContentData getPosition() {return position;}
    public String getSiteUrl() {
        return mSiteUrl;
    }
    public int getStart() { return mStart; }
    public int getEnd() { return mEnd; }
    public String getKeyword() {return keyword;}
    public String getSalary() {return salary;}

    public JobData() {
        mLogoResourceId = android.R.mipmap.sym_def_app_icon;
    }

    private String qualification;
    private String conditions;
    private String period;
}
