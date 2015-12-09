package com.example.androidchoi.jobdam.Model;

import com.example.androidchoi.jobdam.JobItemView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Choi on 2015-11-04.
 */
public class PeriodData implements ChildData {
    private int mDday;
    private Date mStart;
    private Date mEnd;

    public int getDday() {
        return mDday;
    }

    public String getStart() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 HH시 mm분");
        return dateFormat.format(mStart);
    }

    public String getEnd() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 HH시 mm분");
        return dateFormat.format(mEnd);
    }

    public PeriodData(int StartTimeStamp, int EndTimeStamp) {
        mStart = new Date(StartTimeStamp * 1000L);
        mEnd = new Date(EndTimeStamp * 1000L);
        setDDay();
    }

    private void setDDay() {
        Calendar endDay = Calendar.getInstance();
        Calendar currentDay  = Calendar.getInstance();
        currentDay.set(endDay.get(Calendar.YEAR), endDay.get(Calendar.MONTH), endDay.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        endDay.setTime(mEnd);
        long endTime = endDay.getTimeInMillis();
        long todayTime = currentDay.getTimeInMillis();
        long timeGap = (endTime+1000) - todayTime;
        if(timeGap < 0) {
            mDday = -1;
            return;
        }
        int d_day = (int)(timeGap/ JobItemView.ONE_DAY_TIME_STAMP);
        mDday = d_day;
    }

}
