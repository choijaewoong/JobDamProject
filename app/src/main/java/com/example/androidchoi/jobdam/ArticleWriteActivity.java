package com.example.androidchoi.jobdam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class ArticleWriteActivity extends AppCompatActivity {

    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_write);

        mEditText = (EditText)findViewById(R.id.edit_text_article);
//        mEditText.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
