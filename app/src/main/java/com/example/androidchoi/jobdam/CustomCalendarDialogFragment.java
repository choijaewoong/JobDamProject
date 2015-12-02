package com.example.androidchoi.jobdam;


import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Calendar.CalendarData;
import com.example.androidchoi.jobdam.Calendar.CalendarItem;
import com.example.androidchoi.jobdam.Calendar.CalendarManager;
import com.example.androidchoi.jobdam.Calendar.ItemData;
import com.example.androidchoi.jobdam.Calendar.MemoCalendarAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomCalendarDialogFragment extends DialogFragment {

    TextView titleView;
    GridView gridView;
    TextView textCheck;
    MemoCalendarAdapter mAdapter;
    String selectDate;
    boolean isStartDate;

    private static boolean isWeekCalendar = false;

    ArrayList<ItemData> mItemdata = new ArrayList<ItemData>();


    public CustomCalendarDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogAnimation);
        if(getTag().equals(CardWriteActivity.CALENDAR_START_DIALOG))
            isStartDate = true;
        else
            isStartDate = false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_calendar_dialog_frament, container, false);
        titleView = (TextView)view.findViewById(R.id.title);
        textCheck = (TextView)view.findViewById(R.id.text_card_calender_check_button);
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
                    titleView.setText("" + data.year + "." + (data.weekOfYear) + "주");
                    mAdapter.setCalendarData(data);
                } else {
                    CalendarData data = CalendarManager.getInstance().getLastMonthCalendarData();
                    titleView.setText("" + data.year + "." + (data.month + 1));
                    mAdapter.setCalendarData(data);
                }
            }
        });
        gridView = (GridView)view.findViewById(R.id.gridView1);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        try {
            CalendarManager.getInstance().setDataObject(mItemdata);
        } catch (CalendarManager.NoComparableObjectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (isWeekCalendar) {
            CalendarData data = CalendarManager.getInstance().getWeekCalendarData();
            titleView.setText("" + data.year + "." + (data.weekOfYear) +"주");
            mAdapter = new MemoCalendarAdapter(getActivity(), data);
        } else {
            CalendarData data = CalendarManager.getInstance().getCalendarData();
            titleView.setText("" + data.year + "." + (data.month + 1));
            mAdapter = new MemoCalendarAdapter(getActivity(), data);
        }
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                isWeekCalendar = !isWeekCalendar;
//                refreshView();
                selectDate = ((CalendarItem) mAdapter.getItem(position)).year + "년 " +
                        (((CalendarItem) mAdapter.getItem(position)).month + 1) + "월 " +
                        ((CalendarItem) mAdapter.getItem(position)).dayOfMonth + "일";
                Toast.makeText(getActivity(), selectDate, Toast.LENGTH_SHORT).show();
            }
        });
        textCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardWriteActivity) getActivity()).setDate(selectDate, isStartDate);
                dismiss();
            }
        });
        return view;
    }
}



