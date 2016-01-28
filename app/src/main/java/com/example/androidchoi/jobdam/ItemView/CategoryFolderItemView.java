package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.R;

/**
 * Created by Choi on 2015-11-30.
 */
public class CategoryFolderItemView extends RelativeLayout{

    TextView mTextCount;
    TextView mTextName;
    ImageView mImageIcon;
    RelativeLayout mLayout;


    public CategoryFolderItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_category_grid_item, this);
        mTextCount = (TextView)findViewById(R.id.text_category_count);
        mTextName = (TextView)findViewById(R.id.text_category_name);
        mImageIcon = (ImageView)findViewById(R.id.image_category_icon);
        mLayout = (RelativeLayout)findViewById(R.id.layout_folder_grid_item);
    }

    public void setData(CategoryData data){
        mTextCount.setText("");
        mTextName.setText(data.getName());
        mTextName.setTextColor(data.getColor());
        Drawable drawable = ContextCompat.getDrawable(getContext(), data.getImage());
        drawable.setColorFilter(data.getColor(), PorterDuff.Mode.MULTIPLY);
        mImageIcon.setImageDrawable(drawable);
    }

}
