package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.ItemView.QuestionDialogItemView;
import com.example.androidchoi.jobdam.Model.QuestionData;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-20.
 */
public class DialogQuestionAdapter extends BaseAdapter {
    ArrayList<QuestionData> mItems = new ArrayList<QuestionData>();

    public void setItems(ArrayList<QuestionData> arrayList){
        mItems.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuestionDialogItemView view;
        if(convertView == null){
            view  = new QuestionDialogItemView(parent.getContext());
        } else{
            view = (QuestionDialogItemView)convertView;
        }
        view.setItemData(mItems.get(position));

        return view;
    }
}
