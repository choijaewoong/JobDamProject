package com.example.androidchoi.jobdam.Model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Choi on 2015-11-16.
 */
public class MyCards implements Serializable{

    private String _id;
    @SerializedName("memo")
    private MyCard card;

    protected MyCards(Parcel in) {
        _id = in.readString();
    }

    public MyCard getCard() {
        return card;
    }
    public String getId() { return _id; }

    public MyCards() {
        card = new MyCard();
    }


}
