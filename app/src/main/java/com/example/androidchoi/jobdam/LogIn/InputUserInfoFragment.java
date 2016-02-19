package com.example.androidchoi.jobdam.LogIn;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.MainActivity;
import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Manager.PropertyManager;
import com.example.androidchoi.jobdam.Model.LoginData;
import com.example.androidchoi.jobdam.Model.User;
import com.example.androidchoi.jobdam.R;
import com.example.androidchoi.jobdam.Util.SoftKeyboardHandledLinearLayout;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputUserInfoFragment extends Fragment {

    EditText mEditName;
    EditText mEditEmail;
    EditText mEditPassword;
    EditText mEditPasswordCheck;
    SoftKeyboardHandledLinearLayout mLayoutRoot;
    RelativeLayout mLayoutProfile;

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        mLayoutRoot = (SoftKeyboardHandledLinearLayout)view.findViewById(R.id.layout_input_user_root);
        mLayoutProfile = (RelativeLayout)view.findViewById(R.id.layout_input_user_profile);
        mEditEmail = (EditText) view.findViewById(R.id.editText_signup_email);
        mEditEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideProfile();
                }
            }
        });
        mEditEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidEmail(s)) {
                    mEditEmail.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                } else {
                    mEditEmail.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDanger));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEditName = (EditText) view.findViewById(R.id.editText_signup_name);
        mEditName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideProfile();
                }
            }
        });
        mEditPassword = (EditText) view.findViewById(R.id.editText_signup_password);
        mEditPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideProfile();
                }
            }
        });
        mEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidPassword(s)) {
                    mEditPassword.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                } else {
                    mEditPassword.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDanger));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditPasswordCheck = (EditText) view.findViewById(R.id.editText_signup_password_check);
        mEditPasswordCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideProfile();
            }
        });
        mEditPasswordCheck.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    showProfile();
                }
                return false;
            }
        });
        mEditPasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isSamePassword(mEditPassword.getText().toString(), s.toString())) {
                    mEditPasswordCheck.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                } else {
                    mEditPasswordCheck.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDanger));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btn = (Button) view.findViewById(R.id.btn_input_complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        mLayoutRoot.setOnSoftKeyboardVisibilityChangeListener(new SoftKeyboardHandledLinearLayout.SoftKeyboardVisibilityChangeListener() {
            @Override
            public void onSoftKeyboardShow() {
                hideProfile();
            }

            @Override
            public void onSoftKeyboardHide() {
                showProfile();
            }
        });


        return view;
    }

    public void signUp(){
        if(TextUtils.isEmpty(mEditName.getText())){
            Toast.makeText(getActivity(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }else if(!isValidEmail(mEditEmail.getText())){
            Toast.makeText(getActivity(), "Email을 확인해 주세요.", Toast.LENGTH_SHORT).show();
        }else if(!isValidPassword(mEditPassword.getText())){
            Toast.makeText(getActivity(), "비밀번호는 8~20자의 영문, 숫자 조합으로 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }else if(!isSamePassword(mEditPassword.getText().toString(), mEditPasswordCheck.getText().toString())){
            Toast.makeText(getActivity(), "비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
        }else {
            NetworkManager.getInstance().signup(getActivity(),
                    mEditEmail.getText().toString(), mEditPassword.getText().toString(), mEditName.getText().toString(),
                    new NetworkManager.OnResultListener<LoginData>() {
                        @Override
                        public void onSuccess(LoginData result) {
                            if (result.getMessage().equals(LoginFragment.MESSAGE_SUCCESS)) {
                                PropertyManager.getInstance().setId(result.getUserId());
                                PropertyManager.getInstance().setPassword(mEditPassword.getText().toString());
                                User.getInstance().setUser(result.getUserId(), result.getName());
                                startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();
                            } else {
                                if (result.getMessage().equals(LoginFragment.MESSAGE_DUPLICATION)) {
                                    Toast.makeText(getActivity(), "중복된 ID입니다.", Toast.LENGTH_SHORT).show();
                                } else if (result.getMessage().equals(LoginFragment.MESSAGE_MISSING)) {
                                    Toast.makeText(getActivity(), "빠짐없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFail(int code) {
                            Log.i("error : ", code + "");
                            Toast.makeText(MyApplication.getContext(), "요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void hideProfile() {
        mLayoutProfile.setVisibility(View.GONE);
    }

    public void showProfile() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLayoutProfile.setVisibility(View.VISIBLE);
            }
        }, 300);
    }

    public boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean isValidPassword(CharSequence target) {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,20}$";
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Pattern.compile(PASSWORD_PATTERN).matcher(target).matches();
        }
    }

    public boolean isSamePassword(String password, String passwordCheck ){
        if(password.equals(passwordCheck)){
            return true;
        }else{
            return false;
        }
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
