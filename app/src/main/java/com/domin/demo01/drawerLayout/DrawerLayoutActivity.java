package com.domin.demo01.drawerLayout;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.domin.demo01.R;

import java.util.ArrayList;
import java.util.List;

import static com.domin.demo01.R.id.toolbar;
/**
 * 类似AcFun的首页
 */
public class DrawerLayoutActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    List<String> mData;
    private ViewPager mViewPager;
    private List<Fragment> list_fragment;
    private ViewPagerAdapter mFragmentPagerAdapter;
    private TextView tv_net_contro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabandtoobar);
        //initContent();
        intView();

        mToolbar.setTitle("抽屉demo");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        //mToolbar.inflateMenu(R.menu.base_toolbar_menu);
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //创建返回键
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mToggle.syncState();
        mDrawerLayout.setDrawerListener(mToggle);
    }
    private void intView()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tv_net_contro = (TextView) findViewById(R.id.net_contro);
        tv_net_contro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawerLayoutActivity.this,"tv_net_contro",0).show();
            }
        });

        //设置TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        list_fragment = new ArrayList<>();
        mData = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB" + i));
            mData.add("Tab"+i);
            list_fragment.add(DummyFragment.newInstance(i,"1"));
        }
        mFragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),list_fragment,mData) ;
        mViewPager.setAdapter(mFragmentPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        //TabLayout的切换监听
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            private int mPosition;

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPosition = tab.getPosition();

                //切换的时候更新RecyclerView
//                initData(tab.getPosition()+1);
            //    mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //设置RecycleView
/*        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl_left);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int menuItemId = item.getItemId();
            if (menuItemId == R.id.action_search) {
                Toast.makeText(DrawerLayoutActivity.this , R.string.action_search , Toast.LENGTH_SHORT).show();

            } else if (menuItemId == R.id.action_notifi) {
                Toast.makeText(DrawerLayoutActivity.this , R.string.action_nofication , Toast.LENGTH_SHORT).show();

            } else if (menuItemId == R.id.action_item1) {
                Toast.makeText(DrawerLayoutActivity.this , R.string.item_01 , Toast.LENGTH_SHORT).show();

            } else if (menuItemId == R.id.action_item2) {
                Toast.makeText(DrawerLayoutActivity.this , R.string.item_02 , Toast.LENGTH_SHORT).show();

            }
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu,menu);
        return true;
    }


    /*   //RecyclerView Adapter
    private RecyclerView.Adapter mAdapter = new RecyclerView.Adapter<MyViewHolder>() {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(getLayoutInflater().inflate(R.layout.item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_info);
        }
    }*/
}
