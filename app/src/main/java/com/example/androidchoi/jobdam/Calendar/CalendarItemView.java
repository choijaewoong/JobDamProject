package com.example.androidchoi.jobdam.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarItemView extends LinearLayout {

	TextView numberView;
	TextView contentView;
	CalendarItem mItem;
	LinearLayout mLinearLayout;

	public CalendarItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.view_calendar_item, this);
		mLinearLayout = (LinearLayout)findViewById(R.id.calendar_item_view);
		numberView = (TextView)findViewById(R.id.calendar_num);
		contentView = (TextView)findViewById(R.id.calendar_content);
	}
	
	public void setData(CalendarItem item) {
		mItem = item;
		int textColor;
		if (!item.inMonth) {
			textColor = getResources().getColor(R.color.colorCalendarOutMonth);
			mLinearLayout.setBackgroundResource(R.color.colorBackground);
		} else {
			mLinearLayout.setBackgroundColor(Color.WHITE);
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
		numberView.setText("" + item.dayOfMonth);

		ArrayList items = item.items;
		int size = items.size();
		StringBuilder sb = new StringBuilder();
		sb.append(size + "개");
		contentView.setText(sb.toString());
		
	}

}
