package com.example.androidchoi.jobdam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Adpater.CardItemAdapter;
import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.MyCardLab;
import com.example.androidchoi.jobdam.Model.MyCards;

import java.util.ArrayList;

public class CardChoiceActivity extends AppCompatActivity {

    public static final String QUESTION_NUM = "questionNumber";
    public static final String CARD_TITLE = "cardTitle";

    ListView mListView;
    CardItemAdapter mAdapter;
    private ArrayList<MyCards> mCardList = new ArrayList<MyCards>();
    int mQuestionNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        Intent intent = getIntent();
        mQuestionNum = intent.getIntExtra(QUESTION_NUM, 0);
//        Toast.makeText(CardChoiceActivity.this, mQuestionNum + "", Toast.LENGTH_SHORT).show();

        mListView = (ListView)findViewById(R.id.listView_attach_card);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mAdapter = new CardItemAdapter();
        showMyMemo();
        mListView.setAdapter(mAdapter);
//        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }

    public void showMyMemo() {
        NetworkManager.getInstance().showMyMemo(CardChoiceActivity.this,
                new NetworkManager.OnResultListener<MyCardLab>() {
                    @Override
                    public void onSuccess(MyCardLab result) {
                        mCardList = result.getCardList();
                        mAdapter.setItems(mCardList);
                    }
                    @Override
                    public void onFail(int code) {
                        Toast.makeText(MyApplication.getContext(), code + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_attach, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_attach) {
//            List<MyCard> myCards = (List<MyCard>)mListView.getSelectedItem();
            SparseBooleanArray isCheckedArray = mListView.getCheckedItemPositions();
//            Toast.makeText(CardChoiceActivity.this, isCheckedArray.size() + " ", Toast.LENGTH_SHORT).show();
            ArrayList<MyCards> myCardList = new ArrayList<MyCards>();
//            MyCards[] myCardList = new MyCards[checkedPosition.length];
            for(int i = 0; i < isCheckedArray.size(); i++){
//                myCardList[i] = (MyCards) mAdapter.getItem((int) checkedPosition[i]);
                if(isCheckedArray.get(i) == true)
                    myCardList.add((MyCards) mAdapter.getItem(i));
            }
//            Toast.makeText(CardChoiceActivity.this, myCardList.size() + " ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra(QUESTION_NUM, mQuestionNum);
            intent.putExtra(CARD_TITLE, myCardList);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
