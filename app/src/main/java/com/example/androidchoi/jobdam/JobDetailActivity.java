package com.example.androidchoi.jobdam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.JobDetailAdapter;
import com.example.androidchoi.jobdam.Model.AddressData;
import com.example.androidchoi.jobdam.Model.ContentData;
import com.example.androidchoi.jobdam.Model.JobData;

public class JobDetailActivity extends AppCompatActivity {

    JobData mData;
    TextView mTextView;
    ExpandableListView mExpandableListView;
    JobDetailAdapter mExpandableAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        mData = (JobData)intent.getSerializableExtra(JobData.JOBITEM);

        mExpandableListView = (ExpandableListView)findViewById(R.id.listview_job_detail_expandable);
        mExpandableAdapter = new JobDetailAdapter();
        View corpHeaderView = getLayoutInflater().inflate(R.layout.view_job_detail_corp_header, null);
        View titleHeaderView = getLayoutInflater().inflate(R.layout.view_job_detail_title_header,null);
        mExpandableListView.addHeaderView(corpHeaderView);
        mExpandableListView.addHeaderView(titleHeaderView);
        mExpandableListView.setAdapter(mExpandableAdapter);
        mExpandableListView.setGroupIndicator(null);
        initJobDetailMenu();
        for(int i=0; i<mExpandableAdapter.getGroupCount(); i++){
            mExpandableListView.expandGroup(i);
        }
    }

    private void initJobDetailMenu() {
        ContentData data = new ContentData("ddddcvasdfd");
        ContentData data3 = new ContentData("dd234ddasdfd");
        ContentData data4 = new ContentData("dddd33asdfd");
        ContentData data5 = new ContentData("ddddasdfd");
        AddressData data2 = new AddressData("http://www.naver.com");
//        JobDetailGroupData menu = new JobDetailGroupData("지원자격",data);
        mExpandableAdapter.add("지원자격", data);
        mExpandableAdapter.add("근무조건", data3);
        mExpandableAdapter.add("접수기간", data4);
        mExpandableAdapter.add("상세페이지", new AddressData(mData.getSiteUrl()));
        mExpandableAdapter.add("자기소개서 항목", data5);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
