package com.example.androidchoi.jobdam;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.QuestionData;
import com.example.androidchoi.jobdam.Util.PredicateLayout;

/**
 * Created by Choi on 2015-11-04.
 */
public class ExpandableChildQuestionItemView extends FrameLayout {
    public ExpandableChildQuestionItemView(Context context) {
        super(context);
        init();
    }

    TextView mTextQuestionView;
    PredicateLayout mPredicateLayout;
    Button mButtonQuestionDetail;

    private void init() {
        View view = inflate(getContext(), R.layout.view_expandable_child_question_item,this);
        mTextQuestionView = (TextView)view.findViewById(R.id.text_job_question);
        mPredicateLayout = (PredicateLayout)view.findViewById(R.id.layout_job_question_tag);
        mButtonQuestionDetail = (Button)view.findViewById(R.id.button_job_question_detail);
    }

    public void setExpandableQuestion(QuestionData data){
        mTextQuestionView.setText(data.getQuestion());
    }

    public void setVisibleDetailButton(){
        mButtonQuestionDetail.setVisibility(VISIBLE);
    }
}
