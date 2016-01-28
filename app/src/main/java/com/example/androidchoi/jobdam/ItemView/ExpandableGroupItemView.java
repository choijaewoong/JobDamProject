package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.GroupData;
import com.example.androidchoi.jobdam.R;

/**
 * Created by Choi on 2015-11-03.
 */
public class ExpandableGroupItemView extends FrameLayout{
    public ExpandableGroupItemView(Context context) {
        super(context);
        init();
    }

    TextView mExpandableTitleView;
    ImageView mExapandableFoldImage;

    private void init() {
        inflate(getContext(), R.layout.view_expandable_group_item, this);
        mExpandableTitleView = (TextView)findViewById(R.id.text_expandable_title);
        mExapandableFoldImage = (ImageView)findViewById(R.id.image_expandable_fold);
    }
    public void setExpandableTitle(GroupData item) {
        mExpandableTitleView.setText(item.getTitle());
    }
    public void setSelectTitle(boolean flag) {
        mExpandableTitleView.setSelected(flag);
    }

    public void setExapandableFoldImage(boolean flag){
        if(flag){
            mExapandableFoldImage.setImageResource(R.drawable.button_fold);
        }else{
            mExapandableFoldImage.setImageResource(R.drawable.button_unfold);
        }
    }
}
