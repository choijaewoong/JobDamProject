package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.CardData;
import com.example.androidchoi.jobdam.Model.CardLab;
import com.google.gson.Gson;

public class CardWriteActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_DATA = "card data";
    public static final String EXTRA_CARD_POSITION = "card position";


    CardData mData;
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

        mTitle = (EditText)findViewById(R.id.edit_text_card_title);
        mContent = (EditText)findViewById(R.id.edit_text_card_content);
        mCancelSaveLayout = (LinearLayout)findViewById(R.id.linearLayout_cancel_save_button);
        mCancelButton = (TextView)findViewById(R.id.text_cancel_card);

        Intent intent = getIntent();
        isNew = intent.getBooleanExtra(CardData.CARD_NEW, true);
        mData = (CardData)intent.getSerializableExtra(CardData.CARD_ITEM);
        if(mData != null){
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
        mSaveButton = (TextView)findViewById(R.id.text_save_card);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData == null) {
                    mData = new CardData();
                    setData();
                    CardLab.get(getApplicationContext()).addCardData(mData);

                    Gson gson = new Gson();
                    final String json = gson.toJson(mData);
                    NetworkManager.getInstance().addMemo(CardWriteActivity.this, json, new NetworkManager.OnResultListener<String>(){
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(CardWriteActivity.this, json, Toast.LENGTH_SHORT).show();
                            Log.i("dd",json);

                        }

                        @Override
                        public void onFail(int code) {
                            Toast.makeText(CardWriteActivity.this, "실패.", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    setData();
                }
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        mTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!isFocused) {
                    changeMode();
                }
            }
        });//
        mContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!isFocused) {
                    changeMode();
                }
            }
        });
    }
    public void setData(){
        mData.setTitle(mTitle.getText().toString());
        mData.setContent(mContent.getText().toString());
    }

    public void changeMode(){
        isFocused = true;
        mCancelSaveLayout.setVisibility(View.VISIBLE);
    }
}
