package com.example.androidchoi.jobdam.Dialog;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.MyJobItemAdapter;
import com.example.androidchoi.jobdam.JobDetailActivity;
import com.example.androidchoi.jobdam.Model.Calendar.CalendarItem;
import com.example.androidchoi.jobdam.Model.Calendar.ItemData;
import com.example.androidchoi.jobdam.Model.Job;
import com.example.androidchoi.jobdam.Model.MyJob;
import com.example.androidchoi.jobdam.Model.MyJobs;
import com.example.androidchoi.jobdam.R;
import com.example.androidchoi.jobdam.Util.ListUtils;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobCalendarListDialogFragment extends DialogFragment {

    ListView mStartListView;
    ListView mEndListView;
    MyJobItemAdapter mStartJobAdapter;
    MyJobItemAdapter mEndJobAdapter;
    TextView textStartHeader;
    TextView textEndHeader;
    ScrollView mScrollView;
    TextView mTextCloseButton;
//    TextView mTextDate;

    private ArrayList<MyJobs> mJobList;
    private CalendarItem mCalendarItem;

    public MyJobCalendarListDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dialog_height));
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.colorBackground));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_job_calendar_dialog_list, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        mTextCloseButton = (TextView)view.findViewById(R.id.text_my_job_list_close_button);
        mTextCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        mTextDate = (TextView)view.findViewById(R.id.text_my_job_list_dialog_date);
//        mTextDate.setText(mCalendarItem.year + ". " + mCalendarItem.month + ". " + mCalendarItem.dayOfMonth);

        mScrollView = (ScrollView)view.findViewById(R.id.scrollView_my_job_list);
        mStartListView = (ListView) view.findViewById(R.id.listView_start_job);
        mEndListView = (ListView) view.findViewById(R.id.listView_end_job);
        mStartJobAdapter = new MyJobItemAdapter();
        mEndJobAdapter = new MyJobItemAdapter();
        mStartListView.setAdapter(mStartJobAdapter);
        mEndListView.setAdapter(mEndJobAdapter);
        // 해당 날짜에 시작한 채용 공고 리스트
        mStartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job data = (Job) mStartJobAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(Job.JOBITEM, data);
                startActivity(intent);
            }
        });
        // 해당 날짜에 마감하는 채용 공고 리스트
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

//        mStartJobAdapter.setItems(new ArrayList<MyJobs>());
//        mEndJobAdapter.setItems(new ArrayList<MyJobs>());
        int startCount = mCalendarItem.getStartItems().size();
        int endCount = mCalendarItem.getEndItems().size();

        if (startCount != 0) {
            for (int i = 0; i < startCount; i++) {
                mStartJobAdapter.add(findJob(((ItemData) mCalendarItem.getStartItems().get(i)).getJobId()));
            }
            ListUtils.setDynamicHeight(mStartListView);
            textStartHeader.setVisibility(View.VISIBLE);
        } else {
            textStartHeader.setVisibility(View.GONE);
        }
        if (endCount != 0) {
            for (int i = 0; i < endCount; i++) {
                mEndJobAdapter.add(findJob(((ItemData) mCalendarItem.getEndItems().get(i)).getJobId()));
            }
            ListUtils.setDynamicHeight(mEndListView);
            textEndHeader.setVisibility(View.VISIBLE);
        } else {
            textEndHeader.setVisibility(View.GONE);
        }
        mStartJobAdapter.notifyDataSetChanged();
        mEndJobAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
            mScrollView.smoothScrollTo(0, 0);
    }

    public void setData(CalendarItem calendarItem, ArrayList<MyJobs> jobList){
        mCalendarItem = calendarItem;
        mJobList = jobList;
    }



    public MyJob findJob(int id) {
        for (MyJobs myJobs : mJobList) {
            if (myJobs.getJob().getId() == id)
                return myJobs.getJob();
        }
        return null;
    }
}
