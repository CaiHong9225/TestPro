package com.domin.demo01.commonviewpager;

import com.domin.demo01.commonviewpager.ViewPagerHolder;
/**
 * Created by wangQ on 2017/6/8.
 */

public interface ViewPagerHolderCreator <VH extends ViewPagerHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
