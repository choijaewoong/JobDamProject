package com.example.androidchoi.jobdam.LogIn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidchoi.jobdam.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    TextView mTextView;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button btn = (Button)view.findViewById(R.id.btn_sign_in);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = "EMAIL";
                final String password = "PASSWORD";
//                NetworkManager.getInstance().login(email, password);
//                        new NetworkManager.OnResultListener<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        if (result.equals("ok")) {
//                            PropertyManager.getInstance().setId(email);
//                            PropertyManager.getInstance().setPassword(password);
//                            startActivity(new Intent(getContext(), MainActivity.class));
//                            getActivity().finish();
//                        } else {
//                            // ...
//                            Toast.makeText(getActivity(), "사용자 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFail(int code) {
//
//                    }
//                });
            }
        });

       mTextView = (TextView)view.findViewById(R.id.text_sign_up);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new ServiceAgreementFragment()).addToBackStack(null).commit();
            }
        });
        return view;
    }


}
