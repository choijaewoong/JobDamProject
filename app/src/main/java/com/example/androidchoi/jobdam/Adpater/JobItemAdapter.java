package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.JobItemView;
import com.example.androidchoi.jobdam.Model.Job;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-10-19.
 */
public class JobItemAdapter extends BaseAdapter {

    ArrayList<Job> mItems = new ArrayList<Job>();

    int totalCount;
    public void setItems(ArrayList items){
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }
    public void add(Job item){
        mItems.add(item);
        notifyDataSetChanged();
    }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
    public int getStartIndex() {
        if (mItems.size() < totalCount) {
            return mItems.size() + 1;
        }
        return -1;
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
        JobItemView view;
        if(convertView == null){
            view = new JobItemView(parent.getContext());

        }else {
            view = (JobItemView)convertView;
        }
        view.setItemData(mItems.get(position));

        return view;
    }
}
