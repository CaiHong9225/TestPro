package com.domin.demo01.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.domin.demo01.R;
import com.domin.demo01.utils.ToastUtils;

import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangQ on 2017/7/3.
 */

public class LocalService extends Service {

    private static final String TAG = LocalService.class.getSimpleName();
    private final Binder mBinder = new LocalBinder();
    private final Random mGenerator = new Random();
    private OnDataArrivedListener mArrivedListener;
    private ScheduledExecutorService mThreadPool;
    private int mCount = 0;

    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }

        //可以在这里处理逻辑
        LocalBinder getLoaclBinde() {
            return LocalBinder.this;
        }

        public void show() {
            ToastUtils.showShortToast("你好");
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //开启线程池定时任务
        ToastUtils.showShortToast("服务绑定了");
        mThreadPool = Executors.newScheduledThreadPool(1);
        mThreadPool.scheduleAtFixedRate(mTask, 0, 1000, TimeUnit.MILLISECONDS);
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        ToastUtils.showShortToast("他娘的又连上老子了");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mThreadPool != null) {
            mThreadPool.shutdown();
        }
        Toast.makeText(this, "服务解绑了", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            if (mArrivedListener != null) {
                mCount++;
                if (mCount == 1000) {
                    mCount = 0;
                }
                mArrivedListener.onDataArrived(mCount);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "服务停止了", Toast.LENGTH_SHORT).show();
    }

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    public void setOnDataArrivedListener(OnDataArrivedListener listener) {
        this.mArrivedListener = listener;
    }
}
