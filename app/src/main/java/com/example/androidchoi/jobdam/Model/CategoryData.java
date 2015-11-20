package com.example.androidchoi.jobdam.Model;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class CategoryData {


    private String name;
    private int colorResource;
    public String getName() {  return name; }
    public int getColor () { return colorResource; }
    public CategoryData(String name, int color) {
        this.name = name;
        this.colorResource = color;
    }
    //    public enum Category{ RED,
//        BLUE, GREEN, GREY, PURPLE, PINK }
//
//    private static HashMap<Category, String> mName = new HashMap<Category, String>();
////    HashMap<Integer, String> mColor = new HashMap<Integer, String>();
//    public static HashMap<Category, String> getmName() {
//        return mName;
//
//
//    }
//
//    public CategoryData() {
//        init();
//    }
//
//    private void init() {
//        mName.put(Category.RED, "#ffffff");
//        mName.put(Category.BLUE, "#0074df");
//        mName.put(Category.GREY, "#9a9a9a");
//        mName.put(Category.GREEN, "#00c740");
//        mName.put(Category.PURPLE, "#7714bd");
//        mName.put(Category.PINK, "#f436bc");
//    }
}
