package com.example.androidchoi.jobdam.Model;

/**
 * Created by Choi on 2015-11-12.
 */
public class MyJobs{
    private String _id;
    private MyJob job;

    public String getId() {
        return _id;
    }

    public MyJob getJob() {
        return job;
    }
    public void setJob(MyJob job) { this.job = job; }
}
