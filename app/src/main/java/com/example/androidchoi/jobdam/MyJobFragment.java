package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyJobFragment extends Fragment {

    private static final String LIST = "joblist";
    private static final String CALENDAR = "jobcalendar";

    TabHost tabHost;
    ViewPager pager;
    TabsAdapter mAdapter;

    public MyJobFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_job, container, false);
        tabHost = (TabHost)view.findViewById(R.id.tabHost);
        tabHost.setup();
        pager = (ViewPager)view.findViewById(R.id.pager);
        mAdapter = new TabsAdapter(getActivity(), getChildFragmentManager(), tabHost, pager);
        mAdapter.addTab(tabHost.newTabSpec(LIST).setIndicator("잡담 리스트"), TabFragment.class, null);
        mAdapter.addTab(tabHost.newTabSpec(CALENDAR).setIndicator("잡담 캘린더"), TabFragment.class, null);

        return view;
    }
}