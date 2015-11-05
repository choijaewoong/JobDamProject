package com.example.androidchoi.jobdam.Model;

import com.begentgroup.xmlparser.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-05.
 */
public class AllJobListData {
    @SerializedName("job")
    ArrayList<JobData> jobList;
    public ArrayList<JobData> getJobList() {
        return jobList;
    }
}
