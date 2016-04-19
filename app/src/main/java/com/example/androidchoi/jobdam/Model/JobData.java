package com.example.androidchoi.jobdam.Model;

import com.begentgroup.xmlparser.SerializedName;
import com.example.androidchoi.jobdam.R;

import java.io.Serializable;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobData extends Job implements Serializable {

    @SerializedName("id")
    private String job_id;
    private int mLogoResourceId;
    private JobCompanyData company;
    private JobContentData position;
    private String url; // 사이트 주소
    @SerializedName("opening-timestamp")
    private int openingTimestamp; // 시작일
    @SerializedName("expiration-timestamp")
    private int expirationTimestamp; // 마감일
    private String salary;   //연봉
    private String companyImage;

    @Override
    public void init(){}
    @Override
    public String getId() { return job_id; }
    @Override
    public String getCompanyName() {return company.getName().getValue();}
    @Override
    public String getCompanyLink() { return company.getName().getLink(); }
    @Override
    public String getIndustryCode() {
        String code = position.getIndustry().getCode().split(",")[0];
        switch (code.length()){
            case 3 :
                return code.substring(0,1);
            case 4 :
                return code.substring(0,2);
            case 0 :
                return "10";
        }
        return position.getIndustry().getCode().substring(0,1); }
    @Override
    public String getSiteUrl() { return url; }
    @Override
    public String getJobTitle() { return position.getTitle(); }
    @Override
    public String getLocation() { return position.getLocation(); }
    @Override
    public String getExperienceLevel() { return position.getExperienceLevel(); }
    @Override
    public String getEducationLevel() { return position.getEducationLevel(); }

    @Override
    public String getCompanyImage() {
        return companyImage;
    }
    @Override
    public void setCompanyImage(String image) {
        companyImage = image;
    }
    @Override
    public int getStart() { return openingTimestamp; }
    @Override
    public int getEnd() { return expirationTimestamp; }
    @Override
    public String getSalary() {return salary;}

    public JobData() {
        company = new JobCompanyData();
        position = new JobContentData();
        salary = "";
        mLogoResourceId = R.drawable.image_default_corp_logo;
    }

    @Override
    public String toString() {
        return getCompanyName() + "/"
                + getJobTitle();
    }


}
