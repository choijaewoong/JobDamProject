package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.androidchoi.jobdam.Adpater.JobDetailAdapter;
import com.example.androidchoi.jobdam.Manager.MyApplication;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class JobDetailActivity extends AppCompatActivity {

    public static final int REQUEST_ATTACH = 1;
    public static final int REQUEST_DETAIL = 2;
    private Job mData;
    private Questions mQuestions;
    private ImageView mCorpLogo;
    private TextView mCorpName;
    private TextView mJobTitle;
    private ExpandableListView mExpandableListView;
    private JobDetailAdapter mExpandableAdapter;
    boolean isScrap;
    public Questions getQuestions() { return mQuestions; }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if(requestCode == JobDetailActivity.REQUEST_ATTACH) {
            showJobQuestion();
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
                if (isScrap) {
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

        mCorpLogo = (ImageView)corpHeaderView.findViewById(R.id.image_detail_corp_logo);
        MyTask myTask = new MyTask(mData);
        myTask.setOnImagListener(new MyTask.OnImageListener() {
            @Override
            public void onSuccess(String img) {
                if(img != null) {
                    Glide.with(MyApplication.getContext()).load(img).into(mCorpLogo);
                }else{
                    mCorpLogo.setImageResource(R.drawable.image_default_corp_logo);
                }
            }
        });
        myTask.execute();
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

    static class MyTask extends AsyncTask<Void, Void, String> {
        Job job;
        public MyTask(Job job){
            this.job = job;
        }
        public interface OnImageListener {
            public void onSuccess(String img);
        }
        OnImageListener mListener;
        public void setOnImagListener(OnImageListener listener) {
            mListener = listener;
        }

        @Override
        protected String doInBackground(Void... params) {
            Document doc = null;
            Element img = null;
            try {
                    doc = Jsoup.connect(job.getSiteUrl()).get();
                    img = doc.select("table.corp_logo img").first();
                    if (img != null) {
////                    setCompanyImage(img.attr("src"));
//                        job.setCompanyImage(img.attr("src"));
                        return img.attr("src");
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String img) {
            super.onPostExecute(img);
            mListener.onSuccess(img);
        }
    }

    //리스트뷰 메뉴 설정
    private void initJobDetailMenu() {
        mExpandableAdapter.setClear();
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
        mExpandableAdapter.notifyDataSetChanged();
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
