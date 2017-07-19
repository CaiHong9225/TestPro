package com.domin.demo01;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private  final String TAG="BaseFragment";
    private boolean isVisible = false;
    private boolean isIniView =false;
    private boolean isFirstLoad = true;

    public  View convertView ;
    private SparseArray<View> mViews;
    public abstract int getLayoutId();
    public abstract void initViews(View view,Bundle saveInstanState);
    public abstract void initListener();
    public abstract void initData();
    public abstract void processClick(View v);

    protected BaseActivity mActivity;
    //获取宿主Activity
    protected BaseActivity getHoldingActivity(){
        return mActivity;
    }
    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //懒加载
    private void LazyLoad(){
        //true   true（可见之后） true(createView)
        if(!isFirstLoad||!isVisible||!isIniView){
            //不是第一次加载 不可见 不是第一次初始化界面
            Log.i(TAG,"懒加载");
            return;
        }
        //load data
        initListener();
        initData();
        //加载一次数据后false 首次进页面之后 true
        isFirstLoad =false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible =true;
//            LazyLoad();
        }else {
            //设置为不可见
            isVisible=false;
            onInvisible();
        }
    }

    /**
     * 不可见情况下处理
     */
    protected  void onInvisible(){
        //do something

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    //    this.mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViews = new SparseArray<>();
        convertView = inflater.inflate(getLayoutId(),container,false);
        initViews(convertView,savedInstanceState);
        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isIniView = true;
//        LazyLoad();
        //load data
        initListener();
        initData();
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    /**
     * 通过ID发现VIEW
     * @param viewId
     * @param <E>
     * @return
     */
    public  <E extends View> E findView(int viewId){
        if(convertView!=null){
            E view = (E) mViews.get(viewId);
            if(view==null){
                view = (E) convertView.findViewById(viewId);
                mViews.put(viewId,view);
            }
            return view;
        }
        return null;
    }

    /**
     * 设置监听事件
     * @param view
     * @param <E>
     */
    public <E extends View> void setOnClick(E view){
        view.setOnClickListener(this);
    }
}
