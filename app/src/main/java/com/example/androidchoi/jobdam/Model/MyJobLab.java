package com.example.androidchoi.jobdam.Model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-10.
 */
public class MyJobLab {
    private static MyJobLab sMyJobLab;
    private Context mContext;

    private ArrayList<JobData> mJobList;

    private MyJobLab(Context context){
        mContext = context;
        mJobList = new ArrayList<JobData>();
        //데이터 추가가
//        for (int i = 0; i < 10; i++) {
//            // 네트워크 매니저를 통해 데이터를 생성해서 가져옴.
//            JobData data = new JobData();
//            addJobData(data);
//        }
    }
    public static MyJobLab get(Context context){
        if(sMyJobLab == null){
            sMyJobLab = new MyJobLab(context.getApplicationContext());
        }
        return sMyJobLab;
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
