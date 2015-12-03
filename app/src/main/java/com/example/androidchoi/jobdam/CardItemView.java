package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.Model.MyCard;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class CardItemView extends RelativeLayout implements Checkable{

//    CardData data;
    TextView mTitle;
    TextView mContent;
    ImageView mCategoryBar;
    TextView mCategoryText;
    TextView mDateText;
    boolean isChecked = false;
    int categoryColor;

    public CardItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_card_item, this);
        mTitle = (TextView)findViewById(R.id.edit_text_card_title);
        mContent = (TextView)findViewById(R.id.edit_text_card_content);
        mCategoryBar = (ImageView)findViewById(R.id.image_category_bar);
        mCategoryText = (TextView)findViewById(R.id.text_category_title);
        mDateText = (TextView)findViewById(R.id.text_card_write_date);
    }

    public void setItemData(MyCard data){
        categoryColor = CategoryData.get(getContext()).getCategoryList().get(data.getCategory()).getColor();
        mTitle.setText(data.getTitle());
        mContent.setText(data.getContent());
        mCategoryText.setText(CategoryData.get(getContext()).getCategoryList().get(data.getCategory()).getName());
        mCategoryText.setTextColor(categoryColor);
        mCategoryBar.setBackgroundColor(categoryColor);
        mDateText.setText(data.getWriteDate());
    }

    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
        if(checked) {
            mCategoryBar.setBackgroundResource(R.color.colorPrimary);
        } else {
            mCategoryBar.setBackgroundColor(categoryColor);
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}