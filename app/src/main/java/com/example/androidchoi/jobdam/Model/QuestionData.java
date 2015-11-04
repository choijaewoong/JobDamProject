package com.example.androidchoi.jobdam.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2015-11-04.
 */
public class QuestionData implements ChildData {
    private List<String> mQuestions = new ArrayList<String>();

    public String getQuestion(int index) {
        return mQuestions.get(index);
    }
}
