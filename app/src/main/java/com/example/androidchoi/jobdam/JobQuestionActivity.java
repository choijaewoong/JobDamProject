package com.example.androidchoi.jobdam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.QuestionPagerAdapter;
import com.example.androidchoi.jobdam.ItemView.ExpandableChildQuestionItemView;
import com.example.androidchoi.jobdam.Model.Questions;
import com.viewpagerindicator.CirclePageIndicator;

public class JobQuestionActivity extends AppCompatActivity {

    TextView mTextToolbarTitle;
    ViewPager mViewPager;
    QuestionPagerAdapter mQuestionPagerAdapter;
    Questions mQuestions;
    String mCorpName;
    CirclePageIndicator mCirclePageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        Intent intent = getIntent();
        mQuestions = (Questions) intent.getSerializableExtra(ExpandableChildQuestionItemView.QUESTION_LIST);
        mCorpName = intent.getStringExtra(ExpandableChildQuestionItemView.CORP_NAME);
        mTextToolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        mTextToolbarTitle.setText(mCorpName);

        MyTask task = new MyTask();
        task.execute();
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mViewPager = (ViewPager)findViewById(R.id.pager_job_question);
            mQuestionPagerAdapter = new QuestionPagerAdapter();
            mQuestionPagerAdapter.setItems(mQuestions);
            mCirclePageIndicator = (CirclePageIndicator)findViewById(R.id.pager_indicator);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mViewPager.setOffscreenPageLimit(mQuestions.getQuestionList().size());
            mViewPager.setAdapter(mQuestionPagerAdapter);
            mCirclePageIndicator.setViewPager(mViewPager);
        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_save, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        else if (id == R.id.action_save) {
            // 태그 순서 변경 사항 서버 저장
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
