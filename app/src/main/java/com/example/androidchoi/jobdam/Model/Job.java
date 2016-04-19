package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;

/**
 * Created by Choi on 2015-11-12.
 */
public abstract class Job implements Serializable{
    public static final String JOBITEM = "job_item";
    public static final String JOB_SCRAP_CHECK = "job_scrap_check";
    public static final String JOBID = "job_id";

    public abstract void init();
    public abstract String getId();
    public abstract String getCompanyName();
    public abstract String getCompanyLink();
    public abstract String getIndustryCode();
    public abstract String getSiteUrl();
    public abstract  int getStart();
    public abstract  int getEnd();
    public abstract String getSalary();

    public abstract String getJobTitle();
    public abstract String getLocation();
    public abstract String getExperienceLevel();
    public abstract String getEducationLevel();
    public abstract String getCompanyImage();
    public abstract void setCompanyImage(String image);

}
