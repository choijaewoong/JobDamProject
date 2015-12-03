package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import com.example.androidchoi.jobdam.Model.ContentData;
import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.Model.MyCards;
import com.example.androidchoi.jobdam.Model.MyJob;
import com.example.androidchoi.jobdam.Model.QuestionLab;
import com.example.androidchoi.jobdam.Model.Questions;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JobDetailActivity extends AppCompatActivity {

    public static final int REQUEST_ATTACH = 1;
    Job mData;
    Questions mQuestions;
    TextView mCorpName;
    TextView mJobTitle;
    ExpandableListView mExpandableListView;
    JobDetailAdapter mExpandableAdapter;
    boolean isScrap;

    // 날짜 체크 콜백
    public interface OnAddCardCallback {
        void onAddCardTag(List<MyCards> myCardList, int position);
    }
    private OnAddCardCallback mCallback;
    public void setOnAddCardCallback(OnAddCardCallback callback){
        mCallback = callback;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){ return; }
        if(requestCode == REQUEST_ATTACH){
            int position = data.getIntExtra(CardChoiceActivity.QUESTION_NUM, 0);
            List<MyCards> myCardList = (List<MyCards>) data.getSerializableExtra(CardChoiceActivity.CARD_TITLE);
//            mCallback.onAddCardTag(myCardList, position);
            mExpandableAdapter.notifyDataSetChanged();
        }
    }

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
        isScrap = intent.getBooleanExtra(Job.JOB_SCRAP_CHECK, false);

        mExpandableListView = (ExpandableListView) findViewById(R.id.listview_job_detail_expandable);
        mExpandableAdapter = new JobDetailAdapter(JobDetailActivity.this);
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
                            setResult(Activity.RESULT_OK);
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
        mExpandableAdapter.add(getString(R.string.qualification), new ContentData(getQualification()));
        mExpandableAdapter.add(getString(R.string.conditions), new ContentData(getConditions()));
        mExpandableAdapter.add(getString(R.string.period), new ContentData(getPeriod()));
        mExpandableAdapter.add(getString(R.string.detail_page), new AddressData(mData.getSiteUrl()));
        mExpandableAdapter.addQuestion(getString(R.string.questions), mQuestions);
        for (int i = 0; i < mExpandableAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }
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
        Calendar endDay = Calendar.getInstance();
        endDay.setTime(end);
        long gap = (endDay.getTimeInMillis() - Calendar.getInstance().getTimeInMillis())/JobItemView.ONE_DAY_TIME_STAMP;
        if((int)gap > 200){
            return "상시 모집";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("~ yyyy년 MM월 dd일 E요일 HH시 mm분");
        return dateFormat.format(end);
    }

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
