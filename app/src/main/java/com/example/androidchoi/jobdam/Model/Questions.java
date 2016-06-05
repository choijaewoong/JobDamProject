package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2015-12-02.
 */
public class Questions implements Serializable{
    @SerializedName("Question")
    List<QuestionData> questionList = new ArrayList<>();
    public List<QuestionData> getQuestionList() {
        return questionList;
    }
}
