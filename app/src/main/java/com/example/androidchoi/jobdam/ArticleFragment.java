package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Articles;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {

    public static final String ARGS_ARTICLE = "article";
    Articles mArticles;
    TextView mTextContent;
    TextView mTextLikeCount;
    ImageView mToggleLikeButton;
    LinearLayout mLinearLayout;

    public static ArticleFragment newInstance(Articles article){
        ArticleFragment f = new ArticleFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARGS_ARTICLE, article);
        f.setArguments(b);
        return f;
    }
    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if(arg != null) {
            mArticles = (Articles) arg.getSerializable(ARGS_ARTICLE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_article, container, false);
        mTextContent = (TextView)view.findViewById(R.id.text_article);
        mTextLikeCount = (TextView)view.findViewById(R.id.text_like_cnt);
        mToggleLikeButton = (ImageView)view.findViewById(R.id.image_like_button);
        mToggleLikeButton.setSelected(mArticles.getArticle().getLikeBool());
        mLinearLayout = (LinearLayout)view.findViewById(R.id.layout_article_like);
        mTextContent.setMovementMethod(new ScrollingMovementMethod());
        mTextContent.setText(mArticles.getArticle().getContent());
        mTextLikeCount.setText(mArticles.getArticle().getLikeCount() + "");
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "좋아요 클릭", Toast.LENGTH_SHORT).show();
                NetworkManager.getInstance().likeArticle(getActivity(),
                        mArticles.getId(), new NetworkManager.OnResultListener<Articles>() {
                            @Override
                            public void onSuccess(Articles result) {
                                if(result.getArticle().getLikeBool()){
                                    showLikeToast(savedInstanceState);
                                    mToggleLikeButton.setSelected(true);
                                }
                                else {
                                    mToggleLikeButton.setSelected(false);
                                }
                                mArticles.setArticle(result);
                                mTextLikeCount.setText(mArticles.getArticle().getLikeCount() + "");
                            }

                            @Override
                            public void onFail(int code) {
                            }
                        });
            }
        });
        return view;
    }

    public void showLikeToast(Bundle savedInstanceState){
        LayoutInflater inflater = getLayoutInflater(savedInstanceState);
        View view = inflater.inflate(R.layout.view_toast_like,
                (ViewGroup) getActivity().findViewById(R.id.container_like_toast));
        Toast toast = new Toast(getActivity().getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
