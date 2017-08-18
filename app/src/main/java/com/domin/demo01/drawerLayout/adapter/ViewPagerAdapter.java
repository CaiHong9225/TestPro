package com.domin.demo01.drawerLayout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
/**
 * Created by wangQ on 2017/7/3.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> mData;

    private List<Fragment> list_frament;
    public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> list) {
        super(fm);
        this.mData =list;
        this.list_frament =fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return list_frament.get(position);
    }

    @Override
    public int getCount() {
        return  list_frament.size()>0? list_frament.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position);
    }
}
