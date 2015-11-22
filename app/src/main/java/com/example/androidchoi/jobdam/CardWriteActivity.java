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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.Model.MyCard;
import com.example.androidchoi.jobdam.Model.MyCards;
import com.google.gson.Gson;

public class CardWriteActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_DATA = "card data";
    public static final String EXTRA_CARD_POSITION = "card position";
    private static final String CATEGORY_DIALOG = "category_dialog";
    private static final String Calendar_DIALOG = "calendar_dialog";

    private MyCards mData;
    ImageView mCategoryImage; //카테고리 선택 버튼
    TextView mTextCategory; // 카테고리 이름
    ImageView mImageCategory; //카테고리 색 바
    ScrollView scrollView;
    LinearLayout mCancelSaveLayout; // 취소, 저장 버튼
    TextView mCancelButton; // 취소 버튼
    TextView mSaveButton; // 저장 버튼
    TextView mTextStartDate;
    TextView mTextEndDate;
    boolean isNew;
    boolean isStartDate = true;

    //작성 기능
    EditText mEditTitle;
    EditText mEditContent;
    TextView mTextTitle;
    TextView mTextContent;


    public MyCard getData() {
        return mData.getCard();
    }

    public void setDate(String date) {
        if (isStartDate) {
            mData.getCard().setStartDate(date);
            mTextStartDate.setText(date);
        } else {
            mData.getCard().setEndDate(date);
            mTextEndDate.setText(date);
        }
    }

    // 카테고리 이름, 색 설정
    public void setCategory(int position) {
        mData.getCard().setCategory(position);
        CategoryData categoryData = CategoryData.get(getApplicationContext()).getCategoryList().get(position);
        mTextCategory.setText(categoryData.getName());
        mTextCategory.setTextColor(categoryData.getColor());
        mImageCategory.setBackgroundColor(categoryData.getColor());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_write);

        mCategoryImage = (ImageView) findViewById(R.id.image_category_select);
        mTextCategory = (TextView) findViewById(R.id.text_card_category_title);
        mImageCategory = (ImageView) findViewById(R.id.image_card_category_color);
        scrollView = (ScrollView) findViewById(R.id.scroll_view_content);
        mCancelSaveLayout = (LinearLayout) findViewById(R.id.linearLayout_cancel_save_button);
        mCancelButton = (TextView) findViewById(R.id.text_cancel_card);
        mSaveButton = (TextView) findViewById(R.id.text_save_card);
        mTextStartDate = (TextView) findViewById(R.id.text_start_date);
        mTextEndDate = (TextView) findViewById(R.id.text_end_date);
        mEditTitle = (EditText) findViewById(R.id.edit_text_card_title);
        mEditContent = (EditText) findViewById(R.id.edit_text_card_content);
        mTextTitle = (TextView) findViewById(R.id.text_view_card_title);
        mTextContent = (TextView) findViewById(R.id.text_view_card_content);

        Intent intent = getIntent();
        isNew = intent.getBooleanExtra(MyCard.CARD_NEW, true);
        // 기존 Data있는 경우 (메모 수정)
        if (isNew == false) {
            mData = (MyCards) intent.getSerializableExtra(MyCard.CARD_ITEM);
            mCancelSaveLayout.setVisibility(View.GONE);
            mTextTitle.setText(mData.getCard().getTitle());
            mTextContent.setText(mData.getCard().getContent());
        } else { // 기존 Data없는 경우 (메모 추가)
            mData = new MyCards();
            changeWriteMode();
            mEditContent.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEditContent, InputMethodManager.SHOW_IMPLICIT);
        }

        // 카테고리 이름, 색 설정
        CategoryData categoryData = CategoryData.get(getApplicationContext()).getCategoryList().get(mData.getCard().getCategory());
        mTextCategory.setText(categoryData.getName());
        mTextCategory.setTextColor(categoryData.getColor());
        mImageCategory.setBackgroundColor(categoryData.getColor());

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
                if (isNew == true) {  // add CardData
                    jsonString = addJsonString();
                    NetworkManager.getInstance().addMemo(CardWriteActivity.this, jsonString, new NetworkManager.OnResultListener<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(CardWriteActivity.this, jsonString, Toast.LENGTH_SHORT).show();
                            Log.i("생성", jsonString);
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                        @Override
                        public void onFail(int code) {
                            Toast.makeText(CardWriteActivity.this, "실패.", Toast.LENGTH_SHORT).show();
                            setResult(Activity.RESULT_CANCELED);
                            finish();
                        }
                    });
                } else { //modify CardData
                    jsonString = modifyJsonString();
                    NetworkManager.getInstance().updateMemo(CardWriteActivity.this, jsonString, new NetworkManager.OnResultListener<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(CardWriteActivity.this, jsonString, Toast.LENGTH_SHORT).show();
                            Log.i("수정", jsonString);
                            setResult(Activity.RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFail(int code) {
                            Toast.makeText(CardWriteActivity.this, "실패.", Toast.LENGTH_SHORT).show();
                            setResult(Activity.RESULT_CANCELED);
                            finish();
                        }
                    });
                }

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
        mTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = true;
                CustomCalendarDialogFragment dialog = new CustomCalendarDialogFragment();
                dialog.show(getSupportFragmentManager(), CATEGORY_DIALOG);
            }
        });
        mTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = false;
                CustomCalendarDialogFragment dialog = new CustomCalendarDialogFragment();
                dialog.show(getSupportFragmentManager(), CATEGORY_DIALOG);
            }
        });
        mCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getSupportFragmentManager(), CATEGORY_DIALOG);
            }
        });
    }

    public String addJsonString() {
        mData.getCard().setData(mEditTitle.getText().toString()
                , mEditContent.getText().toString());
        Gson gson = new Gson();
        return gson.toJson(mData.getCard());
    }

    public String modifyJsonString() {
        mData.getCard().setData(mEditTitle.getText().toString()
                , mEditContent.getText().toString());
        Gson gson = new Gson();
        return gson.toJson(mData);
    }

    public void changeWriteMode() {
        mCancelSaveLayout.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        mTextTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);
        mEditContent.setVisibility(View.VISIBLE);
        mEditTitle.setText(mTextTitle.getText());
        mEditContent.setText(mTextContent.getText());
    }
}
