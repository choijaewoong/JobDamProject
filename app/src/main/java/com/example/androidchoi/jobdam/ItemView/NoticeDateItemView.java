package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.NoticeDateData;
import com.example.androidchoi.jobdam.R;

/**
 * Created by Choi on 2015-11-28.
 */
public class NoticeDateItemView extends FrameLayout {
    TextView mTextDate;
    public NoticeDateItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_notice_date_item, this);
        mTextDate =(TextView)findViewById(R.id.text_notice_date_header);
    }
    public void setItemData(NoticeDateData data){
        mTextDate.setText(data.getDate());
    }
}
