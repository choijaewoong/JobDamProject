package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.CardItemView;
import com.example.androidchoi.jobdam.Model.CardData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class CardItemAdapter extends BaseAdapter {

    List<CardData> items = new ArrayList<CardData>();

    public void add(CardData item){
        items.add(item);
        notifyDataSetChanged();
    }

    public void add(CardData item, int position){
        items.add(position, item);
        notifyDataSetChanged();
    }

    public void update(CardData item, int position){
        items.get(position).setCarditem(item);
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
        CardItemView view;
        if(convertView == null){
            view = new CardItemView(parent.getContext());
        }else{
            view = (CardItemView)convertView;
        }
        view.setItemData(items.get(position));
        return view;
    }
}
