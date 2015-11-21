package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Article;
import com.example.androidchoi.jobdam.Model.User;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {

    public static final String ARGS_ARTICLE = "article";

    public static ArticleFragment newInstance(Article article){
        ArticleFragment f = new ArticleFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARGS_ARTICLE, article);
        f.setArguments(b);
        return f;
    }
    public ArticleFragment() {
        // Required empty public constructor
    }
    Article mArticle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if(arg != null) {
            mArticle = (Article) arg.getSerializable(ARGS_ARTICLE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_article, container, false);
        TextView content = (TextView)view.findViewById(R.id.text_article);
        final TextView likeCount = (TextView)view.findViewById(R.id.text_like_cnt);
        content.setMovementMethod(new ScrollingMovementMethod());
        content.setText(mArticle.getContent());
        likeCount.setText(mArticle.getLikeCount() + "");
        likeCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "좋아요 클릭", Toast.LENGTH_SHORT).show();
                NetworkManager.getInstance().likeArticle(getActivity(),
                        User.USER_NAME, mArticle.getId(), new NetworkManager.OnResultListener<Article>() {
                            @Override
                            public void onSuccess(Article result) {
                                Log.i("ddd", new Gson().toJson(result));
                                mArticle.setArticle(result);
                                likeCount.setText(mArticle.getLikeCount() + "");
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
