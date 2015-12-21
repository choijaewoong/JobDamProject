package com.example.androidchoi.jobdam;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.CardItemAdapter;
import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.CategoryData;
import com.example.androidchoi.jobdam.Model.MyCard;
import com.example.androidchoi.jobdam.Model.MyCardLab;
import com.example.androidchoi.jobdam.Model.MyCards;

public class FilteredCardActivity extends AppCompatActivity {

    ListView mListView;
    CardItemAdapter mAdapter;
    TextView mItemCountTextView;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_card);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        Intent intent = getIntent();
        int index = intent.getIntExtra(CardBoxFragment.EXTRA_CATEGORY_INDEX, 0);
        getSupportActionBar().setTitle(CategoryData.get(FilteredCardActivity.this).getCategoryList().get(index).getName());
        mToolbar.setBackgroundColor(CategoryData.get(FilteredCardActivity.this).getCategoryList().get(index).getColor());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(CategoryData.get(FilteredCardActivity.this).getCategoryList().get(index).getColor());
        }
//        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));

        View itemCountHeaderView = getLayoutInflater().inflate(R.layout.view_header_card_item_count, null);
        mItemCountTextView = (TextView) itemCountHeaderView.findViewById(R.id.text_item_count);
        mListView = (ListView)findViewById(R.id.listView_filtered_card);
        mListView.addHeaderView(itemCountHeaderView, null, false);
        mAdapter = new CardItemAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyCards myCards = (MyCards) mAdapter.getItem(position - mListView.getHeaderViewsCount());
                Intent intent = new Intent(FilteredCardActivity.this, CardWriteActivity.class);
                intent.putExtra(MyCard.CARD_ITEM, myCards);
                intent.putExtra(MyCard.CARD_NEW, false);
                startActivity(intent);
            }
        });

        showFilteredMemo(index);
    }

    public void showFilteredMemo(final int index){
        NetworkManager.getInstance().showFilteredMemo(FilteredCardActivity.this, index,
                new NetworkManager.OnResultListener<MyCardLab>() {
                    @Override
                    public void onSuccess(MyCardLab result) {
//                        mCardList = result.getCardList();
//                        mAdapter.setItems(mCardList);
                        mAdapter.setItems(result.getCardList());
                        mItemCountTextView.setText(Html.fromHtml("전체카드 <font color=#0db5f7>" + mAdapter.getCount()));
                    }

                    @Override
                    public void onFail(int code) {
                        Toast.makeText(MyApplication.getContext(), code + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
