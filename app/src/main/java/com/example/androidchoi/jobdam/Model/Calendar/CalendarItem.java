package com.example.androidchoi.jobdam.Model.Calendar;

import java.util.ArrayList;

public class CalendarItem {
		
	public int year;
	public int month;
	public int dayOfMonth;
	public int dayOfWeek;
	public boolean inMonth;
	public boolean checked = false;
	private ArrayList startItems = new ArrayList();
	private ArrayList endItems = new ArrayList();
	public ArrayList getStartItems() { return startItems; }
	public ArrayList getEndItems() {
		return endItems;
	}
}
