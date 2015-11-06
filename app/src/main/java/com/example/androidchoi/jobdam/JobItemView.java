package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.JobData;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobItemView extends RelativeLayout{

    TextView mCorp;
    TextView mTitle;
    TextView mPeriod;
    TextView mDDay;

    public JobItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_job_item, this);
        mCorp = (TextView)findViewById(R.id.text_corp);
        mTitle = (TextView)findViewById(R.id.text_job_title);
        mPeriod = (TextView)findViewById(R.id.text_period);
        mDDay = (TextView)findViewById(R.id.text_job_dday);
    }

    public void setItemData(JobData data){
        mCorp.setText(data.getCompany().getName());
        mTitle.setText(data.getPosition().getTitle());

        Date start = new Date(data.getStart() * 1000L);
        Date end = new Date(data.getEnd() * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        StringBuilder period = new StringBuilder();
        period.append(dateFormat.format(start))
                .append("~")
                .append(dateFormat.format(end));
        mPeriod.setText(period.toString());
        long d_day = (end.getTime()-start.getTime())/86400000;
        mDDay.setText( "d-" + (d_day+1));
//        mCorp.setSelected(true);
//        mTitle.setSelected(true);
    }
}
