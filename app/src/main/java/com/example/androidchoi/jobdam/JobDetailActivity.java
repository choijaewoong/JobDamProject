package com.example.androidchoi.jobdam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.androidchoi.jobdam.Adpater.JobDetailAdapter;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.AddressData;
import com.example.androidchoi.jobdam.Model.ChildData;
import com.example.androidchoi.jobdam.Model.ContentData;
import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.Model.MyJob;
import com.example.androidchoi.jobdam.Model.PeriodData;
import com.example.androidchoi.jobdam.Model.QuestionLab;
import com.example.androidchoi.jobdam.Model.Questions;
import com.google.gson.Gson;

import java.util.ArrayList;

public class JobDetailActivity extends AppCompatActivity {

    public static final int REQUEST_ATTACH = 1;
    public static final int REQUEST_DETAIL = 2;
    private Job mData;
    private Questions mQuestions;
    private TextView mCorpName;
    private TextView mJobTitle;
    private ExpandableListView mExpandableListView;
    private JobDetailAdapter mExpandableAdapter;
    boolean isScrap;
    public Questions getQuestions() { return mQuestions; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        Intent intent = getIntent();
        mData = (Job) intent.getSerializableExtra(Job.JOBITEM);
        // 스크랩 확인 요청
//        isScrap = intent.getBooleanExtra(Job.JOB_SCRAP_CHECK, false);

        mExpandableListView = (ExpandableListView) findViewById(R.id.listview_job_detail_expandable);
        mExpandableAdapter = new JobDetailAdapter();
        mExpandableAdapter.setData(mData.getId(), mData.getCompanyName());
        // 헤더뷰 설정
        View corpHeaderView = getLayoutInflater().inflate(R.layout.view_header_job_detail_corp, null);
        View titleHeaderView = getLayoutInflater().inflate(R.layout.view_header_job_detail_title, null);
        ToggleButton scrapButton = (ToggleButton) titleHeaderView.findViewById(R.id.btn_detail_scrap);
        scrapButton.setChecked(isScrap);
        scrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isScrap){
                  //deleteMyJob
                } else {
                    MyJob job = new MyJob();
                    job.setData(mData);
                    Gson gson = new Gson();
                    final String json = gson.toJson(job);
                    NetworkManager.getInstance().addMyJob(JobDetailActivity.this, json, new NetworkManager.OnResultListener<String>() {
                        @Override
                        public void onSuccess(String result) {
                            showScrapToast();
                        }

                        @Override
                        public void onFail(int code) {
                        }
                    });
                }
                isScrap = !isScrap;
            }
        });
        Button corpLink = (Button) titleHeaderView.findViewById(R.id.btn_detail_move_homepage);
        corpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(mData.getCompanyLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(intent);
            }
        });

        mCorpName = (TextView) corpHeaderView.findViewById(R.id.text_detail_corp_name);
        mCorpName.setText(mData.getCompanyName());
        mJobTitle = (TextView) titleHeaderView.findViewById(R.id.text_detail_job_title);
        mJobTitle.setText(mData.getJobTitle());
        mExpandableListView.addHeaderView(corpHeaderView, null, false);
        mExpandableListView.addHeaderView(titleHeaderView, null, false);
        mExpandableListView.setAdapter(mExpandableAdapter);
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setDivider(ContextCompat.getDrawable(JobDetailActivity.this, android.R.color.transparent));
        mExpandableListView.setChildDivider(ContextCompat.getDrawable(JobDetailActivity.this, android.R.color.transparent));
        showJobQuestion();
    }

    //리스트뷰 메뉴 설정
    private void initJobDetailMenu() {
        ArrayList<ChildData> qualificationList = new ArrayList<ChildData>();
        qualificationList.add(new ContentData(getString(R.string.experience_level), mData.getExperienceLevel()));
        qualificationList.add(new ContentData(getString(R.string.education_level), mData.getEducationLevel()));
        mExpandableAdapter.add(getString(R.string.qualification), qualificationList);
        ArrayList<ChildData> conditionsList = new ArrayList<ChildData>();
        conditionsList.add(new ContentData(getString(R.string.location),mData.getLocation().replace(",", "<br>")));
        conditionsList.add(new ContentData(getString(R.string.salary),mData.getSalary()));
        mExpandableAdapter.add(getString(R.string.conditions), conditionsList);

        mExpandableAdapter.add(getString(R.string.period), new PeriodData(mData.getStart(), mData.getEnd()));
        mExpandableAdapter.add(getString(R.string.detail_page), new AddressData(mData.getSiteUrl()));
        mExpandableAdapter.addQuestion(getString(R.string.questions), mQuestions);
        for (int i = 0; i < mExpandableAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }
    }

//    public String getPeriod() {
//        Date end = new Date(mData.getEnd() * 1000L);
//        Calendar endDay = Calendar.getInstance();
//        endDay.setTime(end);
//        long gap = (endDay.getTimeInMillis() - Calendar.getInstance().getTimeInMillis())/JobItemView.ONE_DAY_TIME_STAMP;
//        if((int)gap > 200){
//            return "상시 모집";
//        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat("~ yyyy.MM.dd.E     HH:mm");
//        return "<strong>" + dateFormat.format(end) + "</strong>";
//    }

    public void showScrapToast(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.view_toast_scrap,
                (ViewGroup) findViewById(R.id.container_scrap_toast));
        Toast toast = new Toast(JobDetailActivity.this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public void showJobQuestion(){
        NetworkManager.getInstance().showJobQuestion(JobDetailActivity.this, mData.getId(), new NetworkManager.OnResultListener<QuestionLab>() {
            @Override
            public void onSuccess(QuestionLab result) {
                if (result != null) {
                    mQuestions = result.getQuestions();
                }
                initJobDetailMenu(); // 상세 채용 정보 카테고리 생성
            }
            @Override
            public void onFail(int code) {
            }
        });
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
