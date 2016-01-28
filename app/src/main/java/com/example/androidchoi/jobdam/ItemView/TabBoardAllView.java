package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.R;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class TabBoardAllView extends RelativeLayout{

    TextView mTextView;
    ImageView mImageView;

    public TabBoardAllView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_tab_board, this);
        mTextView = (TextView)findViewById(R.id.text_tab_board);
        mTextView.setText("잡담 톡");
        mImageView = (ImageView)findViewById(R.id.image_tab_board);
        mImageView.setImageResource(R.drawable.selector_board_all_icon);
    }
}