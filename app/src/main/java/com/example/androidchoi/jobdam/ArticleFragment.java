package com.example.androidchoi.jobdam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {

    public static final String ARGS_TITLE = "title";

    public static ArticleFragment newInstance(String title){
        ArticleFragment f = new ArticleFragment();
        Bundle b = new Bundle();
        b.putString(ARGS_TITLE, title);
        f.setArguments(b);
        return f;
    }


    public ArticleFragment() {
        // Required empty public constructor
    }

    String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if(arg != null){
            mTitle = arg.getString(ARGS_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_article, container, false);
        TextView tv = (TextView)view.findViewById(R.id.text_article);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(mTitle);
        return view;
    }

}
