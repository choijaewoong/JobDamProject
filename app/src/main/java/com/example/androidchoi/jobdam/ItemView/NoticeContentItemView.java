package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.NoticeContentData;
import com.example.androidchoi.jobdam.R;

/**
 * Created by Choi on 2015-11-28.
 */
public class NoticeContentItemView extends RelativeLayout{

    TextView mTextContent;
    TextView mTextDate;
    ImageView mImageNoticeType;
    RelativeLayout mLayoutBox;

    public NoticeContentItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_notice_item, this);
        mTextContent = (TextView)findViewById(R.id.text_notice_content);
        mTextDate = (TextView)findViewById(R.id.text_notice_date);
        mImageNoticeType = (ImageView)findViewById(R.id.image_notice_type);
        mLayoutBox = (RelativeLayout)findViewById(R.id.layout_notice_box);
    }

    public void setItemData(NoticeContentData data){
        mTextContent.setText(data.getContent());
        mTextDate.setText(data.getDate());
    }
}
