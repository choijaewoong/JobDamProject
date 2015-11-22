package com.example.androidchoi.jobdam.Calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Choi on 2015-11-21.
 */
public class MemoCalendarAdapter extends CalendarAdapter {
    public MemoCalendarAdapter(Context context, CalendarData data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        MemoCalendarItemView view = (MemoCalendarItemView)convertView;
        if (view == null) {
            view = new MemoCalendarItemView(mContext);
        }
        view.setData(mData.days.get(position));
        return view;
    }
}
