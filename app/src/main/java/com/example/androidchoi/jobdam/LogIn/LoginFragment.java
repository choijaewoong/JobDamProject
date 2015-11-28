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

import com.example.androidchoi.jobdam.MainActivity;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Manager.PropertyManager;
import com.example.androidchoi.jobdam.Model.LoginData;
import com.example.androidchoi.jobdam.Model.User;
import com.example.androidchoi.jobdam.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    public static final String MESSAGE_SUCCESS = "Login Success";
    public static final String MESSAGE_NO_USER  = "사용자가 없습니다.";
    public static final String MESSAGE_DIFF_PW = "비밀번호가 다릅니다.";
    public static final String MESSAGE_MISSING = "Missing credentials";
    public static final String MESSAGE_DUPLICATION = "중복된 id 입니다.";

    TextView mTextSignUp;
    TextView mTextFailMessage;
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
        mTextFailMessage = (TextView)view.findViewById(R.id.text_login_fail_message);
        Button btn = (Button)view.findViewById(R.id.btn_sign_in);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEditEmail.getText().toString();
                final String password = mEditPassWord.getText().toString();
                NetworkManager.getInstance().login(getActivity(), email, password,
                        new NetworkManager.OnResultListener<LoginData>() {
                    @Override
                    public void onSuccess(LoginData result) {
                        if (result.getMessage().equals(MESSAGE_SUCCESS)){
                            PropertyManager.getInstance().setId(email);
                            PropertyManager.getInstance().setPassword(password);
                            User.getInstance().setUser(result.getUserId(), result.getName());
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            mTextFailMessage.setVisibility(View.VISIBLE);
                            if(result.getMessage().equals(MESSAGE_MISSING)){
                                mTextFailMessage.setText(getString(R.string.missing_credentials));
                            }
                            else if(result.getMessage().equals(MESSAGE_NO_USER)){
                                mTextFailMessage.setText(getString(R.string.no_user));
                                mEditEmail.setText("");
                            }else if(result.getMessage().equals(MESSAGE_DIFF_PW)){
                                mTextFailMessage.setText(getString(R.string.diff_pw));
                            }
                            mEditPassWord.setText("");
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
