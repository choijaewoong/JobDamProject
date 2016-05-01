package com.example.androidchoi.jobdam.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidchoi.jobdam.R;

/**
 * Created by Tacademy on 2015-10-14.
 */
public class SaveDialogFragment extends DialogFragment {

    TextView mTextTitle;
    TextView mTextSubTitle;
    Button mButtonYes;
    Button mButtonNo;
    Button mButtonCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_save_dialog, container, false);
        mTextTitle = (TextView)view.findViewById(R.id.text_dialog_title);
        mTextSubTitle = (TextView)view.findViewById(R.id.text_dialog_sub_title);
        mButtonYes = (Button)view.findViewById(R.id.btn_dialog_yes);
        mButtonNo = (Button)view.findViewById(R.id.btn_dialog_no);
        mButtonCancel = (Button)view.findViewById(R.id.btn_dialog_cancel);

        mTextTitle.setText(R.string.dialog_save_title);
        mTextSubTitle.setText(R.string.dialog_save_sub_title);
        mButtonYes.setText(R.string.dialog_save_yes);
        mButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onYesEvent();
            }
        });
        mButtonNo.setText(R.string.dialog_save_no);
        mButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNoEvent();
            }
        });
        mButtonCancel.setText(R.string.dialog_save_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelEvent();
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        int width = getResources().getDimensionPixelSize(R.dimen.default_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.default_dialog_height);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
    }

    public interface ButtonEventListener {
        void onYesEvent();
        void onNoEvent();
        void onCancelEvent();
    }
    ButtonEventListener mListener;
    public void setButtonEventListener(ButtonEventListener listener){
        mListener = listener;
    }
}
