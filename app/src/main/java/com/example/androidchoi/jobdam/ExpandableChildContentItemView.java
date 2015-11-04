package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.ContentData;

/**
 * Created by Choi on 2015-11-03.
 */
public class ExpandableChildContentItemView extends FrameLayout {

    public ExpandableChildContentItemView(Context context) {
        super(context);
        init();
    }

    TextView mExpandableContentView;
    private void init() {
        inflate(getContext(), R.layout.view_expandable_child_content_item, this);
        mExpandableContentView = (TextView)findViewById(R.id.text_expandable_content);
    }
    public void setExpandableContent(ContentData data){
        mExpandableContentView.setText(data.getContent());
    }
}
