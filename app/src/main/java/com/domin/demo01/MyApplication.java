package com.domin.demo01;

import android.app.Application;
import android.content.Context;

import com.domin.demo01.utils.Utils;
/**
 * Created by wangQ on 2017/6/30.
 */

public class MyApplication extends Application {

    private static  MyApplication instance;
    public static MyApplication getInstance()
    {
        if(instance==null)
        {
            return new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());
    }
    public Context getAppContext()
    {
        return getApplicationContext();
    }

}
