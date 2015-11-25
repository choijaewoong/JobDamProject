package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobFragment extends Fragment {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    MyFragmentPagerAdapter mAdapter;
//    private ArrayList<MyJobs> mJobList;
//    public ArrayList<MyJobs> getJobList() { return mJobList; }
//    public void setJobList(ArrayList<MyJobs> jobList) { mJobList = jobList; }

    public MyJobFragment() {
        // Required empty public constructor
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView)getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.my_job);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View shadowToolbar = getActivity().findViewById(R.id.toolbar_shadow);
        shadowToolbar.setVisibility(View.VISIBLE);
        View view = inflater.inflate(R.layout.fragment_my_job, container, false);
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MyJobListFragment());
        fragments.add(new MyJobCalendarFragment());
        mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager = (ViewPager)view.findViewById(R.id.tab_pager);
        mViewPager.setAdapter(mAdapter);
        mTabLayout = (TabLayout)view.findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }
}