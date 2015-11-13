package com.example.androidchoi.jobdam.LogIn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputUserInfoFragment extends Fragment{


    public InputUserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_user_info, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_input_user_info);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button btn = (Button) view.findViewById(R.id.btn_input_complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance().signup("aaa", "1111");

//                        , new NetworkManager.OnResultListener<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        if (result.equals("ok")) {
//                            PropertyManager.getInstance().setId("aaa");
//                            PropertyManager.getInstance().setPassword("1111");
//                            startActivity(new Intent(getContext(), MainActivity.class));
//                            getActivity().finish();
//                        } else {
//                            //
//                        }
//                    }
//
//                    @Override
//                    public void onFail(int code) {
//                        // ...
//                    }
//                });
            }
        });
        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().getSupportFragmentManager().popBackStack();
//            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
