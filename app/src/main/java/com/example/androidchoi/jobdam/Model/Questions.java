package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Choi on 2015-12-02.
 */
public class Questions {
    int job_id;
    @SerializedName("question")
    List<QuestionData> questionList;

    public int getJob_id() {
        return job_id;
    }

    public List<QuestionData> getQuestionList() {
        return questionList;
    }
}
