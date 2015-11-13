package com.example.androidchoi.jobdam.Model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-12.
 */
public class MyJobList {

//    private MyJobs jobs;
//
//    public MyJobs getJobs() {
//        return jobs;
//    }
    private static MyJobList sMyJobList;
    private Context mContext;

    @SerializedName("items")
    private ArrayList<MyJobs> mJobList;
    public ArrayList<MyJobs> getJobList() { return mJobList; }

    private MyJobList(Context context) {
        mContext = context;
        mJobList = new ArrayList<MyJobs>();
    }
    private MyJobList(Context context, ArrayList<MyJobs> jobList){
        mContext = context;
        mJobList = jobList;
    }
    public static MyJobList get(Context context){
        if(sMyJobList == null){
            sMyJobList = new MyJobList(context.getApplicationContext());
        }
        return sMyJobList;
    }
    public static MyJobList get(Context context, MyJobList myJobList){
        if(sMyJobList == null){
            sMyJobList = new MyJobList(context.getApplicationContext(),myJobList.getJobList());
        }
        return sMyJobList;
    }

    public MyJobs getJobData(int id){
        for(MyJobs j : mJobList){
            if(j.getJob().getId() == id)
                return j;
        }
        return null;
    }

    public void addJobData(Job job){
        MyJob myJob = new MyJob();
        myJob.setData(job);

        MyJobs myJobs = new MyJobs();
        myJobs.setJob(myJob);

        mJobList.add(0, myJobs);
    }

}
