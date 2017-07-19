package com.domin.demo01.mvp.base;

/**
 * Created by wangQ on 2017/6/30.
 */

public abstract class BasePresenter<T> {

    public T view;
    public void attach(T view)
    {
        this.view =view;
    }

    public void detach()
    {
        this.view=null;
    }


}
