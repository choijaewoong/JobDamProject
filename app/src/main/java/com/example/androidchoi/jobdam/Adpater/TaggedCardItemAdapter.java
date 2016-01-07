package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.Model.MyCards;
import com.example.androidchoi.jobdam.TaggedCardItemView;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-10-19.
 */
public class TaggedCardItemAdapter extends BaseAdapter {

    ArrayList<MyCards> mItems = new ArrayList<MyCards>();

    public void setItems(ArrayList<MyCards> items){
        mItems = items;
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
        TaggedCardItemView view;
        if(convertView == null){
            view = new TaggedCardItemView(parent.getContext());

        }else {
            view = (TaggedCardItemView)convertView;
        }
        view.setItemData(mItems.get(position).getCard());

        return view;
    }
}
