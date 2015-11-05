package com.example.androidchoi.jobdam;


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

    private static final String LIST = "joblist";
    private static final String CALENDAR = "jobcalendar";

    ViewPager mViewPager;
    TabLayout mTabLayout;
    MyFragmentPagerAdapter mAdapter;

    public MyJobFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_job, container, false);
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MyJobListFragment());
        fragments.add(new MyJobCalendarFragment());
        mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager = (ViewPager)view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mAdapter);
        mTabLayout = (TabLayout)view.findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }
}