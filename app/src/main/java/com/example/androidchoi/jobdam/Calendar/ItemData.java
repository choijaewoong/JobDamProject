package com.example.androidchoi.jobdam.Calendar;

import com.example.androidchoi.jobdam.Model.MyJob;

import java.util.Calendar;
import java.util.Date;

public class ItemData implements CalendarManager.CalendarComparable<ItemData>{

	public int year;
	public int month;
	public int day;
	private MyJob myJob;
	private boolean isStart = true;
	public boolean isStart() { return isStart; }
	public MyJob getMyJob() { return myJob; }

	public ItemData(MyJob myJob, boolean isStart) {
		Date date;
		this.myJob = myJob;
		this.isStart = isStart;
		if(this.isStart){
			date = new Date(myJob.getStart() * 1000L);
		}else {
			date = new Date(myJob.getEnd() * 1000L);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		this.year = calendar.get(calendar.YEAR);
		this.month = calendar.get(calendar.MONTH);
		this.day = calendar.get(calendar.DAY_OF_MONTH);
	}
	
	@Override
	public int compareDate(int year, int month, int day) {
		// TODO Auto-generated method stub
		return ((this.year - year) * 12 + (this.month - month)) * 31 + this.day - day;
	}
	
	@Override
	public int compareToUsingCalendar(ItemData another) {
		// TODO Auto-generated method stub
		return ((this.year - another.year) * 12 + (this.month - another.month)) * 31 + this.day - another.day;
	}
	
}
