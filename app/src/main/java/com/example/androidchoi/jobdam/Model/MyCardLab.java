package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-10.
 */
public class MyCardLab {
    @SerializedName("items")
    private ArrayList<MyCards> mCardList;

    public ArrayList<MyCards> getCardList() {
        return mCardList;
    }
}

