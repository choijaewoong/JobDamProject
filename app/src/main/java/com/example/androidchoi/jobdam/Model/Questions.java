package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2015-12-02.
 */
public class Questions implements Serializable{
//    int job_id;
    @SerializedName("Question")
    List<QuestionData> questionList = new ArrayList<>();

//    public int getJobId() {
//        return job_id;
//    }
    public List<QuestionData> getQuestionList() {
        return questionList;
    }
}
