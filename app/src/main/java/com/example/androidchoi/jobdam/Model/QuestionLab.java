package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2015-12-02.
 */
public class QuestionLab {
    String _id;
    @SerializedName("jasoseo")
    Questions mQuestions;

    public String get_id() {
        return _id;
    }

    public Questions getQuestions() {
        return mQuestions;
    }
}
