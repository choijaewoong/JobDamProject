package com.example.androidchoi.jobdam.Calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidchoi.jobdam.CategoryFolderItemView;
import com.example.androidchoi.jobdam.Model.CategoryData;

/**
 * Created by Choi on 2015-11-29.
 */
public class CategoryFolderAdapter extends BaseAdapter {

    Context mContext;
    CategoryData mCategoryData;

    public CategoryFolderAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return CategoryData.get(mContext).getCategoryList().size();
    }

    @Override
    public Object getItem(int position) {
        return CategoryData.get(mContext).getCategoryList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryFolderItemView mCategoryFolderItemView = (CategoryFolderItemView)convertView;
        if (mCategoryFolderItemView == null) {
            mCategoryFolderItemView = new CategoryFolderItemView(mContext);
        }
        mCategoryFolderItemView.setData(CategoryData.get(mContext).getCategoryList().get(position), 1);
        return mCategoryFolderItemView;
    }
}
