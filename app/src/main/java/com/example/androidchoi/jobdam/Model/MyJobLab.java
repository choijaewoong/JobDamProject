package com.example.androidchoi.jobdam.Model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Choi on 2015-11-12.
 */
public class MyJobLab {
    private static MyJobLab sMyJobLab;
    private Context mContext;

    @SerializedName("items")
    private ArrayList<MyJobs> mJobList;
    public ArrayList<MyJobs> getJobList() { return mJobList; }

    private MyJobLab(Context context) {
        mContext = context;
        mJobList = new ArrayList<MyJobs>();
    }
    private MyJobLab(Context context, ArrayList<MyJobs> jobList){
        mContext = context;
        mJobList = jobList;
        Collections.reverse(mJobList);
    }
    public static MyJobLab get(Context context){
        if(sMyJobLab == null){
            sMyJobLab = new MyJobLab(context.getApplicationContext());
        }
        return sMyJobLab;
    }
    public static MyJobLab get(Context context, MyJobLab myJobLab){
        if(sMyJobLab == null){
            sMyJobLab = new MyJobLab(context.getApplicationContext(), myJobLab.getJobList());
        }
        return sMyJobLab;
    }

    public MyJobs getJobData(int id){
        for(MyJobs j : mJobList){
            if(j.getJob().getId() == id)
                return j;
        }
        return null;
    }

    public void addJobData(MyJob job){
        MyJobs myJobs = new MyJobs();
        myJobs.setJob(job);

        mJobList.add(0, myJobs);
    }

}
