package com.domin.demo01;

import android.app.Application;
import android.content.Context;

import com.domin.demo01.utils.Utils;
/**
 * Created by wangQ on 2017/6/30.
 */

public class MyApplication extends Application {
    private Context mContext;
    private static  MyApplication instance;
    public static MyApplication getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
        mContext =getApplicationContext();
        Utils.init(getApplicationContext());
    }
    public Context getAppContext()
    {
        return getApplicationContext();
    }
    public Context getContext() {
        return mContext;
    }
}
