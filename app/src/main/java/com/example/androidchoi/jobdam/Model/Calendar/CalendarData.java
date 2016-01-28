package com.example.androidchoi.jobdam.Model.Calendar;

import java.util.ArrayList;

public class CalendarData {
	public int year;
	public int month;
	public int weekOfMonth;
	public int weekOfYear;

	public ArrayList<CalendarItem> days = new ArrayList<CalendarItem>();
}
