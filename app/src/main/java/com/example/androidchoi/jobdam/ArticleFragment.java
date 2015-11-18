package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.Article;


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
        TextView tv = (TextView)view.findViewById(R.id.text_article);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(mArticle.getContent());
        return view;
    }

}
