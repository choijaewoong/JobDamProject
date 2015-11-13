package com.example.androidchoi.jobdam.Model;

import com.begentgroup.xmlparser.SerializedName;

import java.io.Serializable;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobData extends Job implements Serializable {

    private int id;
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

    @Override
    public void init(){}
    @Override
    public int getId() { return id; }
    @Override
    public String getCompanyName() {return company.getName().getValue();}
    @Override
    public String getCompanyLink() { return company.getName().getLink(); }
    @Override
    public String getSiteUrl() { return mSiteUrl; }
    @Override
    public String getJobTitle() { return position.getTitle(); }
    @Override
    public String getLocation() { return position.getLocation(); }
    @Override
    public String getExperienceLevel() { return position.getExperienceLevel(); }
    @Override
    public String getEducationLevel() { return position.getEducationLevel(); }

    @Override
    public int getStart() { return mStart; }
    @Override
    public int getEnd() { return mEnd; }
    public String getKeyword() {return keyword;}
    @Override
    public String getSalary() {return salary;}

    public JobData() {
        mLogoResourceId = android.R.mipmap.sym_def_app_icon;
    }

    @Override
    public String toString() {
        return getCompanyName() + "/"
                + getJobTitle();
    }


}
