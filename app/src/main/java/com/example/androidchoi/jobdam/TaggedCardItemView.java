package com.example.androidchoi.jobdam;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.Model.MyCard;
import com.example.androidchoi.jobdam.Util.PredicateLayout;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class TaggedCardItemView extends RelativeLayout{

    TextView mTitle;
    TextView mContent;
    TextView mCategoryText;
    TextView mDateText;
    RelativeLayout mContainerLayout;
    RelativeLayout mContentLayout;
    PredicateLayout mPredicateLayout;
    ToggleButton mToggleCardFold;
    Boolean isFold = false;


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
        mToggleCardFold = (ToggleButton)findViewById(R.id.toggleButton_tag_card_fold);
        mContainerLayout = (RelativeLayout)findViewById(R.id.layout_tag_card_item_container);
        mContentLayout = (RelativeLayout)findViewById(R.id.layout_tag_card_content);
        mPredicateLayout = (PredicateLayout)findViewById(R.id.layout_card_tag_container);

        layerDrawable = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.background_tag_card_item);
        shapeDrawable = layerDrawable.findDrawableByLayerId(R.id.category_bar_tag_card);
        mToggleCardFold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mContentLayout.setVisibility(VISIBLE);
                    isFold = true;
                } else {
                    mContentLayout.setVisibility(GONE);
                    isFold = false;
                }
            }
        });
    }

    public void setItemData(MyCard data){
        categoryColor = CategoryData.get(getContext()).getCategoryList().get(data.getCategory()).getColor();
        shapeDrawable.setColorFilter(categoryColor, PorterDuff.Mode.MULTIPLY);
        mContainerLayout.setBackgroundDrawable(layerDrawable);
        mTitle.setText(data.getTitle());
        mContent.setText(data.getContent());
        mToggleCardFold.setChecked(isFold);
        mCategoryText.setText(CategoryData.get(getContext()).getCategoryList().get(data.getCategory()).getName());
        mCategoryText.setTextColor(categoryColor);
        mDateText.setText(data.getDate());
        mPredicateLayout.removeAllViews();
        for(String tag: data.getTags()){
            addTagView(tag);
        }
    }

    public void addTagView(String tag){
        TextView t = new TextView(getContext());
        t.setText(tag);
        t.setTextSize(10);
        t.setTextColor(categoryColor);
        LayerDrawable drawable = (LayerDrawable)ContextCompat.getDrawable(getContext(), R.drawable.image_card_tag_border);
        Drawable borderDrawable = drawable.findDrawableByLayerId(R.id.image_tag_border);
        borderDrawable.setColorFilter(categoryColor, PorterDuff.Mode.MULTIPLY);
        t.setBackgroundDrawable(drawable);
        t.setPadding(12, 4, 12, 4);
        int width = getResources().getDimensionPixelSize(R.dimen.tag_max_width);
        t.setMaxWidth(width);
        t.setSingleLine(true);
        t.setEllipsize(TextUtils.TruncateAt.END);
        t.setGravity(Gravity.CENTER);
        mPredicateLayout.addView(t);
    }
}