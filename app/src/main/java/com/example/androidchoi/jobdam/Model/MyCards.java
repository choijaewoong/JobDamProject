package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2015-11-16.
 */
public class MyCards {

    @SerializedName("memo")
    private MyCard card;
    public MyCard getCard() {
        return card;
    }
    public void setCard(MyCard card) { this.card = card; }
}
