package com.example.androidchoi.jobdam.Model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-10.
 */
public class MyCardLab {
    private static MyCardLab sMyCardLab;
    private Context mContext;

    @SerializedName("items")
    private ArrayList<MyCards> mCardList;

    private MyCardLab(Context context){
        mContext = context;
        mCardList = new ArrayList<MyCards>();
//        //서버에서 데이터 추가할 부분
//        for (int i = 0; i < 100; i++) {
//            // 네트워크 매니저를 통해 데이터를 생성해서 가져옴.
//            MyCard data = new MyCard();
//            addCardData(data);
//        }
    }
   private MyCardLab(Context context, ArrayList<MyCards> cardList){
       mContext = context;
       mCardList = cardList;
   }

    public static MyCardLab get(Context context){
        if(sMyCardLab == null){
            sMyCardLab = new MyCardLab(context.getApplicationContext());
        }
        return sMyCardLab;
    }
    public static MyCardLab get(Context context, MyCardLab myCardLab){
        if(sMyCardLab == null){
            sMyCardLab = new MyCardLab(context.getApplicationContext(), myCardLab.getCardList());
        }
        return sMyCardLab;
    }

    public ArrayList<MyCards> getCardList(){
        return mCardList;
    }

    public MyCards getCard(String id){
        for(MyCards c : mCardList){
            if(c.getCard().getId().equals(id))
                return c;
        }
        return null;
    }
    public void modifyCardData(MyCard card){
        MyCards myCards = new MyCards();
        myCards.setCard(card);
        for(int i = 0; i<mCardList.size(); i++){
            if(mCardList.get(i).getCard().getId().equals(card.getId())){
                mCardList.set(i, myCards);
                return;
            }
        }
        mCardList.add(0, myCards);
    }
}
