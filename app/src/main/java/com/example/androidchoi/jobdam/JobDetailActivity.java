package com.example.androidchoi.jobdam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.JobDetailAdapter;
import com.example.androidchoi.jobdam.Model.AddressData;
import com.example.androidchoi.jobdam.Model.ContentData;
import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.Model.MyJobList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobDetailActivity extends AppCompatActivity {

    public static final String EXTRA_JOB_DATA = "jobdata";

    Job mData;
    TextView mCorpName;
    TextView mJobTitle;
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
        mData = (Job)intent.getSerializableExtra(Job.JOBITEM);
//        if(mData == null){
//            int id = intent.getIntExtra(Job.JOBID, 0);
//            mData = MyJobLab.get(getApplicationContext()).getJobData(id);
//        }
//        Toast.makeText(JobDetailActivity.this, mData.getId()+" " ,Toast.LENGTH_SHORT).show();

        mExpandableListView = (ExpandableListView)findViewById(R.id.listview_job_detail_expandable);
        mExpandableAdapter = new JobDetailAdapter();
        // 헤더뷰 설정
        View corpHeaderView = getLayoutInflater().inflate(R.layout.view_job_detail_corp_header, null);
        View titleHeaderView = getLayoutInflater().inflate(R.layout.view_job_detail_title_header,null);
        Button scrapButton = (Button)titleHeaderView.findViewById(R.id.btn_detail_scrap);
        scrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyJobList.get(getApplicationContext()).addJobData(mData);
                Toast.makeText(JobDetailActivity.this, getString(R.string.check_scrap), Toast.LENGTH_SHORT).show();
//                if(MyJobLab.get(getApplication()).getJobData(mData.getId()) == null) {
//                    MyJobLab.get(getApplicationContext()).addJobData(mData);
//                    Toast.makeText(JobDetailActivity.this, getString(R.string.check_scrap), Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(JobDetailActivity.this, getString(R.string.check_not_scrap), Toast.LENGTH_SHORT).show();
//                }
            }
        });
        Button corpLink = (Button)titleHeaderView.findViewById(R.id.btn_detail_move_homepage);
        corpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(mData.getCompanyLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(intent);
            }
        });


        mCorpName = (TextView)corpHeaderView.findViewById(R.id.text_detail_corp_name);
        mCorpName.setText(mData.getCompanyName());
        mJobTitle = (TextView)titleHeaderView.findViewById(R.id.text_detail_job_title);
        mJobTitle.setText(mData.getJobTitle());
        mExpandableListView.addHeaderView(corpHeaderView);
        mExpandableListView.addHeaderView(titleHeaderView);

        mExpandableListView.setAdapter(mExpandableAdapter);
        mExpandableListView.setGroupIndicator(null);

        initJobDetailMenu(); // 상세 채용 정보 카테고리 생성
        for(int i=0; i<mExpandableAdapter.getGroupCount(); i++){
            mExpandableListView.expandGroup(i);
        }
    }

    private void initJobDetailMenu() {
        mExpandableAdapter.add(getString(R.string.qualification), new ContentData(getQualification()));
        mExpandableAdapter.add(getString(R.string.conditions), new ContentData(getConditions()));
        mExpandableAdapter.add(getString(R.string.period), new ContentData(getPeriod()));
        mExpandableAdapter.add(getString(R.string.detail_page), new AddressData(mData.getSiteUrl()));
        mExpandableAdapter.add(getString(R.string.questions), new ContentData("empty"));
    }

    public String getQualification() {
        return getString(R.string.experience_level) + mData.getExperienceLevel() + "\n"
                + getString(R.string.education_level) + mData.getEducationLevel();
    }
    public String getConditions() {
        return getString(R.string.location) + Html.fromHtml(mData.getLocation()) + "\n"
                + getString(R.string.salary) + mData.getSalary();
    }

    public String getPeriod() {
        Date end = new Date(mData.getEnd() * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("~ yyyy년 MM월 dd일 E요일 HH시 mm분");
        return dateFormat.format(end);
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
