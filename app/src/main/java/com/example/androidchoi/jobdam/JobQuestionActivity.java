package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.QuestionPagerAdapter;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.QuestionData;
import com.example.androidchoi.jobdam.Model.Questions;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.viewpagerindicator.CirclePageIndicator;

public class JobQuestionActivity extends AppCompatActivity {
    public static final String EXTRA_QUESTION_NUM = "questionNumber";
    public static final String EXTRA_JOB_ID = "questionId";
    public static final String EXTRA_QUESTION_LIST = "questionList";
    public static final String EXTRA_CORP_NAME = "corpName";
    public static final int REQUEST_ADD_CARD = 1;

    TextView mTextToolbarTitle;
    ViewPager mViewPager;
    QuestionPagerAdapter mQuestionPagerAdapter;
    private Questions mQuestions;
    private String mCorpName;
    private int mQuestionPosition;
    private String mJobId;
    CirclePageIndicator mCirclePageIndicator;
    private FloatingActionMenu mFloatingActionMenu;
    private FloatingActionButton mFloatingAddCardButton;
    private FloatingActionButton mFloatingAddQuestionButton;
    private FloatingActionButton mFloatingDeleteQuestionButton;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_ADD_CARD) {
            CreateQuestionTask task = new CreateQuestionTask();
            task.execute();
        }
    }

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
        mQuestions = (Questions) intent.getSerializableExtra(EXTRA_QUESTION_LIST);
        mCorpName = intent.getStringExtra(EXTRA_CORP_NAME);
        mQuestionPosition = intent.getIntExtra(EXTRA_QUESTION_NUM, 0);
        mJobId = intent.getStringExtra(EXTRA_JOB_ID);
        mTextToolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        mTextToolbarTitle.setText(mCorpName);


        // 플로팅 액션 버튼 설정
        mFloatingActionMenu = (FloatingActionMenu)findViewById(R.id.floating_menu);
        mFloatingAddCardButton = (FloatingActionButton)findViewById(R.id.fab_add_card);
        mFloatingAddCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobQuestionActivity.this, CardChoiceActivity.class);
                intent.putExtra(EXTRA_QUESTION_NUM, mViewPager.getCurrentItem());
                intent.putExtra(EXTRA_JOB_ID, mJobId);
                startActivityForResult(intent, REQUEST_ADD_CARD);
                mFloatingActionMenu.close(true);
            }
        });
        mFloatingAddQuestionButton = (FloatingActionButton)findViewById(R.id.fab_add_question);
        mFloatingAddQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestion();
                mFloatingActionMenu.close(true);
            }
        });
        mFloatingDeleteQuestionButton = (FloatingActionButton)findViewById(R.id.fab_delete_question);
        mFloatingDeleteQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        CreateQuestionTask task = new CreateQuestionTask();
        task.execute();
    }

    class CreateQuestionTask extends AsyncTask<Void, Void, Void> {
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
            mViewPager.setCurrentItem(mQuestionPosition, true);
        }
    }
    @Override
         public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        else if (id == R.id.action_modify) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addQuestion(){
        NetworkManager.getInstance().addQuestion(JobQuestionActivity.this, mJobId, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                mQuestionPagerAdapter.addItem(new QuestionData());
                mCirclePageIndicator.notifyDataSetChanged();
                mViewPager.setCurrentItem(mQuestionPagerAdapter.getCount()-1);
            }
            @Override
            public void onFail(int code) {

            }
        });
    }
}
