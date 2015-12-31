package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.androidchoi.jobdam.Adpater.MyJobItemAdapter;
import com.example.androidchoi.jobdam.Calendar.CalendarAdapter;
import com.example.androidchoi.jobdam.Calendar.CalendarData;
import com.example.androidchoi.jobdam.Calendar.CalendarItem;
import com.example.androidchoi.jobdam.Calendar.CalendarManager;
import com.example.androidchoi.jobdam.Calendar.ItemData;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.Model.MyJob;
import com.example.androidchoi.jobdam.Model.MyJobLab;
import com.example.androidchoi.jobdam.Model.MyJobs;
import com.example.androidchoi.jobdam.Util.ListUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobCalendarFragment extends Fragment {

    TextView titleView;
    GridView gridView;
    CalendarAdapter mCalendarAdapter;
    ListView mStartListView;
    ListView mEndListView;
    MyJobItemAdapter mStartJobAdapter;
    MyJobItemAdapter mEndJobAdapter;
    TextView textStartHeader;
    TextView textEndHeader;
    ToggleButton mToggleCalendarType;

    private ArrayList<MyJobs> mJobList;
    private static boolean isWeekCalendar = true;

    ArrayList<ItemData> mItemdata;

    public MyJobCalendarFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_my_job_calendar, container, false);
        titleView = (TextView) view.findViewById(R.id.title);
        ImageView imageCalendarButton = (ImageView) view.findViewById(R.id.image_next_month_button);
        imageCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isWeekCalendar) {
                    CalendarData data = CalendarManager.getInstance().getNextWeekCalendarData();
                    titleView.setText((data.year) + " . " + (data.month + 1));
                    mCalendarAdapter.setCalendarData(data);
                } else {
                    CalendarData data = CalendarManager.getInstance().getNextMonthCalendarData();
                    titleView.setText("" + data.year + " . " + (data.month + 1));
                    mCalendarAdapter.setCalendarData(data);
                }
            }
        });
        imageCalendarButton = (ImageView) view.findViewById(R.id.image_prev_month_button);
        imageCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isWeekCalendar) {
                    CalendarData data = CalendarManager.getInstance().getPrevWeekCalendarData();
                    titleView.setText((data.year) + " . " + (data.month + 1));
                    mCalendarAdapter.setCalendarData(data);
                } else {
                    CalendarData data = CalendarManager.getInstance().getLastMonthCalendarData();
                    titleView.setText("" + data.year + " . " + (data.month + 1));
                    mCalendarAdapter.setCalendarData(data);
                }
            }
        });
        mStartListView = (ListView) view.findViewById(R.id.listView_start_job);
        mEndListView = (ListView) view.findViewById(R.id.listView_end_job);
        mStartJobAdapter = new MyJobItemAdapter();
        mEndJobAdapter = new MyJobItemAdapter();
        mStartListView.setAdapter(mStartJobAdapter);
        mEndListView.setAdapter(mEndJobAdapter);
        mStartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job data = (Job) mStartJobAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(Job.JOBITEM, data);
                startActivity(intent);
            }
        });
        mEndListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job data = (Job) mEndJobAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(Job.JOBITEM, data);
                startActivity(intent);
            }
        });

        textStartHeader = (TextView) view.findViewById(R.id.text_start_header);
        textEndHeader = (TextView) view.findViewById(R.id.text_end_header);
        gridView = (GridView) view.findViewById(R.id.grid_view_calendar);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarItem calendarItem = mCallback.onDateCheck(position);
