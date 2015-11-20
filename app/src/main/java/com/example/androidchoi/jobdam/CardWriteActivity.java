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
import com.example.androidchoi.jobdam.Model.MyCardLab;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CardWriteActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_DATA = "card data";
    public static final String EXTRA_CARD_POSITION = "card position";
    private static final String CATEGORY_DIALOG = "category_dialog";

    private MyCard mData;
    ImageView mCategoryImage;
    EditText mEditTitle;
    EditText mEditContent;
    TextView mTextCategory;
    TextView mTextTitle;
    TextView mTextContent;
    ScrollView scrollView;
    LinearLayout mCancelSaveLayout;
    TextView mCancelButton;
    TextView mSaveButton;
    boolean isNew;

    public MyCard getData() {
        return mData;
    }
    public void setCategoryTextView(int position){
        ArrayList<CategoryData> arrayList = CategoryData.get(getApplicationContext()).getCategoryList();
        mTextCategory.setText(arrayList.get(position).getName());
        mTextCategory.setTextColor(arrayList.get(position).getColor());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_write);

        mCategoryImage = (ImageView)findViewById(R.id.image_category_select);
        mTextCategory = (TextView)findViewById(R.id.text_card_category_title);
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
        if (isNew == false) {
            mCancelSaveLayout.setVisibility(View.GONE);
            mTextTitle.setText(mData.getTitle());
            mTextContent.setText(mData.getContent());
        } else{ // 기존 Data없는 경우 (메모 추가)
            mData = new MyCard();
            changeWriteMode();
            mEditContent.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEditContent, InputMethodManager.SHOW_IMPLICIT);
        }
        mTextCategory.setText(CategoryData.get(getApplicationContext()).getCategoryList().get(mData.getCategory()).getName());
        mTextCategory.setTextColor(CategoryData.get(getApplicationContext()).getCategoryList().get(mData.getCategory()).getColor());

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
        mCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment dialog = new CustomDialogFragment();
                dialog.show(getSupportFragmentManager(), CATEGORY_DIALOG);
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
