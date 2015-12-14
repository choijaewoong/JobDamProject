package com.example.androidchoi.jobdam;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.CardItemAdapter;
import com.example.androidchoi.jobdam.Calendar.CategoryFolderAdapter;
import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.Model.MyCard;
import com.example.androidchoi.jobdam.Model.MyCardLab;
import com.example.androidchoi.jobdam.Model.MyCards;
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
    private static final int REQUEST_MODIFY = 1;
    private static final int REQUEST_NEW = 2;

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
    View gridSearchHeaderView;
    View itemCountHeaderView;
    View categoryCountHeaderView;
    ArrayList<Integer> checkedItems = new ArrayList<Integer>();

    MainActivity.OnCardBoxCallBack callback = new MainActivity.OnCardBoxCallBack() {
        @Override
        public boolean onCheckMode() {
            Log.i(mListView.getChoiceMode() + ".", ListView.CHOICE_MODE_MULTIPLE + ".");
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
        FrameLayout touchInterceptor = (FrameLayout) getActivity().findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mListSearchEdit.isFocused()) {
                        Rect outRect = new Rect();
                        mListSearchEdit.getGlobalVisibleRect(outRect);
                        mGridSearchEdit.getGlobalVisibleRect(outRect);
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
        gridSearchHeaderView = inflater.inflate(R.layout.view_header_item_search, null);
        itemCountHeaderView = inflater.inflate(R.layout.view_header_card_item_count, null);
        categoryCountHeaderView = inflater.inflate(R.layout.view_header_category_item_count, null);

        //리스트 뷰
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
                deleteMode();
                return true;
            }
        });
        // 그리드 뷰
        mGridView = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gridview_category_folder);
        mGridView.setVisibility(View.GONE);
        mCategoryFolderAdapter = new CategoryFolderAdapter(getActivity());
        mGridView.addHeaderView(gridSearchHeaderView);
        mGridView.addHeaderView(categoryCountHeaderView, null, false);
        mGridView.setAdapter(mCategoryFolderAdapter);

        //리스트 헤더뷰
        mItemCountTextView = (TextView) itemCountHeaderView.findViewById(R.id.text_item_count);
        mImageChangeGridView = (ImageView) itemCountHeaderView.findViewById(R.id.image_change_grid_view);
        mImageChangeGridView.setImageResource(R.drawable.button_card_list);
        mImageChangeGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListView.setVisibility(View.GONE);
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
                mListView.setVisibility(View.VISIBLE);
                mGridView.setVisibility(View.GONE);
            }
        });
        mCategoryCountTextView.setText(Html.fromHtml("폴더  <font color=#0db5f7>" + mCategoryFolderAdapter.getCount()));

        mListSearchEdit = (EditText) listSearchHeaderView.findViewById(R.id.editText_search_bar);
        mListSearchDeleteImage = (ImageView) listSearchHeaderView.findViewById(R.id.image_search_delete);
        setEditListener(mListSearchEdit, mListSearchDeleteImage);
        mGridSearchEdit = (EditText) gridSearchHeaderView.findViewById(R.id.editText_search_bar);
        mGridSearchDeleteImage = (ImageView) gridSearchHeaderView.findViewById(R.id.image_search_delete);
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

    public void addTagView(String tag, int categoryIndex, final int index, int tagID) {
        final TextView t = new TextView(getActivity());
        t.setId(tagID);
        t.setText(tag);
        t.setTextSize(14);
        t.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.image_category_color);
        drawable.setColorFilter(CategoryData.get(getActivity()).getCategoryList().get(categoryIndex).getColor(), PorterDuff.Mode.MULTIPLY);
        t.setBackground(drawable);
        t.setPadding(20, 10, 20, 10);
        int width = getResources().getDimensionPixelSize(R.dimen.tag_max_width);
        t.setMaxWidth(width);
        t.setSingleLine(true);
        t.setEllipsize(TextUtils.TruncateAt.END);
        t.setGravity(Gravity.CENTER);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mTextTags.size(); i++) {
                    if (t == mTextTags.get(i)) {
//                        Toast.makeText(CardWriteActivity.this, "해당 태그가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), CardWriteActivity.class);
                        intent.putExtra(MyCard.CARD_ITEM, mCardList.get(index));
                        intent.putExtra(MyCard.CARD_NEW, false);
                        startActivityForResult(intent, REQUEST_MODIFY);
                    }
                }
            }
        });
        for (int i = 0; i < mTextTags.size(); i++) {
            if (t.getId() == mTextTags.get(i).getId()) {
                return;
            }
        }
        mTextTags.add(t);
        mPredicateLayout.addView(mTextTags.get(mTextTags.size() - 1));
    }

    public void showMyMemo() {
        NetworkManager.getInstance().showMyMemo(getActivity(),
                new NetworkManager.OnResultListener<MyCardLab>() {
                    @Override
                    public void onSuccess(MyCardLab result) {
                        mCardList = result.getCardList();
                        mAdapter.setItems(mCardList);
                        mItemCountTextView.setText(Html.fromHtml("전체카드 <font color=#0db5f7>" + mAdapter.getCount()));
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
