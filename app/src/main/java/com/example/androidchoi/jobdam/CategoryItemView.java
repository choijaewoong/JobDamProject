package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.CategoryData;

/**
 * Created by Choi on 2015-11-20.
 */
public class CategoryItemView extends RelativeLayout {

    ImageView mImageView;
    TextView mTextView;

    public CategoryItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_category_item, this);
        mImageView = (ImageView)findViewById(R.id.image_category_item_color);
        mTextView = (TextView)findViewById(R.id.text_category_item_title);
    }

    public void setItemData(CategoryData data){
        mTextView.setText(data.getName());
        mImageView.setImageResource(data.getImage());
    }

}
