package com.example.androidchoi.jobdam;

import android.content.Context;
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
import com.example.androidchoi.jobdam.Model.EmotionData;

/**
 * Created by Tacademy on 2015-10-29.
 */
public class BoardItemView extends RelativeLayout{

    TextView mTextContent;
    LinearLayout mLayoutLikeButton;
    ImageView mImageLike;
    TextView mTextLikeCount;
    TextView mTextEmotionDescription;
    ImageView mImageEmotionIcon;

    Articles article;

    public BoardItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_board_item, this);
        mTextContent = (TextView)findViewById(R.id.text_board_content);
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
                            }
                        });
            }
        });
        mImageLike = (ImageView)findViewById(R.id.image_board_like);
        mTextLikeCount = (TextView)findViewById(R.id.text_board_like_count);
        mTextEmotionDescription = (TextView)findViewById(R.id.text_emotion_description);
        mImageEmotionIcon = (ImageView)findViewById(R.id.image_emotion_icon);
    }

    public void setItemData(Articles data){
        article = data;
        mTextContent.setText(data.getArticle().getContent());
        mTextLikeCount.setText(data.getArticle().getLikeCount() + "");
        mImageLike.setSelected(data.getArticle().getLikeBool());
        mImageEmotionIcon.setImageResource(EmotionData.get(MyApplication.getContext()).getCategoryList().get(data.getArticle().getEmotionIndex()).getImageResource());
        mTextEmotionDescription.setText(EmotionData.get(MyApplication.getContext()).getCategoryList().get(data.getArticle().getEmotionIndex()).getDescription());
    }

    public void showLikeToast(){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_toast_like, null);
        Toast toast = new Toast(getContext().getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
//        LayoutInflater inflater = getContext().getLayoutInflater(savedInstanceState);
//        View view = inflater.inflate(R.layout.view_toast_like, );
    }

}