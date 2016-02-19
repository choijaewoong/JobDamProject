/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidchoi.jobdam.Adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.jobdam.Manager.MyApplication;
import com.example.androidchoi.jobdam.Manager.NetworkManager;
import com.example.androidchoi.jobdam.Model.Articles;
import com.example.androidchoi.jobdam.R;

import java.util.ArrayList;
import java.util.List;

public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {

    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private List<Articles> mItems = new ArrayList<Articles>();
    private int mCurrentItemId = 0;
    private SimpleViewHolder mSimpleViewHolder;
    public SimpleViewHolder getSimpleViewHolder() {
        return mSimpleViewHolder;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView textContent;
        TextView textLikeCount;
        ImageView toggleLikeButton;
        LinearLayout linearLayout;
        Context context;
        public SimpleViewHolder(View view) {
            super(view);
            context = view.getContext();
            textContent = (TextView) view.findViewById(R.id.text_article);
            textContent.setMovementMethod(new ScrollingMovementMethod());
            textLikeCount = (TextView)view.findViewById(R.id.text_like_cnt);
            toggleLikeButton = (ImageView)view.findViewById(R.id.image_like_button);
            linearLayout = (LinearLayout)view.findViewById(R.id.layout_article_like);
        }

//        public void showLikeToast(){
//            mCallback.onShowToast();
//            View view = LayoutInflater.from(context).inflate(R.layout.view_toast_like,
//                    (ViewGroup)findViewById(R.id.container_like_toast));
//
//            Toast toast = new Toast(context);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.setDuration(Toast.LENGTH_SHORT);
//            toast.setView(view);
//            toast.show();
//        }


    }



    public LayoutAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
//        mItems = new ArrayList<>(COUNT);
//        for (int i = 0; i < COUNT; i++) {
//            addItem(i);
//        }

        mRecyclerView = recyclerView;
    }

    public void setItems(ArrayList<Articles> items){
        mItems = items;
        notifyDataSetChanged();
    }

//    public void addItem(int position) {
//        final int id = mCurrentItemId++;
//        mItems.add(position, id);
//        notifyItemInserted(position);
//    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.view_article_layout, parent, false);
        mSimpleViewHolder = new SimpleViewHolder(view);
        return mSimpleViewHolder;
    }



    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
//        holder.title.setText(mItems.get(position).toString());
        holder.textContent.setText(mItems.get(position).getArticle().getContent());
        holder.toggleLikeButton.setSelected(mItems.get(position).getArticle().getLikeBool());
        holder.textLikeCount.setText(mItems.get(position).getArticle().getLikeCount() + "");
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkManager.getInstance().likeArticle(MyApplication.getContext(),
                        mItems.get(position).getId(), new NetworkManager.OnResultListener<Articles>() {
                            @Override
                            public void onSuccess(Articles result) {
                                if(result.getArticle().getLikeBool()){
                                    holder.toggleLikeButton.setSelected(true);
                                }
                                else {
                                    holder.toggleLikeButton.setSelected(false);
                                }
                                mItems.get(position).setArticle(result);
                                holder.textLikeCount.setText(mItems.get(position).getArticle().getLikeCount() + "");
                            }

                            @Override
                            public void onFail(int code) {
                                Log.i("error : ", code + "");
                                Toast.makeText(MyApplication.getContext(), "요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        final View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
            }
        });
//        final int itemId = mItems.get(position);
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
