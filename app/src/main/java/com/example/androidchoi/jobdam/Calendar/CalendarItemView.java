package com.example.androidchoi.jobdam.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.R;

import java.util.Calendar;

public class CalendarItemView extends LinearLayout{ // implements Checkable {

	TextView numberView;
	TextView startView;
	TextView endView;
	ImageView imageRedBall;
	ImageView imageBlueBall;
	CalendarItem mItem;
	RelativeLayout mRelativeLayout;
	int backgroundColor;
	public RelativeLayout getRelativeLayout() {
		return mRelativeLayout;
	}

//	MyJobCalendarFragment.OnDateCheckCallback callback = new MyJobCalendarFragment.OnDateCheckCallback() {
//		@Override
//		public void onDateCheck(int position) {
//			mRelativeLayout.setBackgroundResource(R.color.colorPrimary);
////			Toast.makeText(getActivity(), "선택", Toast.LENGTH_SHORT).show();
//			Log.i("????", numberView.getText()+"");
////			mCheckDate = item;
////			notifyDataSetInvalidated();
//		}
//	};

	public CalendarItemView(Context context) {
		super(context);
//		((MyJobCalendarFragment)((MainActivity) context).
//				getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_MY_JOB).
//				getChildFragmentManager().getFragments().get(1)).setOnDateCheckCallback(callback);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.view_job_calendar_item, this);
//		((MyJobCalendarFragment)this.getParent()).setOnDateCheckCallback(callback);
		mRelativeLayout = (RelativeLayout)findViewById(R.id.calendar_item_view);
		numberView = (TextView)findViewById(R.id.job_calendar_num);
		startView = (TextView)findViewById(R.id.job_calendar_start_count);
		endView = (TextView)findViewById(R.id.job_calendar_end_count);
		imageRedBall = (ImageView)findViewById(R.id.image_red_ball);
		imageBlueBall = (ImageView)findViewById(R.id.image_blue_ball);
	}
	
	public void setData(CalendarItem item) {
		mItem = item;
		int textColor;
		if (!item.inMonth) {
			textColor = getResources().getColor(R.color.colorCalendarOutMonth);
			backgroundColor = getResources().getColor(R.color.colorBackground);
		} else {
			backgroundColor = Color.WHITE;
//			mLinearLayout.setBackgroundColor(Color.WHITE);
			switch (item.dayOfWeek) {
				case Calendar.SUNDAY :
					textColor = getResources().getColor(R.color.colorCalendarSun);
					break;
				case Calendar.SATURDAY :
					textColor = getResources().getColor(R.color.colorCalendarSat);
					break;
				default :
					textColor = getResources().getColor(R.color.colorCalendarInMonth);
					break;
			}
		}
		numberView.setTextColor(textColor);
		mRelativeLayout.setBackgroundColor(backgroundColor);
		numberView.setText("" + item.dayOfMonth);

		int start = item.getStartItems().size();
		int end = item.getEndItems().size();
		if(start == 0){
			imageBlueBall.setVisibility(GONE);
			startView.setVisibility(GONE);
		}else{
			imageBlueBall.setVisibility(VISIBLE);
			startView.setVisibility(VISIBLE);
		}
		if(end == 0){
			endView.setVisibility(INVISIBLE);
			imageRedBall.setVisibility(INVISIBLE);
		}else{
			endView.setVisibility(VISIBLE);
			imageRedBall.setVisibility(VISIBLE);
		}
		startView.setText(start + "개");
		endView.setText(end + "개");
		if(item.checked){
			mRelativeLayout.setBackgroundResource(R.color.colorPrimary);
		}
	}

//
//	public void test(){
//		this.getParent().getClass().toString()
//	}

//	private boolean checked = false;
//	@Override
//	public void setChecked(boolean checked) {
//		this.checked = checked;
//		if(this.checked){
//			mRelativeLayout.setBackgroundResource(R.color.colorPrimary);
////			mCallback.onDateCheck(mItem);
//		}else{
//			mRelativeLayout.setBackgroundColor(backgroundColor);
//		}
//	}
//
//	@Override
//	public boolean isChecked() {
//		return checked;
//	}
//
//	@Override
//	public void toggle() {
//		setChecked(!checked);
//	}
}
