package com.example.androidchoi.jobdam;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
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
    LayerDrawable layerDrawable;
    Drawable shapeDrawable;
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
        layerDrawable = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.background_tag_card_item);
        shapeDrawable = layerDrawable.findDrawableByLayerId(R.id.category_bar_tag_card);
    }

    public void setItemData(MyCard data){
        categoryColor = CategoryData.get(getContext()).getCategoryList().get(data.getCategory()).getColor();
        shapeDrawable.setColorFilter(categoryColor, PorterDuff.Mode.MULTIPLY);
        mLayout.setBackgroundDrawable(layerDrawable);
        mTitle.setText(data.getTitle());
        mContent.setText(data.getContent());
        mCategoryText.setText(CategoryData.get(getContext()).getCategoryList().get(data.getCategory()).getName());
        mCategoryText.setTextColor(categoryColor);
        mDateText.setText(data.getDate());
    }
}