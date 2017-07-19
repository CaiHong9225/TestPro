package com.domin.demo01.mvp;

import com.domin.demo01.mvp.base.BasePresenter;
/**
 * Created by zz on 2017/6/30.
 * Home页面的Present   HomeView的初始化会在Mvp
 */

public class HomePresenter extends BasePresenter<HomeView> {

    public void getGankData()
    { //网络处理模块 database处理模块
        view.getDataHttp("this is remote data");
     //   view.getDataHttpFail("err");
    }
    public void getLocalDta(){
        view.getLocalData("this is local data");
    }

}
