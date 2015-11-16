package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.MyCardLab;
import com.example.androidchoi.jobdam.Model.MyCard;
import com.google.gson.Gson;

public class CardWriteActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_DATA = "card data";
    public static final String EXTRA_CARD_POSITION = "card position";


    MyCard mData;
    EditText mTitle;
    EditText mContent;
    LinearLayout mCancelSaveLayout;
    TextView mCancelButton;
    TextView mSaveButton;
    boolean isNew;
    boolean isFocused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_write);

        mTitle = (EditText) findViewById(R.id.edit_text_card_title);
        mContent = (EditText) findViewById(R.id.edit_text_card_content);
        mCancelSaveLayout = (LinearLayout) findViewById(R.id.linearLayout_cancel_save_button);
        mCancelButton = (TextView) findViewById(R.id.text_cancel_card);

        Intent intent = getIntent();
        isNew = intent.getBooleanExtra(MyCard.CARD_NEW, true);
        mData = (MyCard) intent.getSerializableExtra(MyCard.CARD_ITEM);
        // 기존 Data있는 경우
        if (mData != null) {
            mCancelSaveLayout.setVisibility(View.GONE);
            mTitle.setText(mData.getTitle());
            mContent.setText(mData.getContent());
        }

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSaveButton = (TextView) findViewById(R.id.text_save_card);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String jsonString;
                if (mData == null) {  // add CardData
                    mData = new MyCard();
                    jsonString = jsonStringFromData();
                    NetworkManager.getInstance().addMemo(CardWriteActivity.this, jsonString, new NetworkManager.OnResultListener<String>() {
                        @Override
                        public void onSuccess(String result) {
//                            Toast.makeText(CardWriteActivity.this, json, Toast.LENGTH_SHORT).show();
//                        Log.i("dd", json);
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
//                            Toast.makeText(CardWriteActivity.this, json, Toast.LENGTH_SHORT).show();
//                        Log.i("dd", json);
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
        mTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!isFocused) {
                    changeMode();
                }
            }
        });//
        mContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!isFocused) {
                    changeMode();
                }
            }
        });
    }

    public String jsonStringFromData() {
        mData.setData(mTitle.getText().toString()
                , mContent.getText().toString());
        MyCardLab.get(getApplicationContext()).modifyCardData(mData);
        Gson gson = new Gson();
        return gson.toJson(mData);
    }

    public void changeMode() {
        isFocused = true;
        mCancelSaveLayout.setVisibility(View.VISIBLE);
    }
}
