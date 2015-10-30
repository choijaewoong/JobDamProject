package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.CardItemData;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class CardItemView extends RelativeLayout{

    CardItemData data;
    TextView mTitle;
    TextView mContent;

    public CardItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_card_item, this);
        mTitle = (TextView)findViewById(R.id.text_card_title);
        mContent = (TextView)findViewById(R.id.text_card_content);
    }

    public void setItemData(CardItemData data){
        mTitle.setText(data.getmTitle());
        mContent.setText(data.getmContent());
    }
}
