package com.example.androidchoi.jobdam.Model;

/**
 * Created by Choi on 2015-11-04.
 */
public class ContentData implements ChildData {
    private String mContent;
    private String mTitle;

    public ContentData(String title, String content) {
        mTitle = title;
        mContent = content;
    }
    public String getTitle() { return mTitle; }
    public String getContent() {
        return mContent;
    }
}
