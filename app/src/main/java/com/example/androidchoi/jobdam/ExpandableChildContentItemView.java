package com.example.androidchoi.jobdam;

import android.content.Context;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.ContentData;

/**
 * Created by Choi on 2015-11-03.
 */
public class ExpandableChildContentItemView extends FrameLayout {

    public ExpandableChildContentItemView(Context context) {
        super(context);
        init();
    }

    TextView mExpandableContentView;
    TextView mExpandableTitleView;
    ImageView mImageBottomPadding;
    private void init() {
        inflate(getContext(), R.layout.view_expandable_child_content_item, this);
        mExpandableContentView = (TextView)findViewById(R.id.text_expandable_period_dday);
        mExpandableTitleView = (TextView)findViewById(R.id.text_expandable_title);
        mImageBottomPadding = (ImageView)findViewById(R.id.image_bottom_padding);
    }
    public void setExpandableContent(ContentData data){
        mExpandableContentView.setText(Html.fromHtml(data.getContent()));
        mExpandableTitleView.setText(Html.fromHtml(data.getTitle()));
        mImageBottomPadding.setVisibility(GONE);
    }

    public void setVisibleBottomPadding() {
        mImageBottomPadding.setVisibility(VISIBLE);
    }
}
