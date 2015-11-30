package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.google.gson.Gson;

public class ArticleWriteActivity extends AppCompatActivity {

    Article mArticle;
    EditText mEditText;

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

        View customToolbar = getLayoutInflater().inflate(R.layout.toolbar_write_article, null);
        ImageView imageEmotion = (ImageView)customToolbar.findViewById(R.id.image_article_emotion);
        imageEmotion .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ArticleWriteActivity.this, "emotion", Toast.LENGTH_SHORT).show();
            }
        });
        getSupportActionBar().setCustomView(customToolbar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        mEditText = (EditText)findViewById(R.id.edit_text_article);

        /* intent 가져오고 Data가 없다면
         새로 작성하는 article , Data가 있다면 article 수정 */
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
//            mArticle = new Article();
//            mArticle.setArticle(User.getInstance().getUserId(),
//                    0, false, mEditText.getText().toString(), 0);
//            Gson gson = new Gson();
//            final String jsonString = gson.toJson(mArticle);
            NetworkManager.getInstance().addArticle(ArticleWriteActivity.this, mEditText.getText().toString(), new NetworkManager.OnResultListener<Article>() {
                @Override
                public void onSuccess(Article result) {
                    Log.i("게시글 생성" , new Gson().toJson(result).toString());
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
