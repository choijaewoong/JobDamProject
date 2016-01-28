package com.example.androidchoi.jobdam.ItemView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.androidchoi.jobdam.Model.AddressData;
import com.example.androidchoi.jobdam.R;

/**
 * Created by Choi on 2015-11-04.
 */
public class ExpandableChildAddressItemView extends FrameLayout{
    public ExpandableChildAddressItemView(Context context) {
        super(context);
        init();
    }

    Button mButton;
    private void init() {
        inflate(getContext(), R.layout.view_expandable_child_address_item, this);
        mButton = (Button)findViewById(R.id.btn_move_address);
    }

    public void setExpandableAddress(final AddressData data){
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(data.getURLAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
                v.getContext().startActivity(intent);

            }
        });
    }
}
