package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.MyJobItemAdapter;
import com.example.androidchoi.jobdam.Calendar.CalendarAdapter;
import com.example.androidchoi.jobdam.Calendar.CalendarData;
import com.example.androidchoi.jobdam.Calendar.CalendarManager;
import com.example.androidchoi.jobdam.Calendar.ItemData;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.MyJobLab;
import com.example.androidchoi.jobdam.Model.MyJobs;
import com.example.androidchoi.jobdam.Model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobCalendarFragment extends Fragment {

    TextView titleView;
    GridView gridView;
    CalendarAdapter mCalendarAdapter;
    ListView mListView;
    MyJobItemAdapter mAdapter;

    private ArrayList<MyJobs> mJobList;
    private static boolean isWeekCalendar = false;

    ArrayList<ItemData> mItemdata;

    public MyJobCalendarFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mItemdata.add(new ItemData(2015, 9, 10, "A"));
//        mItemdata.add(new ItemData(2015,9,11,"B"));
//        mItemdata.add(new ItemData(2015,9,12,"C"));
//        mItemdata.add(new ItemData(2015,9,15,"D"));
//        mItemdata.add(new ItemData(2015,9,21,"E"));
//        mItemdata.add(new ItemData(2015, 9, 21, "F"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showMyJob();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(),"2" , Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.fragment_my_job_calendar, container, false);
        titleView = (TextView)view.findViewById(R.id.title);
        mAdapter = new MyJobItemAdapter();

        ImageView imageCalendarButton = (ImageView)view.findViewById(R.id.image_next_month_button);
        imageCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isWeekCalendar) {
                    CalendarData data = CalendarManager.getInstance().getNextWeekCalendarData();
                    titleView.setText("" + data.year + "." + (data.weekOfYear) +"주");
                    mCalendarAdapter.setCalendarData(data);
                } else {
                    CalendarData data = CalendarManager.getInstance().getNextMonthCalendarData();
                    titleView.setText("" + data.year + "." + (data.month + 1));
                    mCalendarAdapter.setCalendarData(data);
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
                    mCalendarAdapter.setCalendarData(data);
                } else {
                    CalendarData data = CalendarManager.getInstance().getLastMonthCalendarData();
                    titleView.setText("" + data.year + "." + (data.month + 1));
                    mCalendarAdapter.setCalendarData(data);
                }
            }
        });
        gridView = (GridView)view.findViewById(R.id.gridView1);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                isWeekCalendar = !isWeekCalendar;
//                refreshView();
//                Toast.makeText(getActivity(), ((CalendarItem)mAdapter.getItem(position)).dayOfMonth + "", Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }

    public void refreshView(){
        try {
            CalendarManager.getInstance().setDataObject(mItemdata);
        } catch (CalendarManager.NoComparableObjectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (isWeekCalendar) {
            CalendarData data = CalendarManager.getInstance().getWeekCalendarData();
            titleView.setText("" + data.year + "." + (data.weekOfYear) +"주");
            mCalendarAdapter = new CalendarAdapter(getActivity(), data);
        } else {
            CalendarData data = CalendarManager.getInstance().getCalendarData();
            titleView.setText("" + data.year + "." + (data.month + 1));
            mCalendarAdapter = new CalendarAdapter(getActivity(), data);
        }
        gridView.setAdapter(mCalendarAdapter);

    }

    public void showMyJob(){
        NetworkManager.getInstance().showMyJob(getActivity(), User.USER_NAME, new NetworkManager.OnResultListener<MyJobLab>() {
            @Override
            public void onSuccess(MyJobLab result) {
                mJobList = result.getJobList();
                mAdapter.setItems(mJobList);
                mItemdata = new ArrayList<ItemData>();
                for(int i=0; i<mJobList.size(); i++){
                    mItemdata.add(new ItemData(mJobList.get(i).getJob(), true));
                    mItemdata.add(new ItemData(mJobList.get(i).getJob(), false));
                }
                refreshView();
            }

            @Override
            public void onFail(int code) {
                Toast.makeText(getActivity(), code + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
