package com.example.androidchoi.jobdam.Adpater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Model.EmotionData;
import com.example.androidchoi.jobdam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2016-01-16.
 */
public class RecyclerAdapter  extends RecyclerView.Adapter<ViewHolder> {

    List<EmotionData> items = new ArrayList<EmotionData>();

    ViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(ViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public RecyclerAdapter(){
        items = EmotionData.get(MyApplication.getContext()).getCategoryList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_emotion_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(items.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
