package com.example.androidchoi.jobdam.Model;

/**
 * Created by Choi on 2015-11-03.
 */
public class ExpandableGroupData {
    private String mTitle;
    private String mContent;

    public ExpandableGroupData(String title, String content) {
        mTitle = title;
        mContent = content;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }
}
