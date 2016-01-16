package com.example.androidchoi.jobdam.Model;

import android.content.Context;

import com.example.androidchoi.jobdam.R;

import java.util.ArrayList;

/**
 * Created by Tacademy on 2015-10-30.
 */
public class EmotionData {
    private static EmotionData sEmotionData;
    private Context mContext;

    private ArrayList<EmotionData> mEmotionList;

    public ArrayList<EmotionData> getCategoryList() {
        return mEmotionList;
    }

    private EmotionData(Context context) {
        mContext = context;
        mEmotionList = new ArrayList<EmotionData>();
        mEmotionList.add(new EmotionData(R.drawable.emotion_smile, "좋아요"));
        mEmotionList.add(new EmotionData(R.drawable.emotion_happy, "행복해요"));
        mEmotionList.add(new EmotionData(R.drawable.emotion_suprized, "놀라워요"));
        mEmotionList.add(new EmotionData(R.drawable.emotion_complicated, "복잡미묘"));
        mEmotionList.add(new EmotionData(R.drawable.emotion_puzzled, "어리둥절"));
        mEmotionList.add(new EmotionData(R.drawable.emotion_depressed, "우울해요"));
        mEmotionList.add(new EmotionData(R.drawable.emotion_sad, "속상해요"));
    }

    private EmotionData(int imageResource, String description) {
        this.imageResource = imageResource;
        this.description = description;
    }

    public static EmotionData get(Context context) {
        if (sEmotionData == null) {
            sEmotionData = new EmotionData(context.getApplicationContext());
        }
        return sEmotionData;
    }
    private int imageResource;
    private String description;
    public int getImageResource() {
        return imageResource;
    }
    public String getDescription() { return description;}
}