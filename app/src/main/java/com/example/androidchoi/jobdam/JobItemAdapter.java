package com.example.androidchoi.jobdam;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2015-10-19.
 */
public class JobItemAdapter extends BaseAdapter {

    List<JobItemData> items = new ArrayList<JobItemData>();

    public void add(JobItemData item){
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
        view.setItemData(items.get(position));

        return view;
    }
}
