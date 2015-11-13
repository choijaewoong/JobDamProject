package com.example.androidchoi.jobdam.Model;

import android.content.Context;

import com.begentgroup.xmlparser.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-10.
 */
public class JobList {
    private static JobList sJobList;
    private Context mContext;

    @SerializedName("job")
    private ArrayList<JobData> mJobList;

    private JobList(Context context){
        mContext = context;
        mJobList = new ArrayList<JobData>();
        //데이터 추가가
//        for (int i = 0; i < 10; i++) {
//            // 네트워크 매니저를 통해 데이터를 생성해서 가져옴.
//            JobData data = new JobData();
//            addJobData(data);
//        }
    }
    public static JobList get(Context context){
        if(sJobList == null){
            sJobList = new JobList(context.getApplicationContext());
        }
        return sJobList;
    }

    public ArrayList<JobData> getJobList(){
        return mJobList;
    }

    public JobData getJobData(int id){
        for(JobData j : mJobList){
            if(j.getId() == id)
                return j;
        }
        return null;
    }

    public void addJobData(JobData job){
        mJobList.add(0, job);
    }
}
