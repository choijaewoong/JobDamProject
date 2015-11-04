package com.example.androidchoi.jobdam;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.ExpandableGroupData;

/**
 * Created by Choi on 2015-11-03.
 */
public class ExpandableGroupItemView extends FrameLayout{
    public ExpandableGroupItemView(Context context) {
        super(context);
        init();
    }

    TextView mExpandableTitleView;
    private void init() {
        inflate(getContext(), R.layout.view_expandable_group_item, this);
        mExpandableTitleView = (TextView)findViewById(R.id.text_expandable_title);
    }
    public void setExpandableTitle(ExpandableGroupData item) {
        mExpandableTitleView.setText(item.getTitle());
    }
    public void setSelectTitle(boolean flag) {
        mExpandableTitleView.setSelected(flag);
    }
}
