package com.domin.demo01.recylerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.domin.demo01.R;
import com.domin.demo01.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

public class BrvahActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private QuickAdapter mAdapter;
    private List<Status> mData;
    private boolean isErr = false;
    private int TOTAL_COUNTER;
    private int mCurrentCounter;
    private int n = 1;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int loadCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brvah);
        initData();
        initView();

    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.brvah_recylerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new QuickAdapter(R.layout.quick_recylerview_item, mData);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.isFirstOnly(false);
        mAdapter.disableLoadMoreIfNotFullPage();

        mAdapter.setEnableLoadMore(true);
     //   mAdapter.setUpFetchEnable(true);

//        mAdapter.setUpFetchListener(mUpFetchListener);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(sLoadMoreListener, mRecyclerView);
        mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShortToast("子控件长按监听");
                return false;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setEnableLoadMore(false);
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       // mAdapter.addData(0,refresh(n++));
                      //  mAdapter.replaceData(refresh(n++));
                        //在原有数据上进行追加
                        mAdapter.setNewData(refresh(n++));
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
                mAdapter.setEnableLoadMore(true);
            }
        });
    }

    private BaseQuickAdapter.UpFetchListener mUpFetchListener = new BaseQuickAdapter.UpFetchListener() {
        @Override
        public void onUpFetch() {
            mAdapter.setUpFetchEnable(true);
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.addData(0,refresh(n++));
                    mAdapter.setUpFetchEnable(false);

                }
            }, 3000);
        }
    };

    private void startUpFetch() {

    }

    private BaseQuickAdapter.RequestLoadMoreListener sLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            mSwipeRefreshLayout.setEnabled(false);
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<Status> data = loadMore(n++);
                    Log.i("Brvah","data TOP size :"+data.size());
                    mAdapter.addData(data);
                    int i =mAdapter.getData().size();
                    Log.i("Brvah","data size :"+i);
                    mAdapter.loadMoreEnd(false);//
                   // mAdapter.loadMoreComplete();
                    mSwipeRefreshLayout.setEnabled(true);

/*                    if(loadCount<2)
                    {
                        List<Status> data = loadMore(n++);
                        mAdapter.addData(data);
                        mAdapter.loadMoreComplete();
                        loadCount++;
                    }else
                    {
                        mAdapter.loadMoreEnd(false);
                    }*/
/*                    if (mCurrentCounter >= TOTAL_COUNTER) {
                        //数据加载完毕
                        mAdapter.loadMoreEnd(false);

                    } else {
                        if (isErr) {
                            mAdapter.addData(loadMore(n++));
                            mCurrentCounter = mAdapter.getData().size();
                            mAdapter.loadMoreComplete();
                        } else {
                            isErr = true;
                            Toast.makeText(BrvahActivity.this, "网络异常，请重试！", Toast.LENGTH_LONG).show();
                            mAdapter.loadMoreFail();
                        }
                    }*/
                }
            }, 2000);


        }
    };

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Status status = new Status();
            status.title = "第" + i + "条 :" + 1;
            mData.add(status);
        }
        TOTAL_COUNTER = mData.size();
    }

    private List<Status> refresh(int page) {
       //  mData.clear();
        for (int i = 0; i < 20; i++) {
            Status status = new Status();
            status.title = "第" + i + "条 : 刷新" + page;
            mData.add(status);
        }
        // mAdapter.notifyDataSetChanged();
        return mData;
        //       n=1;
    }

    private List<Status> loadMore(int page) {
         mData.clear();
        for (int i = 0; i < 10; i++) {
            Status status = new Status();
            status.title = "第" + i + "条 : 上拉" + page;
            mData.add(status);
        }
        // mAdapter.notifyDataSetChanged();
        return mData;
    }
}
