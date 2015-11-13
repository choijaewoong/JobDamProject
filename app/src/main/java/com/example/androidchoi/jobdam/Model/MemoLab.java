package com.example.androidchoi.jobdam.Model;

import com.begentgroup.xmlparser.SerializedName;

/**
 * Created by Choi on 2015-11-12.
 */
public class MemoLab {
    @SerializedName("Memoes")
    private CardLab cardLab;

    public CardLab getCardLab() {
        return cardLab;
    }
}
