package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.BoardItemView;
import com.example.androidchoi.jobdam.Model.Articles;

import java.util.ArrayList;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class BoardItemAdapter extends BaseAdapter {

    ArrayList<Articles> mItems = new ArrayList<Articles>();

    public void setItems(ArrayList<Articles> items){
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
        BoardItemView view;
        if(convertView == null){
            view = new BoardItemView(parent.getContext());
        }else{
            view = (BoardItemView)convertView;
        }
        view.setItemData(mItems.get(position).getArticle());
        return view;
    }
}
