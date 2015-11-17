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
    LinearLayout mCancelSaveLayout;
//    RelativeLayout mCardContainer;
    TextView mCancelButton;
    TextView mSaveButton;
    boolean isNew;
    boolean isFocused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_write);

        mEditTitle = (EditText) findViewById(R.id.edit_text_card_title);
        mEditContent = (EditText) findViewById(R.id.edit_text_card_content);
        mTextTitle = (TextView)findViewById(R.id.text_view_card_title);
        mTextContent = (TextView) findViewById(R.id.text_view_card_content);

        mCancelSaveLayout = (LinearLayout) findViewById(R.id.linearLayout_cancel_save_button);
//        mCardContainer = (RelativeLayout)findViewById(R.id.card_container);
        mCancelButton = (TextView) findViewById(R.id.text_cancel_card);
        mSaveButton = (TextView) findViewById(R.id.text_save_card);

        Intent intent = getIntent();
        isNew = intent.getBooleanExtra(MyCard.CARD_NEW, true);
        mData = (MyCard) intent.getSerializableExtra(MyCard.CARD_ITEM);
        // 기존 Data있는 경우
        if (mData != null) {
            mCancelSaveLayout.setVisibility(View.GONE);
            mTextTitle.setText(mData.getTitle());
            mTextContent.setText(mData.getContent());
        } else{
//            mEditTitle.setText(mData.getTitle());
//            mEditContent.setText(mData.getContent());
        }

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        Log.i("dd", jsonString);
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

//        final int[] x = new int[1];
//        final int[] y = new int[1];
//        mTextTitle.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                x[0] = (int)event.getX();
//                y[0] = (int)event.getY();
//
//                return true;
//            }
//        });

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
//                mTextContent.setCursorVisible(true);
//                mTextContent.setFocusableInTouchMode(true);
//                mTextContent.setEllipsize(TextUtils.TruncateAt.END);
//                mTextContent.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
//                mTextContent.requestFocus();
//                mEditContent.getSelectionStart();
                changeWriteMode();
                mEditContent.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditContent, InputMethodManager.SHOW_IMPLICIT);

            }
        });
        mEditTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!isFocused) {
                    changeMode();
                }
            }
        });//
        mEditContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!isFocused) {
                    changeMode();
                }
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

    public void changeMode() {
        isFocused = true;
        mCancelSaveLayout.setVisibility(View.VISIBLE);
    }

    public void changeWriteMode(){
        ScrollView scrollView = (ScrollView)findViewById(R.id.scroll_view_content);
        scrollView.setVisibility(View.GONE);
        mTextTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);
        mTextContent.setVisibility(View.GONE);
        mEditContent.setVisibility(View.VISIBLE);
        mEditTitle.setText(mTextTitle.getText());
        mEditContent.setText(mTextContent.getText());
    }
}
