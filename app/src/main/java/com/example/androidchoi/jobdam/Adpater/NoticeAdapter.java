package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.Model.NoticeContentData;
import com.example.androidchoi.jobdam.Model.NoticeData;
import com.example.androidchoi.jobdam.Model.NoticeDateData;
import com.example.androidchoi.jobdam.ItemView.NoticeContentItemView;
import com.example.androidchoi.jobdam.ItemView.NoticeDateItemView;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-03.
 */
public class NoticeAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int TYPE_INDEX_CONTENT = 0;
    private static final int TYPE_INDEX_DATE = 1;

    ArrayList<NoticeData> mItems = new ArrayList<NoticeData>();
    public void add(NoticeData data){
        mItems.add(data);
    }
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        NoticeData noticeData = mItems.get(position);
        if(noticeData instanceof NoticeContentData){
            return TYPE_INDEX_CONTENT;
        }else if(noticeData instanceof NoticeDateData){
            return TYPE_INDEX_DATE;
        }
        return  TYPE_INDEX_DATE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)){
            case TYPE_INDEX_CONTENT :
                NoticeContentItemView contentView;
                if(convertView != null && convertView instanceof NoticeContentItemView){
                    contentView = (NoticeContentItemView)convertView;
                }else{
                    contentView = new NoticeContentItemView(parent.getContext());
                }
                contentView.setItemData((NoticeContentData) mItems.get(position));
                return contentView;
            case TYPE_INDEX_DATE :
            default:
                NoticeDateItemView dateView;
                if(convertView != null && convertView instanceof  NoticeDateItemView){
                    dateView = (NoticeDateItemView)convertView;
                } else{
                    dateView = new NoticeDateItemView(parent.getContext());
                }
                dateView.setItemData((NoticeDateData) mItems.get(position));
                return dateView;
        }
    }
}


//    List<GroupData> mItems = new ArrayList<GroupData>();
//
//    public void add(String title, ContentData content) {
//        GroupData data = new GroupData(title, content);
//        mItems.add(data);
//
//        notifyDataSetChanged();
//    }
//    @Override
//    public int getGroupCount() {
//        return mItems.size();
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return 1;
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return mItems.get(groupPosition);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        return mItems.get(groupPosition).getChildData();
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return (long) groupPosition << 32 | 0xffffffff;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return (long) groupPosition << 32 | childPosition;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return true;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        ExpandableGroupItemView view;
//        if (convertView != null) {
//            view = (ExpandableGroupItemView) convertView;
//        } else {
//            view = new ExpandableGroupItemView(parent.getContext());
//        }
//        view.setExpandableTitle(mItems.get(groupPosition));
//        view.setSelectTitle(isExpanded);
//        return view;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        ExpandableChildContentItemView view;
//        if (convertView != null) {
//            view = (ExpandableChildContentItemView) convertView;
//        } else {
//            view = new ExpandableChildContentItemView(parent.getContext());
//        }
//        view.setExpandableContent((ContentData)mItems.get(groupPosition).getChildData());
//        return view;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return false;
//    }
