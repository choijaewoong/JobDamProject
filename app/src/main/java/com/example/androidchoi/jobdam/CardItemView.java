package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.RelativeLayout;

import com.example.androidchoi.jobdam.Model.CardItemData;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class CardItemView extends RelativeLayout{

    CardItemData data;

    public CardItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_card_item, this);
    }

    public void setItemData(CardItemData data){

    }


}
