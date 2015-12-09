package com.example.androidchoi.jobdam.Model;

import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2015-11-04.
 */
public class GroupData {
    private String mTitle;
    private List<ChildData> mChildDataList = new ArrayList<ChildData>();
    public GroupData(String title, Questions questions) {
        mTitle = title;
        if(questions == null || questions.getQuestionList().size() == 0){
            mChildDataList.add(new ContentData(MyApplication.getContext().getString(R.string.empty_question), ""));
            return;
        }
        for(QuestionData data : questions.getQuestionList()){
            mChildDataList.add(data);
        }
    }
    public GroupData(String title, List<ChildData> childDataList){
        mTitle = title;
        mChildDataList.addAll(childDataList);
    }
    public String getTitle() {
        return mTitle;
    }
    public List<ChildData> getChildDataList() {
        return mChildDataList;
    }
}
