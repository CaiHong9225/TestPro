package com.domin.demo01.recylerview;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.security.PublicKey;
/**
 * Created by wangQ on 2017/7/19.
 */

public class MultipleItem implements MultiItemEntity {
    public static final int TEXT=1;
    public static  final int IMG=2;
    public int content;
    public int title;
    private int itemType;

    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
