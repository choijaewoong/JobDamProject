package com.example.androidchoi.jobdam.Model;

import java.util.HashMap;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class CategoryData {

    public enum Category{ RED, BLUE, GREEN, GREY, PURPLE, PINK }

    HashMap<Category, String> mName = new HashMap<Category, String>();
//    HashMap<Integer, String> mColor = new HashMap<Integer, String>();

    public CategoryData() {
        init();
    }

    private void init() {
        mName.put(Category.RED, "#ffffff");
        mName.put(Category.BLUE, "#0074df");
        mName.put(Category.GREY, "#9a9a9a");
        mName.put(Category.GREEN, "#00c740");
        mName.put(Category.PURPLE, "#7714bd");
        mName.put(Category.PINK, "#f436bc");
    }
}
