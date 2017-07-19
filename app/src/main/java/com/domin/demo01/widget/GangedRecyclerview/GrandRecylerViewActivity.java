package com.domin.demo01.widget.GangedRecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.domin.demo01.R;

import java.util.Arrays;
import java.util.List;

/**
 * 左右联动界面
 */
public class GrandRecylerViewActivity extends AppCompatActivity implements CheckListener {
    private RecyclerView rvSort;
    private SortAdapter mSortAdapter;
    private SortDetailFragment mSortDetailFragment;
    private Context mContext;
    public static boolean left;
    public static int finalNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandrecylerview);
        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initData();
    }

    private void initData() {
        String[] classify = getResources().getStringArray(R.array.pill);
        List<String> list = Arrays.asList(classify);
        mSortAdapter = new SortAdapter(mContext, list, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if (mSortDetailFragment != null) {
                    setChecked(position, true);
                }
            }
        });
        rvSort.setAdapter(mSortAdapter);
        createFragment();
    }

    public void createFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mSortDetailFragment = new SortDetailFragment();
        mSortDetailFragment.setListener(this);
        fragmentTransaction.add(R.id.lin_fragment, mSortDetailFragment);
        fragmentTransaction.commit();
    }

    /**
     * 用于右侧向左侧或者 左侧向右侧传值
     * @param position
     * @param isLeft
     */
    private void setChecked(int position, boolean isLeft) {
        finalNumber = position;
        left = isLeft;
        Log.d("boolean---->", String.valueOf(left));
        mSortAdapter.setCheckedPosition(position);
        if (isLeft) {
            //向碎片中传值
            mSortDetailFragment.setData(position * 10 + position);
        }

    }


    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("分类");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvSort = (RecyclerView) findViewById(R.id.rv_sort);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvSort.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        rvSort.addItemDecoration(decoration);

    }

    @Override
    public void check(int position, boolean isChecked) {
        //右侧传值左侧动
        setChecked(position, isChecked);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();;
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
