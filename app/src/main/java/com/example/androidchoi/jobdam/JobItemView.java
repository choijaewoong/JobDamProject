package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.Job;

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

//    public void setItemData(JobData data){
//        mCorp.setText(data.getCompanyName().getName().getValue());
//        mTitle.setText(data.getPosition().getTitle());
//
//        Date start = new Date(data.getStart() * 1000L);
//        Date end = new Date(data.getEnd() * 1000L);
//
//        setPeriod(start, end);
//        setDDay(start, end);
//
////        mCorp.setSelected(true);
////        mTitle.setSelected(true);
//    }
//
//    public void setItemData(MyJob data){
//        mCorp.setText(data.getCompanyName());
//        mTitle.setText(data.getJobtitle());
//
//        Date start = new Date(data.getOpeningDate() * 1000L);
//        Date end = new Date(data.getClosingDate() * 1000L);
//
//        setPeriod(start, end);
//        setDDay(start, end);
//
////        mCorp.setSelected(true);
////        mTitle.setSelected(true);
//    }

    public void setPeriod(Date start, Date end){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        StringBuilder period = new StringBuilder();
        period.append(dateFormat.format(start))
                .append("~")
                .append(dateFormat.format(end));
        mPeriod.setText(period.toString());

    }

    public void setDDay(Date start, Date end){
        long day = (end.getTime()-start.getTime())/86400000;
        int d_day = (int)(day+1);
        mDDay.setText("d-" + d_day);
        if(d_day == 0){
            mDDay.setText( "d-day");
            mDDay.setTextColor(getResources().getColor(R.color.colorOver));
        } else if(d_day > 10){
            mDDay.setTextColor(getResources().getColor(R.color.colorSafe));
        } else if (d_day > 3)
            mDDay.setTextColor(getResources().getColor(R.color.colorWarning));
        else{
            mDDay.setTextColor(getResources().getColor(R.color.colorDanger));
        }
    }

    public void setItemData(Job itemData) {
        Date start = null;
        Date end = null;

        mCorp.setText(itemData.getCompanyName());
        mTitle.setText(itemData.getJobTitle());
        start = new Date(itemData.getStart() *1000L);
        end = new Date(itemData.getEnd() * 1000L);
//        if (itemData instanceof JobData) {
//            mCorp.setText(((JobData) itemData).getCompanyName().getName().getValue());
//            mTitle.setText(((JobData) itemData).getPosition().getTitle());
//
//            start = new Date(((JobData) itemData).getStart() * 1000L);
//            end = new Date(((JobData) itemData).getEnd() * 1000L);
//
//
//        } else if (itemData instanceof MyJob) {
//            mCorp.setText(((MyJob) itemData).getCompanyName());
//            mTitle.setText(((MyJob) itemData).getJobtitle());
//
//            start = new Date(((MyJob) itemData).getOpeningDate() * 1000L);
//            end = new Date(((MyJob) itemData).getClosingDate() * 1000L);
//        }
        setPeriod(start, end);
        setDDay(start, end);
    }
}
