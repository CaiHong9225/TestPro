package com.domin.demo01.drawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.domin.demo01.BaseActivity;
import com.domin.demo01.MainActivity;
import com.domin.demo01.R;
import com.domin.demo01.jsbridge.JsActivity;
import com.domin.demo01.utils.SearchUtils;
import com.domin.demo01.utils.ToastUtils;
import com.domin.demo01.widget.GangedRecyclerview.GrandRecylerViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统生成的抽屉页面
 */
public class DrawerAndNaviActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,TabLayout.OnTabSelectedListener {
    List<String> mData;
    private ViewPager mViewPager;
    private List<Fragment> list_fragment;
    private ViewPagerAdapter mFragmentPagerAdapter;
    private DrawerLayout drawer;

    private LinearLayout mContentPanel;
    private CardView mCardViewSearch;
    private LinearLayout mSearchLayout;
    private ImageView mIvSearchBack;
    private EditText mEtSearch;
    private ImageView mClearSearch;
    private RecyclerView mRecycleview;
    private AppBarLayout mAppBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerandnavi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main2);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.action_search)
                {
                    SearchUtils.handleToolBar(getApplicationContext(),mCardViewSearch,mEtSearch,mAppBarLayout);

                }
                return false;
            }
        });
        toolbar.setTitle("BiliBili");
    //    setSupportActionBar(toolbar);
        findView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initTabLayout();

    }
    private void findView(){
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        mContentPanel = (LinearLayout) findViewById(R.id.contentPanel);
        mCardViewSearch = (CardView) findViewById(R.id.cardView_search);
        mSearchLayout = (LinearLayout) findViewById(R.id.search_layout);
        mIvSearchBack = (ImageView) findViewById(R.id.iv_search_back);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mClearSearch = (ImageView) findViewById(R.id.clearSearch);
        mRecycleview = (RecyclerView) findViewById(R.id.recycleview);
    }
    private  void initTabLayout()
    {
        //设置TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        list_fragment = new ArrayList<>();
        mData = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB" + i));
            mData.add("Tab" + i);
            list_fragment.add(DummyFragment.newInstance(i, "1"));
        }
        mFragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list_fragment, mData);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        //TabLayout的切换监听
        tabLayout.setOnTabSelectedListener(this);

        initResultItem();
        initListener();
    }
    private void initResultItem()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("优酷");
        list.add("土豆");
        list.add("爱奇艺");
        list.add("哔哩哔哩");
        list.add("youtube");
        list.add("斗鱼");
        list.add("熊猫");
        Rv_SearchAdapter adapter = new Rv_SearchAdapter(list, new Rv_SearchAdapter.IListener() {
            @Override
            public void normalItemClick(String s) {
                Toast.makeText(DrawerAndNaviActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clearItemClick() {
                Toast.makeText(DrawerAndNaviActivity.this, "清除历史记录", Toast.LENGTH_SHORT).show();
            }
        });
        mRecycleview.setAdapter(adapter);
        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }
    private void initListener()
    {
        mIvSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchUtils.handleToolBar(getApplication(),mCardViewSearch,mEtSearch,mAppBarLayout);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
//            ToastUtils.showShortToast("setting");
            SearchUtils.handleToolBar(getApplicationContext(),mCardViewSearch,mEtSearch);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent  =new Intent(this, GrandRecylerViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent  =new Intent(this, JsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
