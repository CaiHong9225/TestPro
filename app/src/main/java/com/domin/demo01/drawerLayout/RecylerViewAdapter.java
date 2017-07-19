package com.domin.demo01.drawerLayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.domin.demo01.R;

import java.util.List;
/**
 * Created by wangQ on 2017/7/3.
 */

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder> {

    private final LayoutInflater mInflater;
    private List<String> mDatas;

    public RecylerViewAdapter(List<String> list, Context context) {
        this.mDatas =list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new MyViewHolder(mInflater.inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

     static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_info);
        }
    }
}
