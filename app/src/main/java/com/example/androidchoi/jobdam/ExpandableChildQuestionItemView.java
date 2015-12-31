package com.example.androidchoi.jobdam;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.Model.MyCards;
import com.example.androidchoi.jobdam.Model.QuestionData;
import com.example.androidchoi.jobdam.Model.Questions;
import com.example.androidchoi.jobdam.Util.PredicateLayout;

/**
 * Created by Choi on 2015-11-04.
 */
public class ExpandableChildQuestionItemView extends FrameLayout {

    public static final String QUESTION_NUM = "questionNumber";
    public static final String JOB_ID = "jobId";
    public static final String QUESTION_LIST = "questionList";
    public static final String CORP_NAME = "corpName";

    public ExpandableChildQuestionItemView(Context context) {
        super(context);
        init();
    }
    TextView mTextQuestionView;
    PredicateLayout mPredicateLayout;
    Button mButtonQuestionDetail;

    private void init() {
        View view = inflate(getContext(), R.layout.view_expandable_child_question_item,this);
        mTextQuestionView = (TextView)view.findViewById(R.id.text_job_question);
        mPredicateLayout = (PredicateLayout)view.findViewById(R.id.layout_job_question_tag);
        mButtonQuestionDetail = (Button)view.findViewById(R.id.button_job_question_detail);
    }

    public void setExpandableQuestion(final QuestionData data, final int jobId, final String corpName,  final int position){
        mTextQuestionView.setText(data.getQuestion());
        mButtonQuestionDetail.setVisibility(GONE); //상세보기 숨김
        mPredicateLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CardChoiceActivity.class);
                //질문 번호
                intent.putExtra(QUESTION_NUM, position);
                intent.putExtra(JOB_ID, jobId);
                ((JobDetailActivity) getContext()).startActivityForResult(intent, JobDetailActivity.REQUEST_ATTACH);
            }
        });
        if(data.getCardList() != null){
            for (MyCards myCards : data.getCardList()) {
                addTagView(myCards.getCard().getTitle(), myCards.getCard().getCategory());
            }
        }
        mButtonQuestionDetail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), JobQuestionActivity.class);
                Questions questions = ((JobDetailActivity) getContext()).getQuestions();
                intent.putExtra(QUESTION_LIST, questions);
                intent.putExtra(CORP_NAME, corpName);
                ((JobDetailActivity) getContext()).startActivityForResult(intent, JobDetailActivity.REQUEST_DETAIL);
            }
        });
    }

    public void setVisibleDetailButton(){
        mButtonQuestionDetail.setVisibility(VISIBLE);
    }

    public void addTagView(String tag, int categoryIndex){
        TextView t = new TextView(getContext());
        t.setText(tag);
        t.setTextSize(14);
        t.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.image_category_background);
        drawable.setColorFilter(CategoryData.get(getContext()).getCategoryList().get(categoryIndex).getColor(), PorterDuff.Mode.MULTIPLY);
        t.setBackground(drawable);
        t.setPadding(20, 10, 20, 10);
        int width = getResources().getDimensionPixelSize(R.dimen.tag_max_width);
        t.setMaxWidth(width);
        t.setSingleLine(true);
        t.setEllipsize(TextUtils.TruncateAt.END);
        t.setGravity(Gravity.CENTER);
//        t.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(int i=0; i<mTextTags.size(); i++){
//                    if(t == mTextTags.get(i)){
////                        Toast.makeText(CardWriteActivity.this, "해당 태그가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getActivity(), CardWriteActivity.class);
//                        intent.putExtra(MyCard.CARD_ITEM, mCardList.get(index));
//                        intent.putExtra(MyCard.CARD_NEW, false);
//                        startActivityForResult(intent, REQUEST_MODIFY);
//                    }
//                }
//            }
//        });
//        for(int i=0; i<mTextTags.size(); i++) {
//            if (t.getId() == mTextTags.get(i).getId()) {
//                return;
//            }
//        }
//        mTextTags.add(t);
//        mPredicateLayout.addView(mTextTags.get(mTextTags.size() - 1));
        mPredicateLayout.addView(t);
    }
}
