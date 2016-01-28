package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.ItemView.JobItemView;
import com.example.androidchoi.jobdam.Model.MyJob;
import com.example.androidchoi.jobdam.Model.MyJobs;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-13.
 */
public class MyJobItemAdapter extends BaseAdapter {

    private ArrayList<MyJobs> mItems = new ArrayList<MyJobs>();

    public void setItems(ArrayList<MyJobs> items){
        mItems = items;
        notifyDataSetChanged();
    }

    public void add(MyJob item){
        MyJobs myJobs = new MyJobs();
        myJobs.setJob(item);
        mItems.add(myJobs);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        MyJob myJob =  mItems.get(position).getJob();
        return myJob;
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
        view.setItemData((MyJob)getItem(position), true);
        return view;
    }
}
