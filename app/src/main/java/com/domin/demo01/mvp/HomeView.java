package com.domin.demo01.mvp;

import com.domin.demo01.mvp.base.BaseView;
/**
 * Created by zz on 2017/6/30.
 * 每个单独功能的单独view功能
 */

public interface HomeView extends BaseView {

    void getDataHttp(String data);

    void getDataHttpFail(String msg);

    void getLocalData(String data);
}
