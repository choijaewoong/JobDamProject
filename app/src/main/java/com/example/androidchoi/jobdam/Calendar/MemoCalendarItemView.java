package com.example.androidchoi.jobdam.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.R;

import java.util.Calendar;

/**
 * Created by Choi on 2015-11-22.
 */
public class MemoCalendarItemView extends LinearLayout implements Checkable {
    TextView numberView;
    CalendarItem mItem;
    int backgroundColor;


    public MemoCalendarItemView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        LayoutInflater.from(context).inflate(R.layout.view_memo_calendar_item, this);
        numberView = (TextView)findViewById(R.id.memo_calendar_num);
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
        numberView.setBackgroundColor(backgroundColor);
        numberView.setText("" + item.dayOfMonth);
    }
    private boolean checked = false;
    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        if(checked){
            numberView.setBackgroundResource(R.color.colorPrimary);
        }else{
            numberView.setBackgroundColor(backgroundColor);
        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}
