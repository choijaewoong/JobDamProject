package com.example.androidchoi.jobdam;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.CardItemAdapter;
import com.example.androidchoi.jobdam.Model.CardData;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardBoxFragment extends Fragment {

    private static final int REQUEST_MODIFY = 1;
    private static final int REQUEST_NEW = 2;

    ListView mListView;
    ImageView mImageView;
    CardItemAdapter mAdapter;
    FloatingActionMenu fam;

    public CardBoxFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;

        if(requestCode == REQUEST_MODIFY){
            int position = data.getIntExtra(CardWriteActivity.EXTRA_CARD_POSITION, 0);
            CardData cardData = (CardData) data.getSerializableExtra(CardWriteActivity.EXTRA_CARD_DATA);
            mAdapter.update(cardData, position);
        }else if(requestCode == REQUEST_NEW){
            CardData cardData = (CardData) data.getSerializableExtra(CardWriteActivity.EXTRA_CARD_DATA);
            mAdapter.add(cardData, 0);
            mListView.smoothScrollToPositionFromTop(0,0,500);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView) getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.card_box);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_box, container, false);
        View headerView = inflater.inflate(R.layout.view_card_item_count_header, null);

        mListView = (ListView) view.findViewById(R.id.listview_card);
        mListView.addHeaderView(headerView);
        mAdapter = new CardItemAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardData data = (CardData)mAdapter.getItem(position-1);
                Intent intent = new Intent(getActivity(), CardWriteActivity.class);
                intent.putExtra(CardData.CARDITEM, data);
                intent.putExtra(CardData.CARDPOSITION, position-1);
                startActivityForResult(intent, REQUEST_MODIFY);
            }
        });

        initData();
        TextView textView = (TextView) view.findViewById(R.id.text_card_item_count);
        textView.setText("총 " + mAdapter.getCount() + "건");

        fam = (FloatingActionMenu) view.findViewById(R.id.menu);
        FloatingActionButton addCardButton = (FloatingActionButton) view.findViewById(R.id.fab_write_card);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CardWriteActivity.class);
                startActivityForResult(intent, REQUEST_NEW);
                fam.close(true);
            }
        });
        FloatingActionButton addCategoryButton = (FloatingActionButton) view.findViewById(R.id.fab_add_category);
        mImageView = (ImageView) view.findViewById(R.id.image_background_blur);
        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {

                if (opened) {
                    mImageView.setVisibility(View.VISIBLE);
                    mListView.setEnabled(false);
                } else {
                    mImageView.setVisibility(View.GONE);
                    mListView.setEnabled(true);
                }
            }
        });
        return view;
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            // 네트워크 매니저를 통해 데이터를 생성해서 가져옴.
            CardData data = new CardData();
            mAdapter.add(data);
        }
    }
}
