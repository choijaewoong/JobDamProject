package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Calendar.CalendarAdapter;
import com.example.androidchoi.jobdam.Calendar.CalendarData;
import com.example.androidchoi.jobdam.Calendar.CalendarManager;
import com.example.androidchoi.jobdam.Calendar.ItemData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobCalendarFragment extends Fragment {

    TextView titleView;
    GridView gridView;
    CalendarAdapter mAdapter;

    public static boolean isWeekCalendar = false;

    ArrayList<ItemData> mItemdata = new ArrayList<ItemData>();

    public MyJobCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemdata.add(new ItemData(2015, 9, 10, "A"));
        mItemdata.add(new ItemData(2015,9,11,"B"));
        mItemdata.add(new ItemData(2015,9,12,"C"));
        mItemdata.add(new ItemData(2015,9,15,"D"));
        mItemdata.add(new ItemData(2015,9,21,"E"));
        mItemdata.add(new ItemData(2015, 9, 21, "F"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_my_job_calendar, container, false);
        View view = inflater.inflate(R.layout.fragment_my_job_calendar, container, false);
        titleView = (TextView)view.findViewById(R.id.title);
        ImageView imageCalendarButton = (ImageView)view.findViewById(R.id.image_next_month_button);
        imageCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isWeekCalendar) {
                    CalendarData data = CalendarManager.getInstance().getNextWeekCalendarData();
                    titleView.setText("" + data.year + "." + (data.weekOfYear) +"주");
                    mAdapter.setCalendarData(data);
                } else {
                    CalendarData data = CalendarManager.getInstance().getNextMonthCalendarData();
                    titleView.setText("" + data.year + "." + (data.month + 1));
                    mAdapter.setCalendarData(data);
                }
            }
        });

        imageCalendarButton = (ImageView)view.findViewById(R.id.image_prev_month_button);
        imageCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isWeekCalendar) {
                    CalendarData data = CalendarManager.getInstance().getPrevWeekCalendarData();
                    titleView.setText("" + data.year + "." + (data.weekOfYear) +"주");
                    mAdapter.setCalendarData(data);
                } else {
                    CalendarData data = CalendarManager.getInstance().getLastMonthCalendarData();
                    titleView.setText("" + data.year + "." + (data.month + 1));
                    mAdapter.setCalendarData(data);
                }
            }
        });
        gridView = (GridView)view.findViewById(R.id.gridView1);
        try {
            CalendarManager.getInstance().setDataObject(mItemdata);
        } catch (CalendarManager.NoComparableObjectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (isWeekCalendar) {
            CalendarData data = CalendarManager.getInstance().getWeekCalendarData();
            titleView.setText("" + data.year + "." + (data.weekOfYear) +"주");
            mAdapter = new CalendarAdapter(getActivity(), data);
        } else {
            CalendarData data = CalendarManager.getInstance().getCalendarData();
            titleView.setText("" + data.year + "." + (data.month + 1));
            mAdapter = new CalendarAdapter(getActivity(), data);
        }
        gridView.setAdapter(mAdapter);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        return view;
    }
}
