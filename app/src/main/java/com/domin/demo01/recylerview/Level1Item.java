package com.domin.demo01.recylerview;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;

import static android.R.attr.level;
/**
 * Created by wangQ on 2017/7/19.
 */

public class Level1Item extends AbstractExpandableItem<Person> {
    public static final int TYPE_LEVEL_1=1;

    public String title;
    public int type;

    public Level1Item(String title, int type) {
        this.title = title;
        this.type = type;
    }

    @Override
    public int getLevel() {
        return TYPE_LEVEL_1;
    }
}
