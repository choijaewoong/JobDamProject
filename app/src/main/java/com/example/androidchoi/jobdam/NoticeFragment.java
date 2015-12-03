package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.NoticeAdapter;
import com.example.androidchoi.jobdam.Model.NoticeContentData;
import com.example.androidchoi.jobdam.Model.NoticeDateData;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {

//    ExpandableListView mAnnouncementListView;
//    NoticeAdapter mNoticeAdapter;
    ListView mListView;
    NoticeAdapter mNoticeAdapter;

    public NoticeFragment() {
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
        View shadowToolbar = getActivity().findViewById(R.id.toolbar_shadow);
        shadowToolbar.setVisibility(View.VISIBLE);
        View view =  inflater.inflate(R.layout.fragment_notice, container, false);
        mListView = (ListView)view.findViewById(R.id.listView_notice);
        mNoticeAdapter = new NoticeAdapter();
        // add listview header
        mListView.setAdapter(mNoticeAdapter);
//        mAnnouncementListView =(ExpandableListView)view.findViewById(R.id.listview_announcement);
//        mNoticeAdapter = new NoticeAdapter();
//        mAnnouncementListView.setAdapter(mNoticeAdapter);
//        mAnnouncementListView.setGroupIndicator(null);
        initListItem();
        return view;
    }



    private void initListItem() {
        mNoticeAdapter.add(new NoticeDateData("15.12.04"));
        mNoticeAdapter.add(new NoticeContentData(0, Html.fromHtml(getString(R.string.notice0)), "12:00", true));
        mNoticeAdapter.add(new NoticeContentData(0, Html.fromHtml(getString(R.string.notice2)), "10:30", true));
        mNoticeAdapter.add(new NoticeDateData("15.12.03"));
        mNoticeAdapter.add(new NoticeContentData(0, Html.fromHtml(getString(R.string.notice5)), "18:00", true));
        mNoticeAdapter.add(new NoticeContentData(0, Html.fromHtml(getString(R.string.notice3)), "13:00", true));
        mNoticeAdapter.add(new NoticeContentData(0, Html.fromHtml(getString(R.string.notice4)), "09:00", true));
        mNoticeAdapter.notifyDataSetChanged();
    }
//
//    private void initExpandableMenu() {
//        ContentData contentData = new ContentData("Hello World");
//        mNoticeAdapter.add("First Announcement", contentData);
//        mNoticeAdapter.add("Second Announcement",contentData);
//        mNoticeAdapter.add("Third Announcement", contentData);
//        mNoticeAdapter.add("Fourth AnnouncementFourth AnnouncementFourth Announcement",contentData);
//    }
}
