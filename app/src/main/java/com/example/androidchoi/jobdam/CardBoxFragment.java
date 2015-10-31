package com.example.androidchoi.jobdam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Adpater.CardItemAdapter;
import com.example.androidchoi.jobdam.Model.CardItemData;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardBoxFragment extends Fragment {

    ListView mListView;
    CardItemAdapter mAdapter;

    public CardBoxFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView subTitle = (TextView)getActivity().findViewById(R.id.text_subtitle);
        subTitle.setText(R.string.card_box);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_box, container, false);
        View headerView = inflater.inflate(R.layout.view_card_item_header, null);

        mListView = (ListView)view.findViewById(R.id.listview_card);
        mListView.addHeaderView(headerView);
        mAdapter = new CardItemAdapter();
        mListView.setAdapter(mAdapter);

        initData();
        TextView textView = (TextView)view.findViewById(R.id.text_card_item_count);
        textView.setText("총 " + mAdapter.getCount() + "건");

        FloatingActionMenu fam = (FloatingActionMenu)view.findViewById(R.id.menu);
        FloatingActionButton addCardButton = (FloatingActionButton)view.findViewById(R.id.fab_write_card);
        FloatingActionButton addCategoryButton = (FloatingActionButton)view.findViewById(R.id.fab_add_category);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CardWriteActivity.class);
                startActivity(intent);
            }
        });



//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });
        return view;
    }

    private void initData() {
        for(int i=0; i<10; i++){

            // 네트워크 매니저를 통해 데이터를 생성해서 가져옴.

            CardItemData data = new CardItemData();
            mAdapter.add(data);
        }
    }
}
