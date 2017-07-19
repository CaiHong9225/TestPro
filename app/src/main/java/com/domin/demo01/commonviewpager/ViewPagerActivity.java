package com.domin.demo01.commonviewpager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.domin.demo01.BaseActivity;
import com.domin.demo01.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private CommonViewPager mCommonViewPager;
    private List<DataEntry> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initData();
        initViews();
    }


    protected void initViews() {
        Log.i("viewpager","initview");
        Log.i("datas","dataSize:"+mDatas.size());
        mCommonViewPager = (CommonViewPager) findViewById(R.id.activity_common_view_pager);
        mCommonViewPager.setIndicatorVisble(false);
        mCommonViewPager.setPages(mDatas, new ViewPagerHolderCreator<ViewImageHolder>() {
            @Override
            public ViewImageHolder createViewHolder() {
                return new ViewImageHolder();
            }
        });
        Log.i("datas","dataSizeCompletes");
    }

    protected void initData() {
        mDatas = new ArrayList<>();
            for(int i=0;i<5;i++){
                DataEntry  dataEntry = new DataEntry();
                dataEntry.desc =  "(* ￣3)(ε￣ *)"+i;
                dataEntry.imageResId=R.mipmap.ic_launcher_round;
                mDatas.add(dataEntry);
            }
    }

    /**
     * 提供ViewPager展示的ViewHolder
     * <P>用于提供布局和绑定数据</P>
     */
    public static class ViewImageHolder implements ViewPagerHolder<DataEntry>{
        private ImageView mImageView;
        private TextView mTextView;
        @Override
        public View createView(Context context) {
            Log.i("viewpage","初始化item布局");
            // 返回ViewPager 页面展示的布局
            View view = LayoutInflater.from(context).inflate(R.layout.viewpager_item,null);
            mImageView = (ImageView) view.findViewById(R.id.viewpager_item_image);
            mTextView = (TextView) view.findViewById(R.id.viewpager_item_text);
            return view;
        }

        @Override
        public void onBind(Context context, int position, DataEntry data) {
            // 数据绑定
            // 自己绑定数据，灵活度很大
            mImageView.setImageResource(data.imageResId);
            mTextView.setText(data.desc);
            Log.i("适配器","pageadapter"+position);
        }
    }
}
