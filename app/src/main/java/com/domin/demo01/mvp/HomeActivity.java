package com.domin.demo01.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.domin.demo01.R;
import com.domin.demo01.mvp.base.MvpActivity;

public class HomeActivity extends MvpActivity<HomeView,HomePresenter> implements HomeView{
    //HomePresenter will attach to HomeView,and then homepresenter will use homeview s method
    private TextView tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv= (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void initData() {

    }

    public void getData(View view)
    {
        showProgress();
        presenter.getGankData();
    }
    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public void getDataHttp(String data) {
            tv.setText(data);
            hideProgress();
            toast("success!");
    }

    @Override
    public void getDataHttpFail(String msg) {
            tv.setText(msg);
            toast(msg);
    }

    @Override
    public void getLocalData(String data) {

    }

}
