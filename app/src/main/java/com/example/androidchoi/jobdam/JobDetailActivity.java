package com.example.androidchoi.jobdam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.JobItemData;

public class JobDetailActivity extends AppCompatActivity {

    JobItemData mData;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        Intent intent = getIntent();
        mData = (JobItemData)intent.getSerializableExtra(JobItemData.JOBITEM);
        mTextView = (TextView)findViewById(R.id.detail_title);
        mTextView.setText(mData.getJobTitle());
    }
}
