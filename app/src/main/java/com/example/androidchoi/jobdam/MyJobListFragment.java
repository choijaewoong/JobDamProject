package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.androidchoi.jobdam.Adpater.JobItemAdapter;
import com.example.androidchoi.jobdam.Model.JobItemData;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobListFragment extends Fragment {

    ListView mListView;
    JobItemAdapter mAdapter;

    public MyJobListFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_job_list, container, false);

        mListView = (ListView)view.findViewById(R.id.listView_job);
        mAdapter = new JobItemAdapter();
        mListView.setAdapter(mAdapter);
        initData();
        return view;
    }
    private void initData() {
        for(int i = 0; i<20; i++) {
            JobItemData data = new JobItemData();
            mAdapter.add(data);
        }
    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_f2, menu);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.menu_f2)
//            Toast.makeText(getContext(), "F2M1 selected", Toast.LENGTH_SHORT).show();
//        return super.onOptionsItemSelected(item);
//    }
}
