package com.domin.demo01.commonviewpager;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.domin.demo01.R;
import com.zhouwei.indicatorview.CircleIndicatorView;

import java.util.List;
/**
 * Created by wangz on 2017/6/8.
 */

public class CommonViewPager<T> extends RelativeLayout {
    private final String TAG = CommonViewPager.class.getSimpleName();
    private ViewPager mViewPager;
    private CommonViewPagerAdapter mAdapter;
    private CircleIndicatorView mCircleIndicatorView;

    public CommonViewPager(Context context) {
        super(context);
        init();
    }

    public CommonViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CommonViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommonViewPager(Context context,
                           AttributeSet attrs,
                           int defStyleAttr,
                           int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.common_viewpager,this,false);
        mViewPager = (ViewPager) view.findViewById(R.id.common_view_pager);
        mCircleIndicatorView = (CircleIndicatorView) view.findViewById(R.id.commom_view_pager_indicator_view);
    }
    //设置数据
    public void setPages(List<T> data,ViewPagerHolderCreator creator){
        Log.i(TAG,"data :"+data.size());
        mAdapter = new CommonViewPagerAdapter(data,creator);
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mCircleIndicatorView.setUpWithViewPager(mViewPager);
    }
    public void setCurrentItem(int currentItem){
        mViewPager.setCurrentItem(currentItem);
    }
    public int getCurrentItem(){
        return  mViewPager.getCurrentItem();
    }
    //设置动画
    public void setPageTransFormer(boolean reverseDrawingOrder,ViewPager.PageTransformer transformer){
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }
    public  void  addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener){
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }
    public void setIndicatorVisble(boolean visble){
        if(visble){
            mCircleIndicatorView.setVisibility(View.VISIBLE);
        }else{
            mCircleIndicatorView.setVisibility(View.GONE);
        }
    }
    public ViewPager getViewPager(){
        return mViewPager;
    }
}
