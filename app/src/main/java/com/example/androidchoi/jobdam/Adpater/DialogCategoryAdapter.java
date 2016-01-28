package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.ItemView.CategoryItemView;
import com.example.androidchoi.jobdam.Model.CategoryData;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-20.
 */
public class DialogCategoryAdapter extends BaseAdapter {
    ArrayList<CategoryData> mItems = new ArrayList<CategoryData>();

    public void setItems(ArrayList<CategoryData> arrayList){
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
        CategoryItemView view;
        if(convertView == null){
            view  = new CategoryItemView(parent.getContext());
        } else{
            view = (CategoryItemView)convertView;
        }
        view.setItemData(mItems.get(position));

        return view;
    }
}
