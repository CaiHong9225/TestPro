package com.domin.demo01.recylerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.domin.demo01.R;

import java.util.List;
/**
 * Created by wangQ on 2017/7/3.
 */

public class QuickAdapter extends BaseQuickAdapter<Status,BaseViewHolder> {


    public QuickAdapter(@LayoutRes int layoutResId, @Nullable List<Status> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
            helper.setText(R.id.status_tv,item.title)
            .addOnLongClickListener(R.id.status_tv);//子空间

    }
}
