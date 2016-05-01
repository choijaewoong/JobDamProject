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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Dialog.CategoryListDialogFragment;
import com.example.androidchoi.jobdam.Dialog.SaveDialogFragment;
import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.Model.MyCard;
import com.example.androidchoi.jobdam.Model.MyCards;
import com.example.androidchoi.jobdam.Util.PredicateLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CardWriteActivity extends AppCompatActivity {
    public static final String CALENDAR_START_DIALOG = "calendar_start_dialog";
    public static final String CALENDAR_END_DIALOG = "calendar_end_dialog";

    private MyCards mData;
    TextView mTextCategory; // 카테고리 이름
    ImageView mImageCategoryBar; //카테고리 색 바
    ScrollView scrollView;
    LinearLayout mCancelSaveLayout; // 취소, 저장 버튼
    PredicateLayout mPredicateLayout; //저장된 태그 보여주는 부분
//    TextView mTextStartDate;
//    TextView mTextEndDate;
    ArrayList<TextView> mTextTags = new ArrayList<TextView>();
    TextView mCancelButton;
    TextView mSaveButton;
    boolean isNew;
    Spinner mSpinnerJobCompetence;
    Spinner mSpinnerWorkCompetence;
    Spinner mSpinnerAttitudeCompetence;
    ArrayAdapter<String>[] mArrayAdapters;

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
//        if (isStartDate) {
//            mData.getCard().setStartDate(date);
//            mTextStartDate.setText(date);
//        } else {
//            mData.getCard().setEndDate(date);
//            mTextEndDate.setText(date);
//        }
    }

    // 카테고리 이름, 색 설정
    public void setCategory(int position) {
        mData.getCard().setCategory(position);
        CategoryData categoryData = CategoryData.get(getApplicationContext()).getCategoryList().get(position);
        mTextCategory.setText(categoryData.getName());
        mTextCategory.setTextColor(categoryData.getColor());
        mImageCategoryBar.setBackgroundColor(categoryData.getColor());
        for(TextView t : mTextTags){
            Drawable drawable = ContextCompat.getDrawable(CardWriteActivity.this, R.drawable.image_category_background);
            drawable.setColorFilter(categoryData.getColor(), PorterDuff.Mode.MULTIPLY);
            t.setBackgroundDrawable(drawable);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_write);

        mTextCategory = (TextView) findViewById(R.id.text_card_category_title);
        mImageCategoryBar = (ImageView) findViewById(R.id.image_card_category_color);
        scrollView = (ScrollView) findViewById(R.id.scroll_view_content);
        mCancelSaveLayout = (LinearLayout) findViewById(R.id.linearLayout_cancel_save_button);
        mEditTitle = (EditText) findViewById(R.id.textView_memo_title);
        mEditContent = (EditText) findViewById(R.id.textView_memo_content);
        mTextTitle = (TextView) findViewById(R.id.text_view_card_title);
        mTextContent = (TextView) findViewById(R.id.text_view_card_content);
        mEditTag = (EditText)findViewById(R.id.edit_text_card_tag);
        mPredicateLayout =(PredicateLayout)findViewById(R.id.layout_job_question_tag);
//        mTextStartDate = (TextView) findViewById(R.id.text_start_date);
//        mTextEndDate = (TextView) findViewById(R.id.text_end_date);

        Intent intent = getIntent();
        isNew = intent.getBooleanExtra(MyCard.CARD_NEW, false);
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
//        mTextStartDate.setText(mData.getCard().getStartDate());
//        mTextEndDate.setText(mData.getCard().getEndDate());

        // 카테고리 이름, 색 설정
        CategoryData categoryData = CategoryData.get(getApplicationContext()).getCategoryList().get(mData.getCard().getCategory());
        mTextCategory.setText(categoryData.getName());
        mTextCategory.setTextColor(categoryData.getColor());
        mTextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListDialogFragment dialog = new CategoryListDialogFragment();
                dialog.show(getSupportFragmentManager(), CardBoxFragment.CATEGORY_DIALOG);
            }
        });
        mImageCategoryBar.setBackgroundColor(categoryData.getColor());

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
                saveCardData();
            }
        }); // 저장 버튼 클릭 이벤트 설정

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
//        mTextStartDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomCalendarDialogFragment dialog = new CustomCalendarDialogFragment();
//                dialog.show(getSupportFragmentManager(), CALENDAR_START_DIALOG);
//            }
//        });
//        mTextEndDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomCalendarDialogFragment dialog = new CustomCalendarDialogFragment();
//                dialog.show(getSupportFragmentManager(), CALENDAR_END_DIALOG);
//            }
//        });
        ImageView mCategorySelectImage = (ImageView) findViewById(R.id.image_category_select);
        mCategorySelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListDialogFragment dialog = new CategoryListDialogFragment();
                dialog.show(getSupportFragmentManager(), CardBoxFragment.CATEGORY_DIALOG);
                mCancelSaveLayout.setVisibility(View.VISIBLE);
            }
        });

        // 태그 + 버튼
        ImageView mImageAddTag = (ImageView)findViewById(R.id.btn_add_tag);
        mImageAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = mEditTag.getText().toString();
                String lengthCheck = tag.replace(" ", "");
                if(lengthCheck.length() != 0){
                    if(mData.getCard().addTag(tag)) {
                            addTagView(tag);
                    }
                }
                mEditTag.setText("");
            }
        });

        setSpinner(); // 스피너 설정
    }

    // 변경된 카드 내용 저장 메소드
    public void saveCardData(){
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
                    Log.i("error : ", code+"");
                    Toast.makeText(MyApplication.getContext(), "요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
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
                    Log.i("error : ", code+"");
                    Toast.makeText(MyApplication.getContext(), "요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            });
        }
    }

    //태그 더하기
    public void addTagView(String tag){
        final TextView t = new TextView(CardWriteActivity.this);
        t.setText(tag);
        t.setTextSize(12);
        t.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        Drawable drawable = ContextCompat.getDrawable(CardWriteActivity.this, R.drawable.image_category_background);
        drawable.setColorFilter(CategoryData.get(getApplicationContext()).getCategoryList().get(mData.getCard().getCategory()).getColor(), PorterDuff.Mode.MULTIPLY);
        t.setBackgroundDrawable(drawable);
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
                Toast.makeText(CardWriteActivity.this, "해당 태그가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                mPredicateLayout.removeView(t);
                mData.getCard().removeTagText(t.getText().toString());
                return true;
            }
        });
