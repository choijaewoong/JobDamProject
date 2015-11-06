package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.AnnouncementAdapter;
import com.example.androidchoi.jobdam.Model.ContentData;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementFragment extends Fragment {

    ExpandableListView mAnnouncementListView;
    AnnouncementAdapter mAnnouncementAdapter;

    public AnnouncementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView)getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.announcement);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_announcement, container, false);
        mAnnouncementListView =(ExpandableListView)view.findViewById(R.id.listview_announcement);
        mAnnouncementAdapter = new AnnouncementAdapter();
        mAnnouncementListView.setAdapter(mAnnouncementAdapter);
        mAnnouncementListView.setGroupIndicator(null);
        initExpandableMenu();
        return view;
    }

    private void initExpandableMenu() {
        ContentData contentData = new ContentData("Hello World");
        mAnnouncementAdapter.add("First Announcement", contentData);
        mAnnouncementAdapter.add("Second Announcement",contentData);
        mAnnouncementAdapter.add("Third Announcement", contentData);
        mAnnouncementAdapter.add("Fourth AnnouncementFourth AnnouncementFourth Announcement",contentData);
    }
}
