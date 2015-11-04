package com.example.androidchoi.jobdam.Model;

/**
 * Created by Choi on 2015-11-04.
 */
public class ContentData implements ChildData {
    private String mContent;

    public ContentData(String content) {
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }
}
