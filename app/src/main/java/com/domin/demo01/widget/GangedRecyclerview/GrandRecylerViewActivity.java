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
import android.view.View;

import com.domin.demo01.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.id.list;
import static android.R.transition.move;

/**
 * 左右联动界面
 */
public class GrandRecylerViewActivity extends AppCompatActivity implements CheckListener {
    private RecyclerView rvSort;
    private SortAdapter mSortAdapter;
    private SortDetailFragment mSortDetailFragment;
    private Context mContext;
    private int targetPosition ;
    private boolean isMoved;
    private LinearLayoutManager mLinearLayoutManager;
    private SortBean mSortBean;

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
        String assetsData = getAssertData("sort.json");
        Gson gson =new Gson();
        mSortBean = gson.fromJson(assetsData,SortBean.class);
        List<SortBean.CategoryOneArrayBean> categoryOneArray  = mSortBean.getCategoryOneArray();
        List<String> list = new ArrayList<>();
        for(int i=0;i<categoryOneArray .size();i++){
            list.add(categoryOneArray.get(i).getName());
        }
        mSortAdapter = new SortAdapter(mContext, list, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if (mSortDetailFragment != null) {
                    isMoved=true;
                    targetPosition=position;
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
        Bundle bundle =new Bundle();
        bundle.putParcelableArrayList("right",mSortBean.getCategoryOneArray());;
        mSortDetailFragment.setArguments(bundle);
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
        if (isLeft) {

            mSortAdapter.setCheckedPosition(position);
            //此处的位置需要根据每个分类的集合来进行计算
            int count = 0;
            for (int i = 0; i < position; i++) {
                count += mSortBean.getCategoryOneArray().get(i).getCategoryTwoArray().size();
            }
            count += position;
            //向碎片中传值
            mSortDetailFragment.setData(count);
        }else{
            if(isMoved){
                isMoved=false;
            }else {
                mSortAdapter.setCheckedPosition(position);
            }
            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));
        }
        moveToCenter(position);
    }

    /**
     * 当前选中item居中
     * @param position
     */
    private void moveToCenter(int position)
    {
        View childAt =rvSort.getChildAt(position-mLinearLayoutManager.findFirstVisibleItemPosition());
        int y = (childAt.getTop()-rvSort.getHeight()/2);
        rvSort.smoothScrollBy(0,y);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("分类");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvSort = (RecyclerView) findViewById(R.id.rv_sort);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        rvSort.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        rvSort.addItemDecoration(decoration);

    }

    private String getAssertData(String path){
        String result ="";
        try{
            InputStream mAssets = getAssets().open(path);
            int length = mAssets.available();
            byte[] buffer =new byte[length];
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;

        }catch (IOException e){
            e.printStackTrace();
            return result;
        }
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
