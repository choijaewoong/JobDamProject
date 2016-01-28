package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.PeriodData;
import com.example.androidchoi.jobdam.R;

/**
 * Created by Choi on 2015-11-03.
 */
public class ExpandableChildPeriodItemView extends FrameLayout {

    public ExpandableChildPeriodItemView(Context context) {
        super(context);
        init();
    }

    TextView mExpandableDdayView;
    TextView mExpandableStartView;
    TextView mExpandableEndView;
    private void init() {
        inflate(getContext(), R.layout.view_expandable_child_period_item, this);
        mExpandableDdayView = (TextView)findViewById(R.id.text_expandable_period_dday);
        mExpandableStartView = (TextView)findViewById(R.id.text_expandable_period_start);
        mExpandableEndView = (TextView)findViewById(R.id.text_expandable_period_end);
    }
    public void setExpandablePeriod(PeriodData data){
        if(data.getDday() == -1) {
            mExpandableDdayView.setText("마감");
            mExpandableEndView.setText(data.getEnd());
        }else if(data.getDday() > 200){
            mExpandableDdayView.setVisibility(GONE);
            mExpandableEndView.setText("상시 채용");
        }else {
            mExpandableDdayView.setText(Html.fromHtml("마감 <font color=\"#0db5f7\">" + data.getDday() + "</font>일 전"));
            mExpandableEndView.setText(data.getEnd());
        }
        mExpandableStartView.setText(data.getStart());

    }
}
