package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.Model.MyCard;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class TaggedCardItemView extends RelativeLayout{

    TextView mTitle;
    TextView mContent;
    TextView mCategoryText;
    TextView mDateText;
    RelativeLayout mLayout;
    int categoryColor;

    public TaggedCardItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_tagged_card_item, this);
        mTitle = (TextView)findViewById(R.id.text_tag_memo_title);
        mContent = (TextView)findViewById(R.id.text_tag_memo_content);
        mCategoryText = (TextView)findViewById(R.id.text_tag_category_title);
        mDateText = (TextView)findViewById(R.id.text_tag_card_write_date);
        mLayout = (RelativeLayout)findViewById(R.id.layout_tag_card_item_container);
    }

    public void setItemData(MyCard data){
        categoryColor = CategoryData.get(getContext()).getCategoryList().get(data.getCategory()).getColor();
        mTitle.setText(data.getTitle());
        mContent.setText(data.getContent());
        mCategoryText.setText(CategoryData.get(getContext()).getCategoryList().get(data.getCategory()).getName());
        mCategoryText.setTextColor(categoryColor);
        mDateText.setText(data.getDate());
    }
}