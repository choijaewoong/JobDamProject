package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-04.
 */
public class QuestionData implements ChildData, Serializable {
//    private List<String> mQuestions = new ArrayList<String>();
//
//    public String getQuestion(int index) {
//        return mQuestions.get(index);
//    }
    private String question;
    private String limit;
    @SerializedName("tag")
    private ArrayList<MyCard> cardList = new ArrayList<MyCard>();
    public String getQuestion() {  return question; }
    public ArrayList<MyCard> getCardList() { return cardList; }

    //sample
    public QuestionData(String str){
        question = str + "번째 질문 입니다.";
        limit = "300";
    }
}
