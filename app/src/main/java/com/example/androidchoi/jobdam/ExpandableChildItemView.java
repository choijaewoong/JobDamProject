package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.ExpandableGroupData;

/**
 * Created by Choi on 2015-11-03.
 */
public class ExpandableChildItemView extends FrameLayout {

    public ExpandableChildItemView(Context context) {
        super(context);
        init();
    }

    TextView mExpandableContentView;
    private void init() {
        inflate(getContext(), R.layout.view_expandable_child_item, this);
        mExpandableContentView = (TextView)findViewById(R.id.text_expandable_content);
    }

    public void setExpandableContent(ExpandableGroupData item){
        mExpandableContentView.setText(item.getContent());
    }

}
