package com.example.androidchoi.jobdam.Model;

/**
 * Created by Choi on 2015-11-04.
 */
public class GroupData {
    private String mTitle;
    private ChildData mChildData;

    public GroupData(String title, ChildData childData) {
        mTitle = title;
        mChildData = childData;
    }

    public String getTitle() {
        return mTitle;
    }
    public ChildData getChildData() {
        return mChildData;
    }
}
