package com.example.androidchoi.jobdam.Calendar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.MainActivity;
import com.example.androidchoi.jobdam.MyJobCalendarFragment;

public class CalendarAdapter extends BaseAdapter {

	Context mContext;
	CalendarData mData;
	CalendarItem mCheckDate;
	public CalendarItem getCheckDate() { return mCheckDate; }

	public CalendarAdapter(Context context, CalendarData data) {
		mContext = context;
		mData = data;
		((MyJobCalendarFragment)((MainActivity) context).
				getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_MY_JOB).
				getChildFragmentManager().getFragments().get(1)).setOnDateCheckCallback(callback);
	}
	
	public void setCalendarData(CalendarData data) {
		mData = data;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int size = 0;
		if (mData != null) {
			size = mData.days.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.days.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	MyJobCalendarFragment.OnDateCheckCallback callback = new MyJobCalendarFragment.OnDateCheckCallback() {
		@Override
		public CalendarItem onDateCheck(int position) {
			Log.i("????", mData.days.get(position).dayOfMonth + "");
			for(int i =0 ; i<mData.days.size(); i++){
				mData.days.get(i).checked = false;
			}
			mData.days.get(position).checked = true;
			notifyDataSetChanged();
			return mData.days.get(position);
		}
	};
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			CalendarItemView mCalendarItemView = (CalendarItemView)convertView;
			if (mCalendarItemView == null) {
				mCalendarItemView = new CalendarItemView(mContext);
			}
			mCalendarItemView.setData(mData.days.get(position));
			return mCalendarItemView;
	}
}
