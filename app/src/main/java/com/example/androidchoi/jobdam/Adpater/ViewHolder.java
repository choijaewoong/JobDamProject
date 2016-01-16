package com.example.androidchoi.jobdam.Adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.jobdam.Model.EmotionData;
import com.example.androidchoi.jobdam.R;

/**
 * Created by Tacademy on 2015-10-06.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    TextView textDescription;
    ImageView imageIcon;
    public ViewHolder(View itemView) {
        super(itemView);
        textDescription = (TextView)itemView.findViewById(R.id.text_emotion_description);
        imageIcon = (ImageView)itemView.findViewById(R.id.image_emotion_icon);
        imageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    public void setData(EmotionData data){
        imageIcon.setImageResource(data.getImageResource());
        textDescription.setText(data.getDescription());
    }
}
