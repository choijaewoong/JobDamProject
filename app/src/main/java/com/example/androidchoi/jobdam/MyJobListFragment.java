package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.JobItemAdapter;
import com.example.androidchoi.jobdam.Model.JobData;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobListFragment extends Fragment {
    ListView mListView;
    JobItemAdapter mAdapter;

//    public static MyJobListFragment newInstance(int page){
//        Bundle args = new Bundle();
//        args.putInt("ddd",page);
//        MyJobListFragment myJobListFragment = new MyJobListFragment();
//        myJobListFragment.setArguments(args);
//        return myJobListFragment;
//    }

    public MyJobListFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_job_list, container, false);
        View countHeaderView = inflater.inflate(R.layout.view_item_count_header, null);

        mListView = (ListView)view.findViewById(R.id.listview_my_job);
        mListView.addHeaderView(countHeaderView, null, false);
        mAdapter = new JobItemAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobData data = (JobData) mAdapter.getItem(position - 1);
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra(JobData.JOBITEM, data);
                startActivity(intent);
            }
        });
        initData();
        TextView textView = (TextView)view.findViewById(R.id.text_item_count);
        textView.setText("총 "+ mAdapter.getCount() + "건");
        return view;
    }
    private void initData() {
//        for(int i = 0; i<5; i++) {
//            JobData data = new JobData();
//            data.setJobTitle("기업" + i);
//            mAdapter.add(data);
//        }
    }
}
