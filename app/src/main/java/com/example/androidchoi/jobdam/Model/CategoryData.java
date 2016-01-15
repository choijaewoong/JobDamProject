package com.example.androidchoi.jobdam.Model;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.androidchoi.jobdam.R;

import java.util.ArrayList;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class CategoryData {
    private static CategoryData sCategoryData;
    private Context mContext;

    private ArrayList<CategoryData> mCategoryList;

    public ArrayList<CategoryData> getCategoryList() {
        return mCategoryList;
    }

    private CategoryData(Context context) {
        mContext = context;
        mCategoryList = new ArrayList<CategoryData>();
        mCategoryList.add(new CategoryData(mContext.getString(R.string.category_default),
                R.drawable.image_category_4,
                ContextCompat.getColor(context, R.color.colorCategoryDefault)));
        mCategoryList.add(new CategoryData(mContext.getString(R.string.category_scrap), R.drawable.image_category_5, ContextCompat.getColor(context, R.color.colorCategoryScrap)));
        mCategoryList.add(new CategoryData(mContext.getString(R.string.category_win), R.drawable.image_category_6, ContextCompat.getColor(context, R.color.colorCategoryWin)));
        mCategoryList.add(new CategoryData(mContext.getString(R.string.category_intern), R.drawable.image_category_2, ContextCompat.getColor(context, R.color.colorCategoryIntern)));
        mCategoryList.add(new CategoryData(mContext.getString(R.string.category_link), R.drawable.image_category_8, ContextCompat.getColor(context, R.color.colorCategoryLink)));
    }

    private CategoryData(String name, int imageResource, int color) {
        this.name = name;
        this.imageResource = imageResource;
        this.color = color;
    }

    public static CategoryData get(Context context) {
        if (sCategoryData == null) {
            sCategoryData = new CategoryData(context.getApplicationContext());
        }
        return sCategoryData;
    }

    private String name;
    private int color;
    private int imageResource;

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getImage() {
        return imageResource;
    }
}