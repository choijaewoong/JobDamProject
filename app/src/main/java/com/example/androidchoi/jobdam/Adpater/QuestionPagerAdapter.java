package com.example.androidchoi.jobdam.Adpater;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.QuestionData;
import com.example.androidchoi.jobdam.Model.Questions;
import com.example.androidchoi.jobdam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2015-12-08.
 */
public class QuestionPagerAdapter extends PagerAdapter{

    List<View> scrappedView = new ArrayList<View>();
    List<QuestionData> mItems = new ArrayList<QuestionData>();


    public void setItems(Questions questions){
        for(QuestionData questionData : questions.getQuestionList()){
            mItems.add(questionData);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        View view;
        if (scrappedView.size() > 0){
            view = scrappedView.remove(0);
        }else {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_job_question_item, container, false);
        }
        TextView tv = (TextView)view.findViewById(R.id.text_job_question);
        tv.setText(mItems.get(position).getQuestion());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        container.removeView(view);
        scrappedView.add(view);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
