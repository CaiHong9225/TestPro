package com.domin.demo01.jsbridge;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.domin.demo01.utils.ToastUtils;
import com.just.library.AgentWeb;
/**
 * Created by erhii on 2017/7/10.
 * js调用的native类
 */

public class AndroidInterface {

    private AgentWeb mAgentWeb;
    private Activity mActivity;

    public AndroidInterface(AgentWeb agentWeb, Activity activity) {
        mAgentWeb = agentWeb;
        mActivity = activity;
    }
    private Handler deliver = new Handler(Looper.myLooper());

    @JavascriptInterface
    public void callAndroid(final String msg)
    {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShortToast(TextUtils.isEmpty(msg)?"调用成功":msg);
            }
        });
    }
}
