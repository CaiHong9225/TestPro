package com.domin.demo01.mvp.basemvp;

/**
 * Created by wangQ on 2017/7/19.
 */

public interface BaseModelCallBack {

    void onResponse(String response);
    void onFailure(Throwable t);
}
