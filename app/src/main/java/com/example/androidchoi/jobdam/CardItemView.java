package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.CardData;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class CardItemView extends RelativeLayout{

    CardData data;
    TextView mTitle;
    TextView mContent;

    public CardItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_card_item, this);
        mTitle = (TextView)findViewById(R.id.edit_text_card_title);
        mContent = (TextView)findViewById(R.id.edit_text_card_content);
    }

    public void setItemData(CardData data){
        mTitle.setText(data.getTitle());
        mContent.setText(data.getContent());
    }
}
