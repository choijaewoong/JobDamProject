package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.Job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobItemView extends RelativeLayout {

    public static final int ONE_DAY_TIME_STAMP = 86400000;
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
        mCorp = (TextView) findViewById(R.id.text_corp);
        mTitle = (TextView) findViewById(R.id.text_job_title);
        mPeriod = (TextView) findViewById(R.id.text_period);
        mDDay = (TextView) findViewById(R.id.text_job_dday);
    }

    private void setPeriod(Date start, Date end, boolean checkDeadLine) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        StringBuilder period = new StringBuilder();
        period.append(dateFormat.format(start)).append(" ~ ");
        if(checkDeadLine){
            period.append(dateFormat.format(end));
        } else {
            period.append("채용시까지");
        }
        mPeriod.setText(period.toString());
    }

    private boolean setDDay(Date end) {
        Calendar endDay = Calendar.getInstance();
        Calendar currentDay  = Calendar.getInstance();
        currentDay.set(endDay.get(Calendar.YEAR), endDay.get(Calendar.MONTH), endDay.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        endDay.setTime(end);
        long endTime = endDay.getTimeInMillis();
        long todayTime = currentDay.getTimeInMillis();
        long dday = ((endTime+1000) - todayTime)/ONE_DAY_TIME_STAMP;
        int d_day = (int)dday;
        mDDay.setText("d-" + d_day);
        if (d_day == 0) {
            mDDay.setText("d-day");
            mDDay.setBackgroundResource(R.drawable.image_dday_box_danger);
        } else if (d_day < 7) {
            mDDay.setBackgroundResource(R.drawable.image_dday_box_danger);
        } else if (d_day < 15) {
            mDDay.setBackgroundResource(R.drawable.image_dday_box_warning);
        } else if (d_day > 200) {
            mDDay.setText("상시");
            mDDay.setBackgroundResource(R.drawable.image_dday_box_always);
            return false;
        } else {
            mDDay.setBackgroundResource(R.drawable.image_dday_box_default);
        }
        return true;
    }

    public void setItemData(Job itemData) {
        mCorp.setText(itemData.getCompanyName());
        mTitle.setText(itemData.getJobTitle());
        Date start = new Date(itemData.getStart() * 1000L);
        Date end = new Date(itemData.getEnd() * 1000L);

        setPeriod(start, end, setDDay(end));
    }
}
