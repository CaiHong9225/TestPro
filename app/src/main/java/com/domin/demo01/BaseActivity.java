package com.domin.demo01;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public  abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    public   final String TAG = getClass().getSimpleName();
    private SparseArray<View> mViews;
    protected abstract int  getLayoutId();
    protected abstract void  initViews();
    protected Toolbar toolbar;
    protected abstract void initData();
    protected abstract void initListener();
    //获取布局文件中的Fragment的ID
    protected abstract int getFragmentContentId();
    /**deal click event*/
    protected abstract void processClick(View v);
    //添加一个fragment
    protected void addFragment(BaseFragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(),fragment,fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }
    //移除fragment
    //多于一个回退fragment,剩余一个 直接结束界面
    protected void removeFragment(){
        if(getSupportFragmentManager().getBackStackEntryCount()>1){
            getSupportFragmentManager().popBackStack();
        }else{
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // initStatusBar();
        mViews = new SparseArray<>();
        initData();
        initViews();
        initListener();

    }
    private void initStatusBar()
    {
        //核心代码.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK==keyCode){
            if(getSupportFragmentManager().getBackStackEntryCount()==1){
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    /**
     * 通过ID找view
     * @param viewId
     * @param <E>
     * @return
     */
    public <E extends View> E findView(int viewId){
        E view = (E) mViews.get(viewId);
        if(view==null){
            view = (E) findViewById(viewId);
            mViews.put(viewId,view);
        }
        return view;
    }

    /**
     * 通过View设置监听
     * @param view
     * @param <E>
     */
    public <E extends View> void setOnClick(E view){
        view.setOnClickListener(this);
    }
}
