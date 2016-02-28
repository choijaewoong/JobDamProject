package com.example.androidchoi.jobdam.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.R;

/**
 * Created by Choi on 2016-02-28.
 */
public class ExpandableTextView extends LinearLayout implements View.OnClickListener {
    TextView mTv;
    ImageView mImageView; // Button to expand/collapse
    private boolean mRelayout = false;
    private boolean mCollapsed = true; // Show short version as default.
    private int mMaxCollapsedLines = 6; // The default number of lines;
    public ExpandableTextView(Context context) {
        super(context);
    }
    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onClick(View v) {
        if (mImageView.getVisibility() != View.VISIBLE) {
            return;
        }
        mCollapsed = !mCollapsed;
        mImageView.setVisibility(mCollapsed ? VISIBLE : GONE);
        mTv.setMaxLines(mCollapsed ? mMaxCollapsedLines : Integer.MAX_VALUE);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // If no change, measure and return
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;
        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        mImageView.setVisibility(View.GONE);
        mTv.setMaxLines(Integer.MAX_VALUE);
        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // If the text fits in collapsed mode, we are done.
        if (mTv.getLineCount() <= mMaxCollapsedLines) {
            return;
        }
        // Doesn't fit in collapsed mode. Collapse text view as needed. Show
        // button.
        if (mCollapsed) {
            mTv.setMaxLines(mMaxCollapsedLines);
        }
        mImageView.setVisibility(View.VISIBLE);
        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    private void findViews() {
        mTv = (TextView) findViewById(R.id.expandable_text);
        mTv.setOnClickListener(this);
        mImageView = (ImageView) findViewById(R.id.expand_collapse);
        mImageView.setOnClickListener(this);
    }
    public void setText(String text) {
        mRelayout = true;
        if (mTv == null) {
            findViews();
        }
        String trimmedText = text.trim();
        mTv.setText(trimmedText);
//        this.setVisibility(trimmedText.length() == 0 ? View.GONE : View.VISIBLE);
    }
    public CharSequence getText() {
        if (mTv == null) {
            return "";
        }
        return mTv.getText();
    }

    public void setCollapsed(){
        if (mTv == null) {
            findViews();
        }
        mCollapsed = true;
        mTv.setMaxLines(mMaxCollapsedLines);
        mImageView.setVisibility(VISIBLE);
    }
}
