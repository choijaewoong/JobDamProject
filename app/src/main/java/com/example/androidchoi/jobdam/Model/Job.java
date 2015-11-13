package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;

/**
 * Created by Choi on 2015-11-12.
 */
public abstract class Job implements Serializable{
    public static final String JOBITEM = "job_item";
    public static final String JOBID = "job_id";

    public abstract void init();
    public abstract int getId();
    public abstract String getCompanyName();
    public abstract String getCompanyLink();
    public abstract String getSiteUrl();
    public abstract  int getStart();
    public abstract  int getEnd();
    public abstract String getSalary();

    public abstract String getJobTitle();
    public abstract String getLocation();
    public abstract String getExperienceLevel();
    public abstract String getEducationLevel();

}
