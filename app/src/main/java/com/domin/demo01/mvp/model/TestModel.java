package com.domin.demo01.mvp.model;

import com.domin.demo01.mvp.basemvp.BaseModelCallBack;
/**
 * Created by wangQ on 2017/7/20.
 */

public class TestModel {

    public void getData(final BaseModelCallBack baseModelCallBack){

        baseModelCallBack.onResponse("succcess");
    }
}
