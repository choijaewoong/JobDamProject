package com.example.androidchoi.jobdam.Adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.androidchoi.jobdam.ItemView.ExpandableChildAddressItemView;
import com.example.androidchoi.jobdam.ItemView.ExpandableChildContentItemView;
import com.example.androidchoi.jobdam.ItemView.ExpandableChildPeriodItemView;
import com.example.androidchoi.jobdam.ItemView.ExpandableChildQuestionItemView;
import com.example.androidchoi.jobdam.ItemView.ExpandableGroupItemView;
import com.example.androidchoi.jobdam.Model.AddressData;
import com.example.androidchoi.jobdam.Model.ChildData;
import com.example.androidchoi.jobdam.Model.ContentData;
import com.example.androidchoi.jobdam.Model.GroupData;
import com.example.androidchoi.jobdam.Model.PeriodData;
import com.example.androidchoi.jobdam.Model.QuestionData;
import com.example.androidchoi.jobdam.Model.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2015-11-04.
 */
public class JobDetailAdapter extends BaseExpandableListAdapter {

    List<GroupData> mItems = new ArrayList<GroupData>();
    String  mJobId;
    String mCorpName;
    private static final int VIEW_TYPE_COUNT = 4;
    private static final int TYPE_INDEX_CONTENT = 0;
    private static final int TYPE_INDEX_ADDRESS = 1;
    private static final int TYPE_INDEX_QUESTION = 2;
    private static final int TYPE_INDEX_PERIOD = 3;

    public void add(String title, ArrayList<ChildData> childDataList) {
        GroupData data = new GroupData(title, childDataList);
        mItems.add(data);
        notifyDataSetChanged();
    }
    public void add(String title, ChildData content) {
        List<ChildData> childDataList = new ArrayList<ChildData>();
        childDataList.add(content);
        GroupData data = new GroupData(title, childDataList);
        mItems.add(data);
        notifyDataSetChanged();
    }
    public void addQuestion(String title, Questions questions){
        GroupData data = new GroupData(title, questions);
        mItems.add(data);
        notifyDataSetChanged();
    }

    public void setData(String jobId, String corpName){
        mJobId = jobId;
        mCorpName = corpName;
    }

    public void setClear(){
        mItems = new ArrayList<GroupData>();
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mItems.get(groupPosition).getChildDataList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItems.get(groupPosition).getChildDataList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return (long)groupPosition<<32 | 0xffffffff;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (long)groupPosition << 32 | childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandableGroupItemView view;
        if(convertView != null){
            view = (ExpandableGroupItemView)convertView;
        }else {
            view = new ExpandableGroupItemView(parent.getContext());
        }
        view.setExpandableTitle(mItems.get(groupPosition));
        view.setExapandableFoldImage(isExpanded);
        return view;
    }
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        switch(getChildType(groupPosition,childPosition)){
            case TYPE_INDEX_CONTENT : {
                ExpandableChildContentItemView view;
                if(convertView != null){
                    view = (ExpandableChildContentItemView)convertView;
                } else {
                    view = new ExpandableChildContentItemView(parent.getContext());
                }
                view.setExpandableContent((ContentData)mItems.get(groupPosition).getChildDataList().get(childPosition));
                if(isLastChild){
                    view.setVisibleBottomPadding();
                }
                return view;
            }
            case TYPE_INDEX_ADDRESS : {
                ExpandableChildAddressItemView view;
                if(convertView != null){
                    view = (ExpandableChildAddressItemView)convertView;
                } else {
                    view = new ExpandableChildAddressItemView(parent.getContext());
                }
                view.setExpandableAddress((AddressData)mItems.get(groupPosition).getChildDataList().get(childPosition));
                return view;
            }
            case TYPE_INDEX_PERIOD: {
                ExpandableChildPeriodItemView view;
                if(convertView != null){
                    view = (ExpandableChildPeriodItemView)convertView;
                } else {
                    view = new ExpandableChildPeriodItemView(parent.getContext());
                }
                view.setExpandablePeriod((PeriodData)mItems.get(groupPosition).getChildDataList().get(childPosition));
                return view;
            }
            case TYPE_INDEX_QUESTION :
            default : {
                ExpandableChildQuestionItemView view;
                if(convertView != null){
                    view = (ExpandableChildQuestionItemView)convertView;
                }else{
                    view = new ExpandableChildQuestionItemView(parent.getContext());
                }
                view.setExpandableQuestion((QuestionData)mItems.get(groupPosition).getChildDataList().get(childPosition), mJobId, mCorpName, childPosition);
                // 마지막 질문 밑에 질문 수정 버튼, 상세보기 버튼 추가
                if(isLastChild){
                    view.setVisibleQuestionBottomLayout();
                }
                return view;
            }
        }
    }
    @Override
    public int getChildType(int groupPosition, int childPosition) {
        ChildData data = mItems.get(groupPosition).getChildDataList().get(childPosition);
        if(data instanceof ContentData){
            return TYPE_INDEX_CONTENT;
        } else if(data instanceof AddressData) {
            return TYPE_INDEX_ADDRESS;
        } else if (data instanceof PeriodData){
            return TYPE_INDEX_PERIOD;
        } else{ // data instanceof QuestionData
            return TYPE_INDEX_QUESTION;
        }
    }
    @Override
    public int getChildTypeCount() {
        return VIEW_TYPE_COUNT;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
