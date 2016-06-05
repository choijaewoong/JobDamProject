package com.example.androidchoi.jobdam.Adpater;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

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
        notifyDataSetChanged();
    }

    public void addItems(List<QuestionData> questions){
        for(QuestionData questionData : questions) {
            mItems.add(questionData);
        }
        notifyDataSetChanged();
    }

    public void addItem(QuestionData question){
        mItems.add(question);
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        View view;
        if (scrappedView.size() > 0){
            view = scrappedView.remove(0);
        }else {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_page_job_question, container, false);
        }
        final EditText editTextQuestion = (EditText)view.findViewById(R.id.editText_job_question);
        FrameLayout touchInterceptor = (FrameLayout)view.findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (editTextQuestion.isFocused()) {
                        Rect outRect = new Rect();
                        editTextQuestion.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            editTextQuestion.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });

        TaggedCardItemAdapter adapter = new TaggedCardItemAdapter();
        adapter.setItems(mItems.get(position).getCardList());
        ListView listView = (ListView)view.findViewById(R.id.listView_memo_tag);
        listView.setAdapter(adapter);
        editTextQuestion.setText(mItems.get(position).getQuestion());
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
