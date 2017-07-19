package com.domin.demo01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public abstract class AppActivity extends BaseActivity {
    //获取第一个fragment
    protected abstract BaseFragment getFirstFrgment();
    protected void handleIntent(Intent intent){};//获取Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null!=getIntent()){
            handleIntent(getIntent());
        }//避免重复添加Fragment
        if(null!=getSupportFragmentManager().getFragments()){
            BaseFragment firstFrament = getFirstFrgment();
            if(null!=firstFrament){
                addFragment(firstFrament);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getFragmentContentId() {
        return R.id.fragment_container;
    }

    @Override
    protected void processClick(View v) {

    }

}