//                if (isWeekCalendar == false) {
//                    isWeekCalendar = true;
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.set(calendarItem.year, calendarItem.month, calendarItem.dayOfMonth);
//                    changeCalendar(calendar);
//                    refreshView(((CalendarItem)mCalendarAdapter.getItem(position)));
//                } else {
                if (isWeekCalendar == false) {

                }
                mStartJobAdapter.setItems(new ArrayList<MyJobs>());
                mEndJobAdapter.setItems(new ArrayList<MyJobs>());
                int startCount = calendarItem.getStartItems().size();
                int endCount = calendarItem.getEndItems().size();

                if (startCount != 0) {
                    textStartHeader.setVisibility(View.VISIBLE);
                    for (int i = 0; i < startCount; i++) {
                        mStartJobAdapter.add(findJob(((ItemData) calendarItem.getStartItems().get(i)).getJobId()));
                    }
                } else {
                    textStartHeader.setVisibility(View.GONE);
                }
                if (endCount != 0) {
                    textEndHeader.setVisibility(View.VISIBLE);
                    for (int i = 0; i < endCount; i++) {
                        mEndJobAdapter.add(findJob(((ItemData) calendarItem.getEndItems().get(i)).getJobId()));
                    }
                } else {
                    textEndHeader.setVisibility(View.GONE);
                }
                ListUtils.setDynamicHeight(mStartListView);
                ListUtils.setDynamicHeight(mEndListView);
                mStartJobAdapter.notifyDataSetChanged();
                mEndJobAdapter.notifyDataSetChanged();
            }
        });
        mToggleCalendarType = (ToggleButton) view.findViewById(R.id.toggleButton_calendar_type);
        mToggleCalendarType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isWeekCalendar = !isWeekCalendar;
                refreshView();
                textStartHeader.setVisibility(View.GONE);
                textEndHeader.setVisibility(View.GONE);
                mStartJobAdapter.setItems(new ArrayList<MyJobs>());
                mEndJobAdapter.setItems(new ArrayList<MyJobs>());
            }
        });
        return view;
    }

    // 날짜 체크 콜백
    public interface OnDateCheckCallback {
        CalendarItem onDateCheck(int position);
    }

    private OnDateCheckCallback mCallback;

    public void setOnDateCheckCallback(OnDateCheckCallback callback) {
        mCallback = callback;
    }

    public void refreshView() {
        try {
            CalendarManager.getInstance().setDataObject(mItemdata);
        } catch (CalendarManager.NoComparableObjectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (isWeekCalendar) {
            CalendarData data = CalendarManager.getInstance().getWeekCalendarData();
            titleView.setText((data.year) + " . " + (data.month + 1));
            mCalendarAdapter = new CalendarAdapter(getActivity(), data);
        } else {
            CalendarData data = CalendarManager.getInstance().getCalendarData();
            titleView.setText("" + data.year + " . " + (data.month + 1));
            mCalendarAdapter = new CalendarAdapter(getActivity(), data);
        }
        gridView.setAdapter(mCalendarAdapter);
//        mAdapter.add(((ItemData)mCalendarAdapter.getCheckDate().getStartItems().get(0)).getMyJob());
    }

    public void changeCalendar(Calendar calendar) {
        try {
            CalendarManager.getInstance().setDataObject(mItemdata);
        } catch (CalendarManager.NoComparableObjectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (isWeekCalendar) {
            CalendarData data = CalendarManager.getInstance().getWeekCalendarData(calendar);
            titleView.setText((data.year) + " . " + (data.month + 1));
            mCalendarAdapter = new CalendarAdapter(getActivity(), data);
        } else {
            CalendarData data = CalendarManager.getInstance().getCalendarData(calendar);
            titleView.setText("" + data.year + " . " + (data.month + 1));
            mCalendarAdapter = new CalendarAdapter(getActivity(), data);
        }
        gridView.setAdapter(mCalendarAdapter);
//        mAdapter.add(((ItemData)mCalendarAdapter.getCheckDate().getStartItems().get(0)).getMyJob());
    }

    public void showMyJob() {
        NetworkManager.getInstance().showMyJob(getActivity(), new NetworkManager.OnResultListener<MyJobLab>() {
            @Override
            public void onSuccess(MyJobLab result) {
                mJobList = result.getJobList();
//                mAdapter.setItems(mJobList);
                mItemdata = new ArrayList<ItemData>();
                for (int i = 0; i < mJobList.size(); i++) {
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

    public MyJob findJob(int id) {
        for (MyJobs myJobs : mJobList) {
            if (myJobs.getJob().getId() == id)
                return myJobs.getJob();
        }
        return null;
    }
}