//        t.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                mCancelSaveLayout.setVisibility(View.VISIBLE);
//                for(int i=0; i<mTextTags.size(); i++){
//                    if(t == mTextTags.get(i)){
//                        Toast.makeText(CardWriteActivity.this, "해당 태그가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                        mData.getCard().removeTag(i);
//                        mPredicateLayout.removeView(mTextTags.get(i));
//                        mTextTags.remove(i);
//                    }
//                }
//                return true;
//            }
//        });
        mPredicateLayout.addView(t);
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

    //스피너 세팅 메소드
    public void setSpinner(){
        initArrayAdapter();
        mSpinnerJobCompetence = (Spinner)findViewById(R.id.spinner_job_competence);
        mSpinnerJobCompetence.setAdapter(mArrayAdapters[0]);
        mSpinnerJobCompetence.setSelection(mData.getCard().getJobCompetenceIndex());
        mSpinnerJobCompetence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String old = mArrayAdapters[0].getItem(mData.getCard().getJobCompetenceIndex());
                String select = (String) mSpinnerJobCompetence.getSelectedItem();
                if(old.equals(select)){
                    return;
                }
                int tagIndex = findTagIndex(old);
                if(tagIndex != -1) {
                    if(mData.getCard().removeTagText(old)) {
                        mPredicateLayout.removeViewAt(tagIndex);
                    }
                }
                mData.getCard().setJobCompetenceIndex(position);
                if(position > 0 ) {
                    if(mData.getCard().addTag(select)) {
                        addTagView((String) mSpinnerJobCompetence.getSelectedItem());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerWorkCompetence = (Spinner)findViewById(R.id.spinner_work_competence);
        mSpinnerWorkCompetence.setAdapter(mArrayAdapters[1]);
        mSpinnerWorkCompetence.setSelection(mData.getCard().getWorkCompetenceIndex());
        mSpinnerWorkCompetence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String old = mArrayAdapters[1].getItem(mData.getCard().getWorkCompetenceIndex());
                String select = (String) mSpinnerWorkCompetence.getSelectedItem();
                if(old.equals(select)){
                    return;
                }
                int tagIndex = findTagIndex(old);
                if(tagIndex != -1) {
                    if(mData.getCard().removeTagText(old)) {
                        mPredicateLayout.removeViewAt(tagIndex);
                    }
                }
                mData.getCard().setWorkCompetenceIndex(position);
                if(position > 0) {
                    if(mData.getCard().addTag(select)) {
                        addTagView((String) mSpinnerWorkCompetence.getSelectedItem());
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerAttitudeCompetence = (Spinner)findViewById(R.id.spinner_attitude_competence);
        mSpinnerAttitudeCompetence.setAdapter(mArrayAdapters[2]);
        mSpinnerAttitudeCompetence.setSelection(mData.getCard().getAttitudeCompetenceIndex());
        mSpinnerAttitudeCompetence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String old = mArrayAdapters[2].getItem(mData.getCard().getAttitudeCompetenceIndex());
                String select = (String) mSpinnerAttitudeCompetence.getSelectedItem();
                if(old.equals(select)){
                    return;
                }
                int tagIndex = findTagIndex(old);
                if(tagIndex != -1) {
                    if(mData.getCard().removeTagText(old)) {
                        mPredicateLayout.removeViewAt(tagIndex);
                    }
                }
                mData.getCard().setAttitudeCompetenceIndex(position);
                if(position > 0) {
                    if(mData.getCard().addTag(select)) {
                        addTagView((String) mSpinnerAttitudeCompetence.getSelectedItem());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initArrayAdapter() {
        mArrayAdapters = new ArrayAdapter[3];
        String[][] StringArray = new String[3][];
        StringArray[0] = getResources().getStringArray(R.array.job_competence);
        StringArray[1] = getResources().getStringArray(R.array.work_competence);
        StringArray[2] = getResources().getStringArray(R.array.attitude_competence);
        for (int i = 0; i < mArrayAdapters.length; i++) {
            mArrayAdapters[i] = new ArrayAdapter<String>(CardWriteActivity.this, R.layout.spinner_header_item_card);
            mArrayAdapters[i].setDropDownViewResource(R.layout.spinner_dropdown_item_card);
            mArrayAdapters[i].addAll(StringArray[i]);
        }
    }

    private int findTagIndex(String str){
        for(int i=0; i<mPredicateLayout.getChildCount(); i++){
            TextView textView = (TextView)mPredicateLayout.getChildAt(i);
            if(textView.getText().toString().equals(str)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {
        final SaveDialogFragment dialog = new SaveDialogFragment();
        dialog.show(getSupportFragmentManager(), "dialog");
        SaveDialogFragment.ButtonEventListener listener = new SaveDialogFragment.ButtonEventListener() {
            @Override
            public void onYesEvent() {
                saveCardData();
            }

            @Override
            public void onNoEvent() {
                finish();
            }

            @Override
            public void onCancelEvent() {
                dialog.dismiss();
            }
        };
        dialog.setButtonEventListener(listener);
    }
}
