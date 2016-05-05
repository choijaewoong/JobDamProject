package com.example.androidchoi.jobdam.Dialog;

import android.app.Dialog;
import android.os.Bundle;
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
import com.example.androidchoi.jobdam.CardBoxFragment;
import com.example.androidchoi.jobdam.CardWriteActivity;
import com.example.androidchoi.jobdam.MainActivity;
import com.example.androidchoi.jobdam.R;

public class QuestionListDialogFragment extends DialogFragment {

    Toolbar toolbar;
    ListView mListView;
    DialogCategoryAdapter mAdapter;

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
        int width = getResources().getDimensionPixelSize(R.dimen.question_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.question_dialog_height);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_list_dialog, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
        mListView = (ListView) view.findViewById(R.id.list_view_category);

        mAdapter = new DialogCategoryAdapter();
//        mCategoryList = CategoryData.get(getActivity()).getCategoryList();
//        mAdapter.setItems(mCategoryList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity() instanceof CardWriteActivity) {
                    ((CardWriteActivity) getActivity()).setCategory(position);
                }  else if (getActivity() instanceof MainActivity) {
                    CardBoxFragment cardBoxFragment;
                    cardBoxFragment = (CardBoxFragment)getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_CARD_BOX);
                    if(cardBoxFragment != null){
                        cardBoxFragment.moveCategory(position);
                    }
                }
                dismiss();
            }
        });
        return view;
    }
}
