package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Articles;
import com.example.androidchoi.jobdam.Model.CurrentTime;
import com.example.androidchoi.jobdam.Model.EmotionData;
import com.example.androidchoi.jobdam.R;
import com.example.androidchoi.jobdam.Util.ExpandableTextView;

import java.util.Calendar;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class MyBoardItemView extends RelativeLayout{

    ExpandableTextView mTextContent;
    LinearLayout mLayoutLikeButton;
    ImageView mImageLike;
    TextView mTextLikeCount;
    TextView mTextEmotionDescription;
    TextView mTextWriteDate;
    ImageView mImageEmotionIcon;
    Articles article;
    View mItemBottomShadow;

    public MyBoardItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_my_board_item, this);
        mTextContent = (ExpandableTextView)findViewById(R.id.text_board_content);
        mLayoutLikeButton = (LinearLayout)findViewById(R.id.layout_board_like_button);
        mLayoutLikeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance().likeArticle(getContext(),
                        article.getId(), new NetworkManager.OnResultListener<Articles>() {
                            @Override
                            public void onSuccess(Articles result) {
                                if(result.getArticle().getLikeBool()){
                                    showLikeToast();
                                    mImageLike.setSelected(true);
                                }
                                else {
                                    mImageLike.setSelected(false);
                                }
                                article.setArticle(result);
                                mTextLikeCount.setText(article.getArticle().getLikeCount() + "");
                            }

                            @Override
                            public void onFail(int code) {
                                Log.i("error : ", code + "");
                                Toast.makeText(MyApplication.getContext(), "요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        mImageLike = (ImageView)findViewById(R.id.image_board_like);
        mTextLikeCount = (TextView)findViewById(R.id.text_board_like_count);
        mTextEmotionDescription = (TextView)findViewById(R.id.text_emotion_description);
        mTextWriteDate = (TextView)findViewById(R.id.text_board_write_date);
        mImageEmotionIcon = (ImageView)findViewById(R.id.image_emotion_icon);
        mItemBottomShadow = findViewById(R.id.view_my_board_item_shadow);
    }

    public void setItemData(Articles data){
        article = data;
        mTextContent.setCollapsed();
        mTextContent.setText(data.getArticle().getContent());
        mTextLikeCount.setText(data.getArticle().getLikeCount() + "");
        mImageLike.setSelected(data.getArticle().getLikeBool());
        mImageEmotionIcon.setImageResource(EmotionData.get(MyApplication.getContext()).getCategoryList().get(data.getArticle().getEmotionIndex()).getImageResource());
        mTextWriteDate.setText(getTimeAgo(data.getArticle().getWriteTimeStamp()));
        mTextEmotionDescription.setText(EmotionData.get(MyApplication.getContext()).getCategoryList().get(data.getArticle().getEmotionIndex()).getDescription());
        mItemBottomShadow.setVisibility(GONE);
    }

    public void setShadow(){
        mItemBottomShadow.setVisibility(VISIBLE);
    }

    public void showLikeToast(){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_toast_like, null);
        Toast toast = new Toast(getContext().getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    public static String getTimeAgo(long time) {
        long now = Calendar.getInstance().getTimeInMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "방금 전";
        }
        else if (diff < HOUR_MILLIS) {
            return diff / MINUTE_MILLIS + "분 전";
        }
        else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + "시간 전";
        }
        CurrentTime writeDate = new CurrentTime();
        writeDate.seTimeStamp(time);
        if(writeDate.getYear() == Calendar.getInstance().get(Calendar.YEAR)){
            return writeDate.getMonth() + "월 " + writeDate.getDayOfMonth() + "일 "
                    + writeDate.getAmPm() + " " + writeDate.getHourOfDay() + ":" + String.format("%02d", writeDate.getMinute());
        }else {

            return writeDate.getYear() + "년 " + writeDate.getMonth() + "월 " + writeDate.getDayOfMonth() + "일 "
                    + writeDate.getAmPm() + " " + writeDate.getHourOfDay() + ":" + String.format("%02d", writeDate.getMinute());
        }
    }
}