package com.example.androidchoi.jobdam.Model;

/**
 * Created by Choi on 2015-11-04.
 */
public class AddressData implements ChildData {
    private String mURLAddress;

    public AddressData(String URLAddress) {
        mURLAddress = URLAddress;
    }

    public String getURLAddress() {
        return mURLAddress;
    }
}
