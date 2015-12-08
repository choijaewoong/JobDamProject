package com.example.androidchoi.jobdam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.QuestionPagerAdapter;
import com.example.androidchoi.jobdam.Model.Questions;

public class JobQuestionActivity extends AppCompatActivity {

    TextView mTextToolbarTitle;
    ViewPager mViewPager;
    QuestionPagerAdapter mQuestionPagerAdapter;
    Questions mQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        mTextToolbarTitle = (TextView)findViewById(R.id.toolbar_title);

        Intent intent = getIntent();
        mQuestions = (Questions) intent.getSerializableExtra(ExpandableChildQuestionItemView.QUESTION_LIST);

        mViewPager = (ViewPager)findViewById(R.id.pager_job_question);
        mQuestionPagerAdapter = new QuestionPagerAdapter();
        mQuestionPagerAdapter.setItems(mQuestions);
        mViewPager.setAdapter(mQuestionPagerAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_save) {
            // 태그 순서 변경 사항 서버 저장
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}