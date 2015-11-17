package com.example.androidchoi.jobdam.Calendar;

import java.util.ArrayList;

public class CalendarItem {
		
	public int year;
	public int month;
	public int dayOfMonth;
	public int dayOfWeek;
	public boolean inMonth;
	ArrayList items = new ArrayList();
	public ArrayList getItems() {
		return items;
	}
}
