package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class TabBoardMeView extends RelativeLayout{

    TextView mTextView;
    ImageView mImageView;

    public TabBoardMeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_tab_board, this);
        mTextView = (TextView)findViewById(R.id.text_tab_board);
        mTextView.setText("나의 톡");
        mImageView = (ImageView)findViewById(R.id.image_tab_board);
        mImageView.setImageResource(R.drawable.selector_board_me_icon);
    }
}