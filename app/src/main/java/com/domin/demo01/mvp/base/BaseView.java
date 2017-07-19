package com.domin.demo01.mvp.base;

import android.view.View;
/**
 * Created by wangQ on 2017/6/30.
 */

public interface BaseView {
    /**
     * 显示loading框
     */
    void  showProgress();

    /**
     * 隐藏loading框
     */
    void hideProgress();

    void toast(CharSequence charSequence);
    void toast(int id);
    void toastLong(CharSequence s);
    void toastLong(int id);

    /**
     * 展示空布局
     */
    void showNullLayout();

    /**
     * 隐藏空布局
     */
    void hideNullLayout();

    /**
     * 展示错误界面
     * @param onClickListener
     */
    void showErrorLayout(View.OnClickListener onClickListener);

    /**
     * 隐藏错误布局
     */
    void hideErrorLayout();
}
