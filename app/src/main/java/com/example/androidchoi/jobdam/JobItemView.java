package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.RelativeLayout;

import com.example.androidchoi.jobdam.Model.JobItemData;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobItemView extends RelativeLayout{

    JobItemData data;

    public JobItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_job_item, this);
    }

    public void setItemData(JobItemData data){
    }
}
