package com.example.androidchoi.jobdam.LogIn;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidchoi.jobdam.MainActivity;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Manager.PropertyManager;
import com.example.androidchoi.jobdam.Model.User;
import com.example.androidchoi.jobdam.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputUserInfoFragment extends Fragment {

    EditText mEditName;
    EditText mEditEmail;
    EditText mEditPassword;
    EditText mEditPasswordCheck;

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
        mEditEmail = (EditText) view.findViewById(R.id.editText_signup_email);
        mEditName = (EditText)view.findViewById(R.id.editText_signup_name);
        mEditPassword = (EditText) view.findViewById(R.id.editText_signup_password);
        mEditPasswordCheck = (EditText)view.findViewById(R.id.editText_signup_password_check);

        Button btn = (Button) view.findViewById(R.id.btn_input_complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance().signup(getActivity(), mEditEmail.getText().toString(), mEditPassword.getText().toString()
                    , new NetworkManager.OnResultListener<String>() {
                        @Override
                    public void onSuccess(String result) {
                        if (result.equals("success")) {
                            String email = mEditEmail.getText().toString();
                            String name = mEditName.getText().toString();
                            PropertyManager.getInstance().setId(email);
                            PropertyManager.getInstance().setPassword(mEditPassword.getText().toString());
                            User.getInstance().setUser(email, name);
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        } else {
                                                    // ...
                        }
                    }
                    @Override
                    public void onFail(int code) {
                        // ...
                    }
                });
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
