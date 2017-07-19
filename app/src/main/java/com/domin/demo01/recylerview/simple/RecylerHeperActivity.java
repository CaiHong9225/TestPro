package com.domin.demo01.recylerview.simple;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.domin.demo01.R;
import com.domin.demo01.recylerview.Status;
import com.domin.demo01.utils.ToastUtils;
import com.lvr.library.adapter.CommonAdapter;
import com.lvr.library.adapter.MultiItemCommonAdapter;
import com.lvr.library.anims.animators.LandingAnimator;
import com.lvr.library.holder.BaseViewHolder;
import com.lvr.library.recyclerview.HRecyclerView;
import com.lvr.library.recyclerview.OnLoadMoreListener;
import com.lvr.library.recyclerview.OnRefreshListener;
import com.lvr.library.support.MultiItemTypeSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.media.CamcorderProfile.get;

/**
 * 一个多类型的adapter ,添加了header和footer view，上拉刷新和来拉加载
 */
public class RecylerHeperActivity extends AppCompatActivity implements OnLoadMoreListener, OnRefreshListener {

    private List<Status> mDatas;
    private final int NORMAL_TYPE = 1;
    private final int ANOTHER_TYPE = 2;
    private Random mRandom = new Random();
    private int[] type = new int[2];
    private HRecyclerView mRecylerView;
    private MultiItemTypeSupport<Status> mItemTypeSupport;
    private MultiItemCommonAdapter<Status> mAdapter;
    private int n = 1;
    private LoadMoreFooterView mLoadMoreFooterView;
    private int count = 1;

    public RecylerHeperActivity() {}

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    refresh(1);
                    mRecylerView.setRefreshing(false);
                    break;
                case 6:
//                    mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                    break;
                case 5:
                    mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR);
                    break;
                default:
                    loadMore(n++);
                    mDatas.addAll(loadMore(count++));
                    mAdapter.notifyItemInserted(mDatas.size()+1);
                    mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_heper);
        initData();
        initView();
    }

    private int getRandomInt() {
        return mRandom.nextInt(2);
    }

    private void initData() {
        type[0] = NORMAL_TYPE;
        type[1] = ANOTHER_TYPE;
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Status status = new Status();
            status.title = "data :" + i;
            status.TYPE = type[getRandomInt()];
            mDatas.add(status);
        }
    }

    private void initView() {
        mRecylerView = (HRecyclerView) findViewById(R.id.helper_recyclerview);

        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mRecylerView.setItemAnimator(new LandingAnimator());
        mRecylerView.addItemDecoration(new MyDecoration(this, MyDecoration.HORIZONTAL_LIST));
        mItemTypeSupport = new MultiItemTypeSupport<Status>() {
            @Override
            public int getLayoutId(int i) {
                if (i == NORMAL_TYPE) {
                    return R.layout.item_layout;
                } else {
                    return R.layout.quick_recylerview_item;
                }
            }

            @Override
            public int getItemViewType(int i, Status status) {
                int ty = mDatas.get(i).TYPE;
                if (ty == NORMAL_TYPE) {
                    return NORMAL_TYPE;
                }
                return ANOTHER_TYPE;
            }
        };
        mAdapter = new MultiItemCommonAdapter<Status>(this, mDatas, mItemTypeSupport) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, int i) {
                if (mDatas.get(i).TYPE == NORMAL_TYPE) {
                    baseViewHolder.setText(R.id.tv_info, mDatas.get(i).title + "NORMAL");
                } else {
                    baseViewHolder.setText(R.id.status_tv, mDatas.get(i).title + "ANOTHER");
                }
            }
        };
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                ToastUtils.showShortToast("点击");
            }
        });
        mRecylerView.setOnRefreshListener(this);
        mRecylerView.setOnLoadMoreListener(this);
        mLoadMoreFooterView = (LoadMoreFooterView) mRecylerView.getLoadMoreFooterView();
        mLoadMoreFooterView.setOnRetryListener(new LoadMoreFooterView.OnRetryListener() {
            @Override
            public void onRetry(LoadMoreFooterView view) {
                ToastUtils.showShortToast("err");
                loadMore(count++);
                mDatas.addAll(loadMore(count++));
                mAdapter.notifyItemInserted(mDatas.size()+1);
            }
        });
        mRecylerView.setAdapter(mAdapter);
    }

    private List<Status> loadMore(int i) {
        List<Status> more_list = new ArrayList<>();
        for (int m = 0; m < 5; m++) {
            Status status = new Status();
            status.TYPE = type[getRandomInt()];
            status.title = "loadmore" + i;
            more_list.add(status);
        }
        return more_list;
    }

    private List<Status> refresh(int i) {
        mDatas.clear();
        List<Status> more_list = new ArrayList<>();
        for (int m = 0; m < 5; m++) {
            Status status = new Status();
            status.TYPE = type[getRandomInt()];
            status.title = "refrsh" + i;
            more_list.add(status);
        }
        mDatas.addAll(more_list);
        mAdapter.notifyDataSetChanged();
        return more_list;
    }

    @Override
    public void onLoadMore() {
        if (mLoadMoreFooterView.canLoadMore()) {
            mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
            Message message = Message.obtain();
            message.what = count++;
            mHandler.sendMessageDelayed(message, 2000);
        }
    }

    @Override
    public void onRefresh() {
        Message message = Message.obtain();
        message.what = 0;
        mHandler.sendMessageDelayed(message,2000);
    }
}
