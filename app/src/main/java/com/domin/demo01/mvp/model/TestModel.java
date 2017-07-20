package com.domin.demo01.mvp.model;

import com.domin.demo01.mvp.basemvp.BaseModelCallBack;
/**
 * Created by wangQ on 2017/7/20.
 */

public class TestModel {

    /**
     * 数据访问接口
     * @param baseModelCallBack
     */
    public void getData(final BaseModelCallBack baseModelCallBack){

        baseModelCallBack.onResponse("succcess");
    }
}
