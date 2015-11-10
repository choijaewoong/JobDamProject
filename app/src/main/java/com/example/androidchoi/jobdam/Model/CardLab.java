package com.example.androidchoi.jobdam.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Choi on 2015-11-10.
 */
public class CardLab {
    private static CardLab sCardLab;
    private Context mContext;
    private ArrayList<CardData> mCardList;

    private CardLab(Context context){
        mContext = context;
        mCardList = new ArrayList<CardData>();

        //서버에서 데이터 추가할 부분
        for (int i = 0; i < 10; i++) {
            // 네트워크 매니저를 통해 데이터를 생성해서 가져옴.
            CardData data = new CardData();
            addCardData(data);
        }
    }
    public static CardLab get(Context context){
        if(sCardLab == null){
            sCardLab = new CardLab(context.getApplicationContext());
        }
        return sCardLab;
    }

    public ArrayList<CardData> getCardList(){
        return mCardList;
    }

    public CardData getCard(UUID id){
        for(CardData c : mCardList){
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }
    public void addCardData(CardData card){
        mCardList.add(0,card);
    }
}
