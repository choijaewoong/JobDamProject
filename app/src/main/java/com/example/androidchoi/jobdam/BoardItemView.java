package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.Article;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class BoardItemView extends RelativeLayout{

    TextView mTextContent;
    TextView mTextLikeCount;
    TextView mTextEmotionDescription;
    ImageView mImageEmotionIcon;

    public BoardItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_board_item, this);
        mTextContent = (TextView)findViewById(R.id.text_board_content);
        mTextLikeCount = (TextView)findViewById(R.id.text_board_like_count);
        mTextEmotionDescription = (TextView)findViewById(R.id.text_emotion_description);
        mImageEmotionIcon = (ImageView)findViewById(R.id.image_emotion_icon);
    }

    public void setItemData(Article data){
        mTextContent.setText(data.getContent());
        mTextLikeCount.setText(data.getLikeCount()+"");
    }
}