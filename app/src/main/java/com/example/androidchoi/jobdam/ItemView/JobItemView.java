package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Choi on 2015-10-18.
 */
public class JobItemView extends RelativeLayout implements Checkable {

    public static final int ONE_DAY_TIME_STAMP = 86400000;
    TextView mCorp;
    TextView mTitle;
    TextView mPeriod;
    TextView mDDay;
    TextView mTextQuestion;
    ImageView mImageJobLogo;
    RelativeLayout mLayout;
    boolean isChecked = false;

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
        mTextQuestion = (TextView) findViewById(R.id.text_show_question);
        mImageJobLogo = (ImageView)findViewById(R.id.image_job_logo);
        mLayout = (RelativeLayout) findViewById(R.id.layout_job_item_container);
    }

    public void setItemData(final Job itemData, boolean check) {
        mCorp.setText(itemData.getCompanyName());
        mTitle.setText(itemData.getJobTitle());
        setImageJob(itemData.getIndustryCode());
        setChecked(false);
        Date start = new Date(itemData.getStart() * 1000L);
        Date end = new Date(itemData.getEnd() * 1000L);
        boolean checkDeadLine = setDDay(end); // deadline이 없는 경우만 false를 받음.
        setPeriod(start, end, checkDeadLine);
        if (!check) { // 전체 채용 정보인 경우 항목보기 버튼 안보이도록 함.
            mTextQuestion.setVisibility(GONE);
        }
    }

    private void setImageJob(String code){
        switch (code){
            case "1":
                mImageJobLogo.setImageResource(R.drawable.image_industry_1);
                break;
            case "2":
                mImageJobLogo.setImageResource(R.drawable.image_industry_10);
                break;
            case "3":
                mImageJobLogo.setImageResource(R.drawable.image_industry_3);
                break;
            case "4":
                mImageJobLogo.setImageResource(R.drawable.image_industry_10);
                break;
            case "5":
                mImageJobLogo.setImageResource(R.drawable.image_industry_10);
                break;
            case "6":
                mImageJobLogo.setImageResource(R.drawable.image_industry_6);
                break;
            case "7":
                mImageJobLogo.setImageResource(R.drawable.image_industry_10);
                break;
            case "8":
                mImageJobLogo.setImageResource(R.drawable.image_industry_10);
                break;
            case "9":
                mImageJobLogo.setImageResource(R.drawable.image_industry_9);
                break;
            default:
                mImageJobLogo.setImageResource(R.drawable.image_industry_10);
                break;
        }
    }

    // period 계산 method
    private void setPeriod(Date start, Date end, boolean checkDeadLine) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        StringBuilder period = new StringBuilder();
        period.append(dateFormat.format(start)).append(" ~ ");
        if (checkDeadLine) {
            period.append(dateFormat.format(end));
        } else {
            period.append("채용시까지");
        }
        mPeriod.setText(period.toString());
    }

    // d-day 계산 method
    private boolean setDDay(Date end) {
        Calendar endDay = Calendar.getInstance();
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(endDay.get(Calendar.YEAR), endDay.get(Calendar.MONTH), endDay.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        endDay.setTime(end);
        long endTime = endDay.getTimeInMillis();
        long todayTime = currentDay.getTimeInMillis();
        long timeGap = (endTime + 1000) - todayTime;
        int d_day = (int) (timeGap / ONE_DAY_TIME_STAMP);
        mDDay.setText("D-" + d_day);
        if (timeGap < 0) {
            mDDay.setText("마감");
            mDDay.setBackgroundResource(R.drawable.image_dday_box_end);
            mDDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_size_small));
            return true;
        }
        if (d_day == 0) {
            mDDay.setText("D-day");
            mDDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_size_min));
            mDDay.setBackgroundResource(R.drawable.image_dday_box_danger);
            return true;
        } else if (d_day < 7) {
            mDDay.setBackgroundResource(R.drawable.image_dday_box_danger);
        } else if (d_day < 15) {
            mDDay.setBackgroundResource(R.drawable.image_dday_box_warning);
        } else if (d_day > 200) {
            mDDay.setText("상시");
            mDDay.setBackgroundResource(R.drawable.image_dday_box_always);
            return false; // deadline이 없는 경우 false 리턴
        } else {
            mDDay.setBackgroundResource(R.drawable.image_dday_box_default);
            if (d_day > 99) {
                mDDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_size_min));
                return true;
            }
        }
        mDDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_size_small));
        return true;
    }

    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
        if (checked) {
            mLayout.setSelected(true);
        } else {
            mLayout.setSelected(false);
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
