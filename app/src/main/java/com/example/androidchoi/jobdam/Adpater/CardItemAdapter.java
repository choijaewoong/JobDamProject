package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.CardItemView;
import com.example.androidchoi.jobdam.Model.MyCards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class CardItemAdapter extends BaseAdapter {

    ArrayList<MyCards> mItems = new ArrayList<MyCards>();

    public void setItems(ArrayList<MyCards> items){
        mItems = items;
        Collections.reverse(mItems);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mItems.size();
    }
    @Override
    public Object getItem(int position) {
        return mItems.get(position).getCard();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardItemView view;
        if(convertView == null){
            view = new CardItemView(parent.getContext());
        }else{
            view = (CardItemView)convertView;
        }
        view.setItemData(mItems.get(position).getCard());
        return view;
    }
}
