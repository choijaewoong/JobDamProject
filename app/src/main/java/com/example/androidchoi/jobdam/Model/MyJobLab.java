package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-12.
 */
public class MyJobLab {
    @SerializedName("items")
    private ArrayList<MyJobs> mJobList;
    public ArrayList<MyJobs> getJobList() { return mJobList; }
}
