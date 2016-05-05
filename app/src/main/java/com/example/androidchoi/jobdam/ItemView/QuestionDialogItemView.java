package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.androidchoi.jobdam.Model.QuestionData;
import com.example.androidchoi.jobdam.R;

/**
 * Created by Choi on 2015-11-20.
 */
public class QuestionDialogItemView extends RelativeLayout {

    ImageView mImageView;
    EditText mEditText;

    public QuestionDialogItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_question_dialog_item, this);
        mEditText = (EditText) findViewById(R.id.editText_question);
        mImageView = (ImageView) findViewById(R.id.image_question_delete);
    }

    public void setItemData(QuestionData data) {
        mEditText.setText(data.getQuestion());
//        mImageView.setImageDrawable(drawable);
//        mImageView.setImageResource(data.getImage());
    }

}
