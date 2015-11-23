package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Article;
import com.example.androidchoi.jobdam.Model.Articles;
import com.example.androidchoi.jobdam.Model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {

    public static final String ARGS_ARTICLE = "article";

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
    Articles mArticles;
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
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_article, container, false);
        TextView textContent = (TextView)view.findViewById(R.id.text_article);
        final TextView textLikeCount = (TextView)view.findViewById(R.id.text_like_cnt);
        textContent.setMovementMethod(new ScrollingMovementMethod());
        textContent.setText(mArticles.getArticle().getContent());
        textLikeCount.setText(mArticles.getArticle().getLikeCount() + "");
        textLikeCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "좋아요 클릭", Toast.LENGTH_SHORT).show();
                NetworkManager.getInstance().likeArticle(getActivity(),
                        User.USER_NAME, mArticles.getId(), new NetworkManager.OnResultListener<Article>() {
                            @Override
                            public void onSuccess(Article result) {
                                mArticles.getArticle().setArticle(result);
                                textLikeCount.setText(mArticles.getArticle().getLikeCount() + "");
                            }

                            @Override
                            public void onFail(int code) {
                            }
                        });
            }
        });
        return view;
    }
}
