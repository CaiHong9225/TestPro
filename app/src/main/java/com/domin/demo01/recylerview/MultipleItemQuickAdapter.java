package com.domin.demo01.recylerview;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.domin.demo01.R;

import java.util.List;
/**
 * Created by wangQ on 2017/7/19.
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem,BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleItemQuickAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.item_layout);
        addItemType(MultipleItem.IMG,R.layout.item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()){
            case MultipleItem.TEXT:
                break;
            case MultipleItem.IMG:
                break;
        }
    }
}
