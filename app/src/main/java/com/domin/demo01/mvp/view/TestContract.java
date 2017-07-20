package com.domin.demo01.mvp.view;

import com.domin.demo01.mvp.basemvp.BasePresenter;
import com.domin.demo01.mvp.basemvp.BaseView;
/**
 * Created by wangQ on 2017/7/20.
 */

public class TestContract {

    //mvp层的view层回调函数
    public interface View extends BaseView<Presenter>{
        void updateUI(String response);
        void updateFail(Throwable t);
    }

    //mvp中的presenter层的回调函数
    public interface Presenter extends BasePresenter{

        void getHttpData();
    }
}
