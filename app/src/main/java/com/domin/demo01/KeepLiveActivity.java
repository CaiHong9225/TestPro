package com.domin.demo01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
/**
 * 一像素的保活界面 注意（通过动态注册广播的方式启动）
 * 场景为：屏幕解锁 锁屏
 */
public class KeepLiveActivity extends AppCompatActivity {
    private static KeepLiveActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
    }

    /**
     * go
     */
    public static void startKeepLive() {
        Intent mIntent = new Intent(MyApplication.getInstance().getAppContext(), KeepLiveActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getInstance().getAppContext().startActivity(mIntent);
    }

    /**
     * stop
     */
    public static void killKeepLive() {
        if (instance != null) {
            instance.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}

