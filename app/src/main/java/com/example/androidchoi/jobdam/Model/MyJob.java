package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;

/**
 * Created by Choi on 2015-11-12.
 */
public class MyJob extends Job implements Serializable{

    private int job_id;
    private String url;
    private String posting_date;
    private int openingTimestamp;
    private int expirationTimestamp;
    private String company;
    private String companySite;
    private String jobtitle;
    private String position;
    private String location;
    private String exprience_level;
    private String required_level;
    private String salary;
    private String companyImage;

    public MyJob() {
        init();
    }
    @Override
    public void init(){
        job_id = 0;
        company = "empty";
        jobtitle = "empty";
        openingTimestamp = 0;
        expirationTimestamp = 0;
        location = "empty";
        exprience_level = "empty";
        required_level = "empty";
        salary = "empty";
    }

    @Override
    public int getId() { return job_id; }
    @Override
    public String getCompanyName() {
        return company;
    }
    @Override
    public String getCompanyLink() { return companySite; }
    @Override
    public String getSiteUrl() { return url;}
    @Override
    public String getSalary() { return salary; }
    @Override
    public String getJobTitle() { return jobtitle; }
    @Override
    public String getLocation() { return location; }
    @Override
    public String getExperienceLevel() { return exprience_level; }
    @Override
    public String getEducationLevel() { return required_level; }

    @Override
    public String getCompanyImage() {
        return companyImage;
    }

    @Override
    public void setCompanyImage(String image) {
        companyImage = image;
    }

    @Override
    public int getStart() {
        return openingTimestamp;
    }
    @Override
    public int getEnd() {return expirationTimestamp;}

    public void setData(Job data){
     job_id = data.getId();
     company = data.getCompanyName();
     companySite = data.getCompanyLink();
     url = data.getSiteUrl();
     openingTimestamp = data.getStart();
    expirationTimestamp = data.getEnd();
     salary = data.getSalary();
     jobtitle = data.getJobTitle();
     location = data.getLocation();
     exprience_level = data.getExperienceLevel();
     required_level = data.getEducationLevel();
    companyImage = data.getCompanyImage();
    }
}
