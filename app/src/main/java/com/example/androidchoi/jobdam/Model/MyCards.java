package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Choi on 2015-11-16.
 */
public class MyCards implements Serializable{

    private String _id;
    @SerializedName("memo")
    private MyCard card;

    public MyCard getCard() {
        return card;
    }
    public String getId() { return _id; }
    public MyCards() { }
    public MyCards(String test) {
        card = new MyCard(test);
    }


}
