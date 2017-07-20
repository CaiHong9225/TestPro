package com.domin.demo01.mvp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.domin.demo01.R;
import com.domin.demo01.mvp.presenter.TestPresenter;
import com.domin.demo01.mvp.view.TestContract;

public class TestActivity extends AppCompatActivity implements TestContract.View{

    private TestContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        mPresenter = new TestPresenter(this);
        //调用数据接口
        mPresenter.getHttpData();
    }

    @Override
    public void updateUI(String response) {
        //更新UI
    }

    @Override
    public void updateFail(Throwable t) {
        //fail后提示
    }
}
