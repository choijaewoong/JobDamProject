package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.MyCard;
import com.example.androidchoi.jobdam.Model.MyCardLab;
import com.google.gson.Gson;

public class CardWriteActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_DATA = "card data";
    public static final String EXTRA_CARD_POSITION = "card position";

    MyCard mData;
    EditText mEditTitle;
    EditText mEditContent;
    TextView mTextTitle;
    TextView mTextContent;
    ScrollView scrollView;
    LinearLayout mCancelSaveLayout;
    TextView mCancelButton;
    TextView mSaveButton;
    boolean isNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_write);

        mEditTitle = (EditText) findViewById(R.id.edit_text_card_title);
        mEditContent = (EditText) findViewById(R.id.edit_text_card_content);
        mTextTitle = (TextView)findViewById(R.id.text_view_card_title);
        mTextContent = (TextView) findViewById(R.id.text_view_card_content);
        scrollView = (ScrollView)findViewById(R.id.scroll_view_content);

        mCancelSaveLayout = (LinearLayout) findViewById(R.id.linearLayout_cancel_save_button);
        mCancelButton = (TextView) findViewById(R.id.text_cancel_card);
        mSaveButton = (TextView) findViewById(R.id.text_save_card);

        Intent intent = getIntent();
        isNew = intent.getBooleanExtra(MyCard.CARD_NEW, true);
        mData = (MyCard) intent.getSerializableExtra(MyCard.CARD_ITEM);
        // 기존 Data있는 경우 (메모 수정)
        if (mData != null) {
            mCancelSaveLayout.setVisibility(View.GONE);
            mTextTitle.setText(mData.getTitle());
            mTextContent.setText(mData.getContent());
        } else{ // 기존 Data없는 경우 (메모 추가)
            changeWriteMode();
            mEditContent.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEditContent, InputMethodManager.SHOW_IMPLICIT);
        }

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show Cancel Check Dialog
                finish();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String jsonString;
                if (mData == null) {  // add CardData
                    mData = new MyCard();
                    jsonString = jsonStringFromData();
                    NetworkManager.getInstance().addMemo(CardWriteActivity.this, jsonString, new NetworkManager.OnResultListener<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(CardWriteActivity.this, jsonString, Toast.LENGTH_SHORT).show();
//                             Log.i("dd", jsonString);
                        }

                        @Override
                        public void onFail(int code) {
                            Toast.makeText(CardWriteActivity.this, "실패.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else { //modify CardData
                    jsonString = jsonStringFromData();
                    NetworkManager.getInstance().updateMemo(CardWriteActivity.this, jsonString, new NetworkManager.OnResultListener<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(CardWriteActivity.this, jsonString, Toast.LENGTH_SHORT).show();
                        Log.i("dd", jsonString);
                        }
                        @Override
                        public void onFail(int code) {
                            Toast.makeText(CardWriteActivity.this, "실패.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        mTextTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeWriteMode();
                mEditTitle.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditTitle, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        mTextContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeWriteMode();
                mEditContent.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditContent, InputMethodManager.SHOW_IMPLICIT);

            }
        });
    }

    public String jsonStringFromData() {
        mData.setData(mEditTitle.getText().toString()
                , mEditContent.getText().toString());
        MyCardLab.get(getApplicationContext()).modifyCardData(mData);
        Gson gson = new Gson();
        return gson.toJson(mData);
    }
    public void changeWriteMode(){
        mCancelSaveLayout.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        mTextTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);
        mEditContent.setVisibility(View.VISIBLE);
        mEditTitle.setText(mTextTitle.getText());
        mEditContent.setText(mTextContent.getText());
    }
}
