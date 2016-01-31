package com.example.androidchoi.jobdam.LogIn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceAgreementFragment extends Fragment {

    TextView mTextUserAgreement;
    TextView mTextPrivateInfo;
    CheckBox mCheckBox;
    CheckBox mCheckBox2;

    public ServiceAgreementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_agreement, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_service_agreement);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        mTextUserAgreement = (TextView)view.findViewById(R.id.text_user_agreement);
        mTextPrivateInfo = (TextView)view.findViewById(R.id.text_private_info);
        mTextUserAgreement.setText(Html.fromHtml(getString(R.string.text_user_agreement)));
        mTextPrivateInfo.setText(Html.fromHtml(getString(R.string.text_private_info)));

        mCheckBox = (CheckBox)view.findViewById(R.id.checkBox_user_agreement);
        mCheckBox2 = (CheckBox)view.findViewById(R.id.checkBox_private_info);

        Button button = (Button)view.findViewById(R.id.btn_next_level);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheckBox.isChecked() && mCheckBox2.isChecked())
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.login_container, new InputUserInfoFragment()).addToBackStack(null).commit();
                else
                    Toast.makeText(getActivity(), "약관에 모두 동의해주세요.", Toast.LENGTH_SHORT).show();
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

