package com.example.androidchoi.jobdam;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.androidchoi.jobdam.Adpater.DialogCategoryAdapter;
import com.example.androidchoi.jobdam.Model.CategoryData;

import java.util.ArrayList;

public class CustomDialogFragment extends DialogFragment {

    Toolbar toolbar;
    ListView mListView;
    DialogCategoryAdapter mAdapter;
    ArrayList<CategoryData> mCategoryList = new ArrayList<CategoryData>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        Dialog dialog = getDialog();
        dialog.getWindow().setLayout(450, 500);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_dialog, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
        mListView = (ListView) view.findViewById(R.id.list_view_category);

        mAdapter = new DialogCategoryAdapter();
        mCategoryList = CategoryData.get(getActivity()).getCategoryList();
        mAdapter.setItems(mCategoryList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((CardWriteActivity) getActivity()).setCategory(position);
                dismiss();
            }
        });
        return view;
    }
}
