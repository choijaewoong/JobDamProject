package com.example.androidchoi.jobdam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
import com.example.androidchoi.jobdam.Util.PredicateLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CardWriteActivity extends AppCompatActivity {

    private static final String CATEGORY_DIALOG = "category_dialog";
    public static final String CALENDAR_START_DIALOG = "calendar_start_dialog";
    public static final String CALENDAR_END_DIALOG = "calendar_end_dialog";

    private MyCards mData;
    TextView mTextCategory; // 카테고리 이름
    ImageView mImageCategory; //카테고리 색 바
    ScrollView scrollView;
    LinearLayout mCancelSaveLayout; // 취소, 저장 버튼
    PredicateLayout mPredicateLayout; //저장된 태그 보여주는 부분
    TextView mTextStartDate;
    TextView mTextEndDate;
    ArrayList<TextView> mTextTags = new ArrayList<TextView>();
    TextView mCancelButton;
    TextView mSaveButton;
    boolean isNew;

    //작성 기능
    EditText mEditTitle;
    EditText mEditContent;
    TextView mTextTitle;
    TextView mTextContent;
    EditText mEditTag;

    public MyCard getData() {
        return mData.getCard();
    }
    public void setDate(String date, boolean isStartDate) {
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
        Drawable drawable = ContextCompat.getDrawable(CardWriteActivity.this, R.drawable.image_category_color);
        drawable.setColorFilter(categoryData.getColor(), PorterDuff.Mode.MULTIPLY);
        for(TextView t : mTextTags){
            t.setBackground(drawable);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_write);

        mTextCategory = (TextView) findViewById(R.id.text_card_category_title);
        mImageCategory = (ImageView) findViewById(R.id.image_card_category_color);
        scrollView = (ScrollView) findViewById(R.id.scroll_view_content);
        mCancelSaveLayout = (LinearLayout) findViewById(R.id.linearLayout_cancel_save_button);
        mEditTitle = (EditText) findViewById(R.id.edit_text_card_title);
        mEditContent = (EditText) findViewById(R.id.edit_text_card_content);
        mTextTitle = (TextView) findViewById(R.id.text_view_card_title);
        mTextContent = (TextView) findViewById(R.id.text_view_card_content);
        mEditTag = (EditText)findViewById(R.id.edit_text_card_tag);
        mPredicateLayout =(PredicateLayout)findViewById(R.id.layout_job_question_tag);
        mTextStartDate = (TextView) findViewById(R.id.text_start_date);
        mTextEndDate = (TextView) findViewById(R.id.text_end_date);

        Intent intent = getIntent();
        isNew = intent.getBooleanExtra(MyCard.CARD_NEW, true);
        // 기존 Data있는 경우 (메모 수정)
        if (isNew == false) {
            mData = (MyCards) intent.getSerializableExtra(MyCard.CARD_ITEM);
            mCancelSaveLayout.setVisibility(View.GONE);
            mTextTitle.setText(mData.getCard().getTitle());
            mTextContent.setText(mData.getCard().getContent());
            mEditTitle.setText(mTextTitle.getText());
            mEditContent.setText(mTextContent.getText());
        } else { // 기존 Data없는 경우 (메모 추가)
            mData = new MyCards();
            changeWriteMode();
            mEditTitle.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEditTitle, InputMethodManager.SHOW_IMPLICIT);
        }
        // 활동기간
        mTextStartDate.setText(mData.getCard().getStartDate());
        mTextEndDate.setText(mData.getCard().getEndDate());

        // 카테고리 이름, 색 설정
        CategoryData categoryData = CategoryData.get(getApplicationContext()).getCategoryList().get(mData.getCard().getCategory());
        mTextCategory.setText(categoryData.getName());
        mTextCategory.setTextColor(categoryData.getColor());
        mTextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getSupportFragmentManager(), CATEGORY_DIALOG);
            }
        });
        mImageCategory.setBackgroundColor(categoryData.getColor());

        // 태그 등록
        for(String tag : mData.getCard().getTags()){
            addTagView(tag);
        }

        //취소,저장 버튼
        mCancelButton = (TextView) findViewById(R.id.text_cancel_card);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show Cancel Check Dialog
                finish();
            }
        });
        mSaveButton = (TextView) findViewById(R.id.text_save_card);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String jsonString;
                if (isNew == true) {  // add CardData
                    jsonString = addJsonString();
                    NetworkManager.getInstance().addMemo(CardWriteActivity.this, jsonString, new NetworkManager.OnResultListener<String>() {
                        @Override
                        public void onSuccess(String result) {
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
        mEditTag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mCancelSaveLayout.setVisibility(View.VISIBLE);
            }
        });
        mTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCalendarDialogFragment dialog = new CustomCalendarDialogFragment();
                dialog.show(getSupportFragmentManager(), CALENDAR_START_DIALOG);
            }
        });
        mTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCalendarDialogFragment dialog = new CustomCalendarDialogFragment();
                dialog.show(getSupportFragmentManager(), CALENDAR_END_DIALOG);
            }
        });
        ImageView mCategorySelectImage = (ImageView) findViewById(R.id.image_category_select);
        mCategorySelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getSupportFragmentManager(), CATEGORY_DIALOG);
            }
        });

        // 태그 + 버튼
        ImageView mImageAddTag = (ImageView)findViewById(R.id.btn_add_tag);
        mImageAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = mEditTag.getText().toString();
                String LengthCheck = tag.replace(" ", "");
                if(LengthCheck.length() != 0){
                    if(mData.getCard().getTags().size() > 5){
                        Toast.makeText(CardWriteActivity.this, "더 이상 추가 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        mData.getCard().addTag(tag);
                        addTagView(tag);
                    }
                }
                mEditTag.setText("");
            }
        });
    }

    //태그 더하기
    public void addTagView(String tag){
        final TextView t = new TextView(CardWriteActivity.this);
        t.setText(tag);
        t.setTextSize(12);
        t.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        Drawable drawable = ContextCompat.getDrawable(CardWriteActivity.this, R.drawable.image_category_color);
        drawable.setColorFilter(CategoryData.get(getApplicationContext()).getCategoryList().get(mData.getCard().getCategory()).getColor(), PorterDuff.Mode.MULTIPLY);
        t.setBackground(drawable);
        t.setPadding(10, 5, 10, 5);
        int width = getResources().getDimensionPixelSize(R.dimen.tag_max_width);
        t.setMaxWidth(width);
        t.setSingleLine(true);
        t.setEllipsize(TextUtils.TruncateAt.END);
        t.setGravity(Gravity.CENTER);
        t.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mCancelSaveLayout.setVisibility(View.VISIBLE);
                for(int i=0; i<mTextTags.size(); i++){
                    if(t == mTextTags.get(i)){
                        Toast.makeText(CardWriteActivity.this, "해당 태그가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        mData.getCard().removeTag(i);
                        mPredicateLayout.removeView(mTextTags.get(i));
                        mTextTags.remove(i);
                    }
                }
                return true;
            }
        });
        mTextTags.add(t);
        mPredicateLayout.addView(mTextTags.get(mTextTags.size() - 1));
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

    //작성 모드 변경
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
