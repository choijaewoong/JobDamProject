package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Article;
import com.example.androidchoi.jobdam.Model.EmotionData;

import java.util.Calendar;

public class ArticleWriteActivity extends AppCompatActivity {

    private static final String EMOTION_DIALOG = "emotion_dialog";

    Article mArticle;
    EditText mEditText;
    int mEmotionIndex;
    long mWriteTimeStamp;
    ImageView mImageEmotion;
    ImageView mImageArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_write);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

        final View customToolbar = getLayoutInflater().inflate(R.layout.toolbar_write_article, null);
        mImageEmotion = (ImageView)customToolbar.findViewById(R.id.image_article_emotion);
        mImageArrow = (ImageView)customToolbar.findViewById(R.id.image_emotion_arrow);
        mImageEmotion .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ArticleWriteActivity.this, "emotion", Toast.LENGTH_SHORT).show();

                mImageArrow.setImageResource(android.R.color.black);
                ArticleEmotionListDialogFragment dialog = new ArticleEmotionListDialogFragment();
                dialog.show(getSupportFragmentManager(), EMOTION_DIALOG);
            }
        });
        getSupportActionBar().setCustomView(customToolbar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        mEditText = (EditText)findViewById(R.id.edit_text_article);

        /* intent 가져오고 Data가 없다면 새로 작성하는 article
        Data가 있다면 article 수정 */
        if(mArticle != null){
//            mEditText.setText(mArticle.getContent());
        } else{

        }
        FrameLayout touchInterceptor = (FrameLayout)findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mEditText.isFocused()) {
                        Rect outRect = new Rect();
                        mEditText.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mEditText.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });
    }

    public void setEmotion(int position){
        mEmotionIndex = position;
        mImageEmotion.setImageResource(EmotionData.get(ArticleWriteActivity.this).getCategoryList().get(position).getImageResource());
    }

    public void setArrow(){
        mImageArrow.setImageResource(android.R.color.white);
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
        else if (id == R.id.action_save) {

            mWriteTimeStamp = Calendar.getInstance().getTimeInMillis();
//            Log.i("timestamp", timeStamp+"");

            NetworkManager.getInstance().addArticle(ArticleWriteActivity.this, mEditText.getText().toString(), mEmotionIndex, mWriteTimeStamp,
                    new NetworkManager.OnResultListener<String>() {
                @Override
                public void onSuccess(String result) {
                    setResult(Activity.RESULT_OK);
                    Toast.makeText(ArticleWriteActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFail(int code) {
                    Toast.makeText(ArticleWriteActivity.this, "실패.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
