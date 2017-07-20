package com.domin.demo01.recylerview;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
/**
 * Created by wangQ on 2017/7/19.
 */

public class Level0Item extends AbstractExpandableItem<Level0Item> {
    public static final int TYPE_LEVEL_0 =0;
    public String title;

    public Level0Item(String title) {
        this.title = title;
    }

    @Override
    public int getLevel() {
        return TYPE_LEVEL_0;
    }
}
