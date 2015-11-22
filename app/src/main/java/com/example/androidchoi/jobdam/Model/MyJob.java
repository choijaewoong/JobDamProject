package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Choi on 2015-11-12.
 */
public class MyJob extends Job implements Serializable{

    private int job_id;
    private String url;
    private String posting_date;
    private int opening_date = 0;
    private int closing_date = 0;
    private String company;
    private String companySite;
    private String jobtitle;
    private String position;
    private String location;
    private String job_type;
    private String exprience_level;
    private String required_level;
    private int read_cnt;
    private int apply_cnt;
    private int reply_cnt;
    private String salary_code;
    private List<MyJobUser> scrap_userID;

    public MyJob() {
        init();
    }
    @Override
    public void init(){
        job_id = 0;
        company = "empty";
        jobtitle = "empty";
        opening_date = 0;
        closing_date = 0;
        location = "empty";
        exprience_level = "empty";
        required_level = "empty";
        salary_code = "empty";
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
    public String getSalary() { return salary_code; }
    @Override
    public String getJobTitle() { return jobtitle; }
    @Override
    public String getLocation() { return location; }
    @Override
    public String getExperienceLevel() { return exprience_level; }
    @Override
    public String getEducationLevel() { return required_level; }
    @Override
    public int getStart() {
        return opening_date;
    }
    @Override
    public int getEnd() {return closing_date;}

    public void setData(Job data){
     job_id = data.getId();
     company = data.getCompanyName();
     companySite = data.getCompanyLink();
     url = data.getSiteUrl();
     opening_date = data.getStart();
     closing_date = data.getEnd();
     salary_code = data.getSalary();
     jobtitle = data.getJobTitle();
     location = data.getLocation();
     exprience_level = data.getExperienceLevel();
     required_level = data.getEducationLevel();
    }
}
