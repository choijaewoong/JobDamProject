package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.androidchoi.jobdam.ExpandableChildItemView;
import com.example.androidchoi.jobdam.ExpandableGroupItemView;
import com.example.androidchoi.jobdam.Model.ExpandableGroupData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2015-11-03.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {

    List<ExpandableGroupData> mItems = new ArrayList<ExpandableGroupData>();

    public void add(String title, String content) {
        ExpandableGroupData data = new ExpandableGroupData(title, content);
        mItems.add(data);

        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {
        return mItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItems.get(groupPosition).getContent();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return (long) groupPosition << 32 | 0xffffffff;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (long) groupPosition << 32 | childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandableGroupItemView view;
        if (convertView != null) {
            view = (ExpandableGroupItemView) convertView;
        } else {
            view = new ExpandableGroupItemView(parent.getContext());
        }
        view.setExpandableTitle(mItems.get(groupPosition));
        view.setSelectTitle(isExpanded);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ExpandableChildItemView view;
        if (convertView != null) {
            view = (ExpandableChildItemView) convertView;
        } else {
            view = new ExpandableChildItemView(parent.getContext());
        }
        view.setExpandableContent(mItems.get(groupPosition));
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}