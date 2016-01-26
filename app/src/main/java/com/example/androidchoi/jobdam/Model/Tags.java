package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2016-01-26.
 */
public class Tags {

    private int tag_count;
    @SerializedName("items")
    private List<String> tag = new ArrayList<String>();

    public int getTagCount() {
        return tag_count;
    }
    public List<String> getTag() {
        return tag;
    }
}
