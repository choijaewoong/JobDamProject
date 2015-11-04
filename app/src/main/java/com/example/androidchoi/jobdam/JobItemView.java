package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.JobData;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobItemView extends RelativeLayout{

    JobData data;
    TextView mCorp;
    TextView mTitle;


    public JobItemView(Context context) {
        super(context);
        init();
    }

    private void init() {

        inflate(getContext(), R.layout.view_job_item, this);
        mCorp = (TextView)findViewById(R.id.text_corp);
        mTitle = (TextView)findViewById(R.id.text_job_title);
    }

    public void setItemData(JobData data){
        mCorp.setText(data.getCorporation());
        mTitle.setText(data.getJobTitle());
    }
}
