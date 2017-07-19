package com.domin.demo01.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.domin.demo01.R;

import static com.domin.demo01.R.id.tv;

public class LocalServiceActivity extends AppCompatActivity {
    private Button btn_bound,btn_unbund,btn_call_func;
    private LocalService mBoundService;
    private TextView tv_display;
    private boolean mIsBound;
    private static final int MSG_FROM_SERVICE =1;
    private IncomingHandler mHandler = new IncomingHandler();
    private LocalService.LocalBinder mLocalBinder;
    private final class IncomingHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MSG_FROM_SERVICE:
                    tv_display.setText(msg.obj+"");
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_service);
        initView();

    }
    private void initView()
    {
        btn_bound = (Button) findViewById(R.id.bangding);
        btn_unbund = (Button) findViewById(R.id.jiebang);
        btn_call_func = (Button) findViewById(R.id.diaoyongfuwu);
        tv_display = (TextView) findViewById(R.id.tv_showmsg);
        btn_bound.setOnClickListener(mListener);
        btn_unbund.setOnClickListener(mListener);
        btn_call_func.setOnClickListener(mListener);
    }
    void doBindService(){
        bindService(new Intent(LocalServiceActivity.this,LocalService.class), mConnection,Context.BIND_AUTO_CREATE);
        mIsBound =true;
    }
    void doUnBind(){
        if(mIsBound)
        {
            unbindService(mConnection);
            mIsBound =false;
        }

    }
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((LocalService.LocalBinder) service).getService();
            mLocalBinder = ((LocalService.LocalBinder) service).getLoaclBinde();
            mLocalBinder.show();
            mBoundService.setOnDataArrivedListener(new OnDataArrivedListener() {
                @Override
                public void onDataArrived(int data) {
                    mHandler.obtainMessage(MSG_FROM_SERVICE,data).sendToTarget();
                }
            });
            Toast.makeText(LocalServiceActivity.this,"绑定成功",0).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService=null;
            Toast.makeText(LocalServiceActivity.this,"解绑成功",0).show();
        }
    };
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.bangding:
                    doBindService();
                    break;
                case R.id.jiebang:
                    doUnBind();
                    break;
                case R.id.diaoyongfuwu:
                    Toast.makeText(LocalServiceActivity.this, "number:" + mBoundService.getRandomNumber(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnBind();
    }
}
