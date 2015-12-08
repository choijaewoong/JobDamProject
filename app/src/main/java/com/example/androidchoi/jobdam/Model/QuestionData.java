package com.example.androidchoi.jobdam.Model;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-04.
 */
public class QuestionData implements ChildData {
//    private List<String> mQuestions = new ArrayList<String>();
//
//    public String getQuestion(int index) {
//        return mQuestions.get(index);
//    }
    private String question;
    private int limit;
    private ArrayList<MyCards> cardList = new ArrayList<MyCards>();
    public String getQuestion() {  return question; }
    public QuestionData(String question) {
        this.question = question;
    }
    public ArrayList<MyCards> getCardList() { return cardList; }
}
