package com.example.androidchoi.jobdam;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.CardItemAdapter;
import com.example.androidchoi.jobdam.Calendar.CategoryFolderAdapter;
import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.MyCard;
import com.example.androidchoi.jobdam.Model.MyCardLab;
import com.example.androidchoi.jobdam.Model.MyCards;
import com.example.androidchoi.jobdam.Model.Tags;
import com.example.androidchoi.jobdam.Util.PredicateLayout;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import in.srain.cube.views.GridViewWithHeaderAndFooter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardBoxFragment extends Fragment {

    public static final String CATEGORY_DIALOG = "category_dialog";
    public static final String EXTRA_CATEGORY_INDEX = "category_index";
    private static final int REQUEST_MODIFY = 1;
    private static final int REQUEST_NEW = 2;

    SwipeRefreshLayout mRefreshLayout;
    ListView mListView;
    GridViewWithHeaderAndFooter mGridView;
    CategoryFolderAdapter mCategoryFolderAdapter;
    CardItemAdapter mAdapter;
    FloatingActionMenu fam;
    EditText mListSearchEdit;
    EditText mGridSearchEdit;
    ImageView mListSearchDeleteImage;
    ImageView mGridSearchDeleteImage;
    TextView mItemCountTextView;
    TextView mCategoryCountTextView;
    ImageView mImageChangeGridView;
    ImageView mImageChangeListView;
    PredicateLayout mPredicateLayout;
    ScrollView mScrollView;
    ImageView mImageTagCloseButton;
    ArrayList<TextView> mTextTags = new ArrayList<TextView>();
    private ArrayList<MyCards> mCardList = new ArrayList<MyCards>();
    View listSearchHeaderView;
    View itemCountHeaderView;
    RelativeLayout gridSearchHeaderView;
    View categoryCountHeaderView;
    ArrayList<Integer> checkedItems = new ArrayList<Integer>();

    MainActivity.OnCardBoxCallBack callback = new MainActivity.OnCardBoxCallBack() {
        @Override
        public boolean onCheckMode() {
            if (mListView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                return true;
            }
            return false;
        }

        @Override
        public void onChangeMode() {
            defaultMode();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        super.setMenuVisibility(false);
        ((MainActivity) getActivity()).setOnCardBoxCallback(callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        showMyMemo();
        if (requestCode == REQUEST_MODIFY) {
        } else if (requestCode == REQUEST_NEW) {
            mListView.smoothScrollToPositionFromTop(0, 0, 500);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView) getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.card_box);
        showMyMemo();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScrollView.setVisibility(View.GONE);
                showMyMemo();
            }
        });
        FrameLayout touchInterceptor = (FrameLayout) getActivity().findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mListSearchEdit.isFocused()) {
                        Rect outRect = new Rect();
                        mListSearchEdit.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mListSearchEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    } else if (mGridSearchEdit.isFocused()) {
                        Rect outRect = new Rect();
                        mGridSearchEdit.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mGridSearchEdit.clearFocus();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_box, container, false);
        listSearchHeaderView = inflater.inflate(R.layout.view_header_item_search, null);
        itemCountHeaderView = inflater.inflate(R.layout.view_header_card_item_count, null);
        categoryCountHeaderView = inflater.inflate(R.layout.view_header_category_item_count, null);
        gridSearchHeaderView = (RelativeLayout)view.findViewById(R.id.layout_card_folder_search);

        //리스트 뷰
        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_card_box);
        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        mRefreshLayout.setColorSchemeResources(android.R.color.white);
        mListView = (ListView) view.findViewById(R.id.listview_card);
        mListView.addHeaderView(listSearchHeaderView);
        mListView.addHeaderView(itemCountHeaderView, null, false);
        mAdapter = new CardItemAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mListView.getCheckedItemCount() + " 개 선택");
                    for (int i = 0; i < checkedItems.size(); i++) {
                        if (checkedItems.get(i).equals(position)) {
                            checkedItems.remove(i);
                            return;
                        }
                    }
                    checkedItems.add(position);
                    return;
                }
                MyCards myCards = (MyCards) mAdapter.getItem(position - mListView.getHeaderViewsCount());
                Intent intent = new Intent(getActivity(), CardWriteActivity.class);
                intent.putExtra(MyCard.CARD_ITEM, myCards);
                intent.putExtra(MyCard.CARD_NEW, false);
                startActivityForResult(intent, REQUEST_MODIFY);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mScrollView.setVisibility(View.GONE);
                deleteMode();
                return true;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mScrollView.setVisibility(View.GONE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        // 그리드 뷰
        mGridView = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gridview_category_folder);
        mGridView.setVisibility(View.GONE);
        mCategoryFolderAdapter = new CategoryFolderAdapter(getActivity());
        mGridView.addHeaderView(categoryCountHeaderView, null, false);
        mGridView.setAdapter(mCategoryFolderAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FilteredCardActivity.class);
                intent.putExtra(EXTRA_CATEGORY_INDEX, position);
                startActivity(intent);
            }
        });

        //리스트 헤더뷰
        mItemCountTextView = (TextView) itemCountHeaderView.findViewById(R.id.text_item_count);
        mImageChangeGridView = (ImageView) itemCountHeaderView.findViewById(R.id.image_change_grid_view);
        mImageChangeGridView.setImageResource(R.drawable.button_card_list);
        mImageChangeGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListView.setVisibility(View.GONE);
                gridSearchHeaderView.setVisibility(View.VISIBLE);
                mGridView.setVisibility(View.VISIBLE);
            }
        });

        //그리드 헤더뷰
        mCategoryCountTextView = (TextView) categoryCountHeaderView.findViewById(R.id.text_category_count);
        mImageChangeListView = (ImageView) categoryCountHeaderView.findViewById(R.id.image_change_list_view);
        mImageChangeListView.setImageResource(R.drawable.button_card_folder);
        mImageChangeListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListView.setVisibility(View.VISIBLE);
                gridSearchHeaderView.setVisibility(View.GONE);
                mGridView.setVisibility(View.GONE);
            }
        });
        mCategoryCountTextView.setText(Html.fromHtml("폴더  <font color=#0db5f7>" + mCategoryFolderAdapter.getCount()));

        // 검색 뷰
        mListSearchEdit = (EditText) listSearchHeaderView.findViewById(R.id.editText_search_bar);
        mGridSearchEdit = (EditText)view.findViewById(R.id.editText_folder_search_bar);
        mListSearchDeleteImage = (ImageView) listSearchHeaderView.findViewById(R.id.image_search_delete);
        mGridSearchDeleteImage = (ImageView) view.findViewById(R.id.image_folder_search_delete);
        setEditListener(mListSearchEdit, mListSearchDeleteImage);
        setEditListener(mGridSearchEdit, mGridSearchDeleteImage);


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
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getString(R.string.preparation), Toast.LENGTH_SHORT).show();
            }
        });
        mPredicateLayout = (PredicateLayout) view.findViewById(R.id.predicateLayout_all_tag_box);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView_tag);
        mImageTagCloseButton = (ImageView) view.findViewById(R.id.image_tag_box_close_button);
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

    public void addTagView(String tag) {
        final TextView t = new TextView(getActivity());
//        t.setId(tagID);
        t.setText(tag);
        t.setTextSize(12);
        t.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.darker_gray));
        LayerDrawable drawable = (LayerDrawable)ContextCompat.getDrawable(getContext(), R.drawable.image_card_tag_border_default);
        t.setBackgroundDrawable(drawable);
        t.setPadding(12, 4, 12, 4);
        int width = getResources().getDimensionPixelSize(R.dimen.tag_max_width);
        t.setMaxWidth(width);
        t.setSingleLine(true);
        t.setEllipsize(TextUtils.TruncateAt.END);
        t.setGravity(Gravity.CENTER);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mPredicateLayout.addView(t);
    }

    public void showMyMemo() {
        NetworkManager.getInstance().showMyMemo(getActivity(),
                new NetworkManager.OnResultListener<MyCardLab>() {
                    @Override
                    public void onSuccess(MyCardLab result) {
                        mCardList = result.getCardList();
                        mAdapter.setItems(mCardList);
                        mItemCountTextView.setText(Html.fromHtml("전체카드 <font color=#0db5f7>" + mAdapter.getCount()));
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(int code) {
                        Toast.makeText(MyApplication.getContext(), code + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setEditListener(final EditText editText, final ImageView imageView) {
        editText.setHint("태그를 검색해주세요");
        editText.addTextChangedListener(new TextWatcher() {
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
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    NetworkManager.getInstance().showCardTag(getContext(), new NetworkManager.OnResultListener() {
                        @Override
                        public void onSuccess(Object result) {
                            Tags tagList = (Tags) result;
                            Log.i("asdfs",tagList.getTagCount()+"" );
                            if (tagList.getTagCount() == 0) {
                                return;
                            }
                            mPredicateLayout.removeAllViews();
                            for (String tag : tagList.getTag()) {
                                mScrollView.setVisibility(View.VISIBLE);
                                addTagView(tag);
                            }
                        }

                        @Override
                        public void onFail(int code) {

                        }
                    });
//
//                    int total = 0;
//                    for (int i = 0; i < mCardList.size(); i++) {
//                        int categoryIndex = mCardList.get(i).getCard().getCategory();
//                        for (String tag : mCardList.get(i).getCard().getTags()) {
//                            addTagView(tag, categoryIndex, i, total);
//                            total++;
//                        }
//                    }
                } else {

                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    public void deleteMode() {
        super.setMenuVisibility(true);
        mRefreshLayout.setEnabled(false);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.colorNavHeaderBackground));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mListView.getCheckedItemCount() + " 개 선택");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        mListView.removeHeaderView(listSearchHeaderView);
        mImageChangeGridView.setVisibility(View.GONE);
        fam.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
    }

    public void defaultMode() {
        super.setMenuVisibility(false);
        for (int i = 0; i < checkedItems.size(); i++) {
            mListView.setItemChecked(checkedItems.get(i), false);
        }
        checkedItems.clear();
        mRefreshLayout.setEnabled(true);
        mListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.colorPrimary));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        mListView.removeHeaderView(itemCountHeaderView);
        mListView.addHeaderView(listSearchHeaderView);
        mListView.addHeaderView(itemCountHeaderView, null, false);
        mImageChangeGridView.setVisibility(View.VISIBLE);
        fam.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();
    }

    public void moveCategory() {

        // 변경된 카드 리스트 서버에 전달
        defaultMode();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_card, menu);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            defaultMode();
            return true;
        }else if(id == R.id.action_move){
            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.show(getActivity().getSupportFragmentManager(), CATEGORY_DIALOG);
        }else if (id == R.id.action_delete) {

            // mAdapter.getCheckedItemIndexList() 보내 삭제 요청

            defaultMode();
            return true;
        }
        return true;
    }
}
