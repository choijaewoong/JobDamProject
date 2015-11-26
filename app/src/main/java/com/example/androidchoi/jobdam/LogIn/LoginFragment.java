package com.example.androidchoi.jobdam.LogIn;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.MainActivity;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Manager.PropertyManager;
import com.example.androidchoi.jobdam.Model.NetworkMessage;
import com.example.androidchoi.jobdam.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    public static final String MESSAGE_LOGIN_SUCCESS = "Login success";

    TextView mTextSignUp;
    EditText mEditEmail;
    EditText mEditPassWord;
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
                final String email = mEditEmail.getText().toString();
                final String password = mEditPassWord.getText().toString();
                NetworkManager.getInstance().login(getActivity(), email, password,
                        new NetworkManager.OnResultListener<NetworkMessage>() {
                    @Override
                    public void onSuccess(NetworkMessage result) {
                        if (result.getMessage().equals(MESSAGE_LOGIN_SUCCESS)){
                            PropertyManager.getInstance().setId(email);
                            PropertyManager.getInstance().setPassword(password);
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            mEditEmail.setText("");
                            mEditPassWord.setText("");
                            Toast.makeText(getActivity(), "사용자 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFail(int code) {

                    }
                });
            }
        });

       mTextSignUp = (TextView)view.findViewById(R.id.text_sign_up);
        mTextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_container, new ServiceAgreementFragment()).addToBackStack(null).commit();
            }
        });
        mEditEmail = (EditText)view.findViewById(R.id.editText_login_email);
        mEditPassWord = (EditText)view.findViewById(R.id.editText_login_password);
        return view;
    }


}
