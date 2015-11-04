package com.example.androidchoi.jobdam;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Choi on 2015-11-04.
 */
public class ExpandableChildQuestionItemView extends FrameLayout {
    public ExpandableChildQuestionItemView(Context context) {
        super(context);
        init();
    }

    View mQuestionView;
    TextView mTextQuestionView;
    EditText mEditTagView;

    private void init() {
        View view = inflate(getContext(), R.layout.view_expandable_child_question_item,this);
    }
}
