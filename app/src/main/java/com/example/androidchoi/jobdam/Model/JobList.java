package com.example.androidchoi.jobdam.Model;
import com.begentgroup.xmlparser.SerializedName;
import java.util.ArrayList;
/**
 * Created by Choi on 2015-11-10.
 */
public class JobList {
    private int total = 1000;
    @SerializedName("job")
    private ArrayList<JobData> mJobList;

    public ArrayList<JobData> getJobList() {
        return mJobList;
    }
    public int getTotal() { return total; }
}

