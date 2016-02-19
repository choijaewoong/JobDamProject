package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.CalendarAdapter;
import com.example.androidchoi.jobdam.Dialog.MyJobCalendarListDialogFragment;
import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Model.Calendar.CalendarData;
import com.example.androidchoi.jobdam.Model.Calendar.CalendarItem;
import com.example.androidchoi.jobdam.Manager.CalendarManager;
import com.example.androidchoi.jobdam.Model.Calendar.ItemData;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.MyJobLab;
import com.example.androidchoi.jobdam.Model.MyJobs;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobCalendarFragment extends Fragment {

    public static final String DIALOG = "my_job_dialog";

    TextView titleView;
    GridView gridView;
    CalendarAdapter mCalendarAdapter;

    private ArrayList<MyJobs> mJobList;
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
                    CalendarData data = CalendarManager.getInstance().getNextMonthCalendarData();
                    titleView.setText("" + data.year + " . " + (data.month + 1));
                    mCalendarAdapter.setCalendarData(data);
            }
        });
        imageCalendarButton = (ImageView) view.findViewById(R.id.image_prev_month_button);
        imageCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                    CalendarData data = CalendarManager.getInstance().getLastMonthCalendarData();
                    titleView.setText("" + data.year + " . " + (data.month + 1));
                    mCalendarAdapter.setCalendarData(data);
            }
        });

        gridView = (GridView) view.findViewById(R.id.grid_view_calendar);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarItem calendarItem = mCallback.onDateCheck(position);
                int startCount = calendarItem.getStartItems().size();
                int endCount = calendarItem.getEndItems().size();
                if(startCount == 0 && endCount == 0){
                    return;
                }
                MyJobCalendarListDialogFragment dialog = new MyJobCalendarListDialogFragment();
                dialog.setData(calendarItem, mJobList);
                dialog.show(getActivity().getSupportFragmentManager(), DIALOG);
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
            CalendarData data = CalendarManager.getInstance().getCalendarData();
            titleView.setText("" + data.year + " . " + (data.month + 1));
            mCalendarAdapter = new CalendarAdapter(getActivity(), data);
        gridView.setAdapter(mCalendarAdapter);
    }

    public void showMyJob() {
        NetworkManager.getInstance().showMyJob(getActivity(), new NetworkManager.OnResultListener<MyJobLab>() {
            @Override
            public void onSuccess(MyJobLab result) {
                mJobList = result.getJobList();
                mItemdata = new ArrayList<ItemData>();
                for (int i = 0; i < mJobList.size(); i++) {
                    mItemdata.add(new ItemData(mJobList.get(i).getJob(), true));
                    mItemdata.add(new ItemData(mJobList.get(i).getJob(), false));
                }
                refreshView();
            }

            @Override
            public void onFail(int code) {
                Log.i("error : ", code + "");
                Toast.makeText(MyApplication.getContext(), "데이터를 불러 올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
