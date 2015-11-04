package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.ExpandableAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementFragment extends Fragment {

    ExpandableListView mExpandableListView;
    ExpandableAdapter mExpandableAdapter;

    public AnnouncementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView)getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.alarm);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_announcement, container, false);
        mExpandableListView =(ExpandableListView)view.findViewById(R.id.listview_expandable);
        mExpandableAdapter = new ExpandableAdapter();
        mExpandableListView.setAdapter(mExpandableAdapter);
        mExpandableListView.setGroupIndicator(null);
        initExpandableMenu();
        return view;
    }

    private void initExpandableMenu() {
        mExpandableAdapter.add("First Announcement","Hello World");
        mExpandableAdapter.add("Second Announcement","Hello World");
        mExpandableAdapter.add("Third Announcement","Hello World");
        mExpandableAdapter.add("Fourth AnnouncementFourth AnnouncementFourth Announcement","Hello World");
    }


}
