package com.example.androidchoi.jobdam.Model;

import android.content.Context;

import com.begentgroup.xmlparser.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-05.
 */
public class AllJobListData {
    private static MyJobLab sMyJobLab;
    private Context mContext;
    @SerializedName("job")
    private ArrayList<JobData> jobList;
    public ArrayList<JobData> getJobList() {
        return jobList;
    }
}
