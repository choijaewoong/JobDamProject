package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.CardData;
import com.example.androidchoi.jobdam.Model.CardLab;

public class CardWriteActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_DATA = "card data";
    public static final String EXTRA_CARD_POSITION = "card position";

    CardData mData;
    EditText mTitle;
    EditText mContent;
    LinearLayout mCancelSaveLayout;
    TextView mCancelButton;
    TextView mSaveButton;
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
        int cardId = intent.getIntExtra(CardData.CARD_ID, 0);
        mData = CardLab.get(getApplicationContext()).getCard(cardId);
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
