package com.domin.demo01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    private TextView tv;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
          tv=findView(R.id.tv);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        setOnClick(tv);
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void processClick(View v) {
            switch (v.getId()){

            }
    }

  
}
