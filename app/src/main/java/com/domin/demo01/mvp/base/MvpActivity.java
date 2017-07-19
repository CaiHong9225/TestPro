package com.domin.demo01.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;





/**
 * Created by zz on 2017/6/30.
 * make view bind with presenter
 */

public abstract class MvpActivity<V,P extends BasePresenter<V>> extends BaseAcitvity {
    protected P presenter;
    public abstract P initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }


}
