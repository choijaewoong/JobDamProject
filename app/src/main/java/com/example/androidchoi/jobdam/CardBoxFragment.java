package com.example.androidchoi.jobdam;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.CardItemAdapter;
import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.Model.MyCard;
import com.example.androidchoi.jobdam.Model.MyCardLab;
import com.example.androidchoi.jobdam.Model.MyCards;
import com.example.androidchoi.jobdam.Model.User;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardBoxFragment extends Fragment {

    private static final int REQUEST_MODIFY = 1;
    private static final int REQUEST_NEW = 2;

    ListView mListView;
    CardItemAdapter mAdapter;
    FloatingActionMenu fam;
    EditText mSearchEdit;
    ImageView mDeleteImage;
    TextView mCountTextView;
    PredicateLayout mPredicateLayout;
    ScrollView mScrollView;
    ImageView mImageTagCloseButton;
    ArrayList<TextView> mTextTags = new ArrayList<TextView>();
    private ArrayList<MyCards> mCardList = new ArrayList<MyCards>();

    public CardBoxFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){ return; }
        showMyMemo();
        if(requestCode == REQUEST_MODIFY){
        }else if(requestCode == REQUEST_NEW){
            mListView.smoothScrollToPositionFromTop(0, 0, 500);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView) getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.card_box);
        showMyMemo();
        FrameLayout touchInterceptor = (FrameLayout)getActivity().findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mSearchEdit.isFocused() || mPredicateLayout.isFocused()) {
                        Rect outRect = new Rect();
                        mSearchEdit.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mSearchEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View shadowToolbar = getActivity().findViewById(R.id.toolbar_shadow);
        shadowToolbar.setVisibility(View.VISIBLE);
        final View view = inflater.inflate(R.layout.fragment_card_box, container, false);
        View searchHeaderView = inflater.inflate(R.layout.view_item_search_header, null);
        View countHeaderView = inflater.inflate(R.layout.view_item_count_header, null);
        mListView = (ListView) view.findViewById(R.id.listview_card);
        mListView.addHeaderView(searchHeaderView);
        mListView.addHeaderView(countHeaderView, null, false);
        mDeleteImage = (ImageView)searchHeaderView.findViewById(R.id.image_search_delete);
        mDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEdit.setText("");
            }
        });
        mSearchEdit = (EditText)searchHeaderView.findViewById(R.id.editText_search_bar);
        mSearchEdit.setHint("태그를 검색해주세요");
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if (!string.equals("")) {
                    mDeleteImage.setVisibility(View.VISIBLE);
                } else {
                    mDeleteImage.setVisibility(View.INVISIBLE);
                }
            }
        });
        mSearchEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mScrollView.setVisibility(View.VISIBLE);
                    int total = 0;
                    for (int i = 0; i < mCardList.size(); i++) {
                        int categoryIndex = mCardList.get(i).getCard().getCategory();
                        for (String tag : mCardList.get(i).getCard().getTags()) {
                            addTagView(tag, categoryIndex, i, total);
                            total++;
                        }
                    }
                    Log.i("count", total + "");
                } else {
//                    scrollView.setVisibility(View.GONE);
                }
            }
        });
        mAdapter = new CardItemAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                MyCard data = (MyCard) mAdapter.getItem(position - mListView.getHeaderViewsCount());
                MyCards myCards = (MyCards) mAdapter.getItem(position - mListView.getHeaderViewsCount());
                Intent intent = new Intent(getActivity(), CardWriteActivity.class);
                intent.putExtra(MyCard.CARD_ITEM, myCards);
                intent.putExtra(MyCard.CARD_NEW, false);
//                intent.putExtra(CardData.CARDPOSITION, position - mListView.getHeaderViewsCount());
                startActivityForResult(intent, REQUEST_MODIFY);
            }
        });
        fam = (FloatingActionMenu) view.findViewById(R.id.menu);
        FloatingActionButton addCardButton = (FloatingActionButton) view.findViewById(R.id.fab_write_card);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CardWriteActivity.class);
                intent.putExtra(MyCard.CARD_NEW, true);
                startActivityForResult(intent, REQUEST_NEW);
                fam.close(true);
            }
        });
        FloatingActionButton addCategoryButton = (FloatingActionButton) view.findViewById(R.id.fab_add_category);
        mCountTextView = (TextView) view.findViewById(R.id.text_item_count);
        mPredicateLayout = (PredicateLayout)view.findViewById(R.id.predicateLayout_all_tag_box);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView_tag);
        mImageTagCloseButton = (ImageView)view.findViewById(R.id.image_tag_box_close_button);
        mImageTagCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.setVisibility(View.GONE);
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    public void addTagView(String tag, int categoryIndex, final int index, int tagID){
        final TextView t = new TextView(getActivity());
        t.setId(tagID);
        t.setText(tag);
        t.setTextSize(14);
        t.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        t.setBackgroundResource(CategoryData.get(getActivity()).getCategoryList().get(categoryIndex).getImage());
        t.setPadding(20, 10, 20, 10);
        int width = getResources().getDimensionPixelSize(R.dimen.tag_max_width);
        t.setMaxWidth(width);
        t.setSingleLine(true);
        t.setEllipsize(TextUtils.TruncateAt.END);
        t.setGravity(Gravity.CENTER);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<mTextTags.size(); i++){
                    if(t == mTextTags.get(i)){
//                        Toast.makeText(CardWriteActivity.this, "해당 태그가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), CardWriteActivity.class);
                        intent.putExtra(MyCard.CARD_ITEM, mCardList.get(index));
                        intent.putExtra(MyCard.CARD_NEW, false);
                        startActivityForResult(intent, REQUEST_MODIFY);
                    }
                }
            }
        });
        for(int i=0; i<mTextTags.size(); i++) {
            if (t.getId() == mTextTags.get(i).getId()) {
                return;
            }
        }
        mTextTags.add(t);
        mPredicateLayout.addView(mTextTags.get(mTextTags.size() - 1));
    }

    public void showMyMemo() {
        NetworkManager.getInstance().showMyMemo(getActivity(),
                User.getInstance().getUserId(), new NetworkManager.OnResultListener<MyCardLab>() {
                    @Override
                    public void onSuccess(MyCardLab result) {
                        mCardList = result.getCardList();
                        mAdapter.setItems(mCardList);
                        mCountTextView.setText("총 " + mAdapter.getCount() + "건");
                    }
                    @Override
                    public void onFail(int code) {
                        Toast.makeText(MyApplication.getContext(), code + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
