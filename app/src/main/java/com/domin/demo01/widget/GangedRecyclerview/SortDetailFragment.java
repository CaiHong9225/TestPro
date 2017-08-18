package com.domin.demo01.widget.GangedRecyclerview;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.domin.demo01.R;

import java.util.ArrayList;
import java.util.List;

public class SortDetailFragment extends BaseFragment<SortDetailPresenter, String> implements CheckListener {
    private RecyclerView mRv;
    private ClassifyDetailAdapter mAdapter;
    private GridLayoutManager mManager;
    private List<RightBean> mDatas = new ArrayList<>();
    private ItemHeaderDecoration mDecoration;
    private boolean move = false;
    private int mIndex = 0;
    private CheckListener checkListener;

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort_detail;
    }

    @Override
    protected void initCustomView(View view) {
        mRv = (RecyclerView) view.findViewById(R.id.rv);
    }

    @Override
    protected void initListener() {
        mRv.addOnScrollListener(new RecyclerViewListener());
    }

    @Override
    protected SortDetailPresenter initPresenter() {
        showRightPage(1);
        mManager = new GridLayoutManager(mContext, 3);
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDatas.get(position).isTitle() ? 3 : 1;
            }
        });
        mRv.setLayoutManager(mManager);
        mAdapter = new ClassifyDetailAdapter(mContext, mDatas, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                String content = "";
                switch (id) {
                    case R.id.root:
                        content = "title";
                        break;
                    case R.id.content:
                        content = "content";
                        break;

                }
                Snackbar snackbar = Snackbar.make(mRv, "当前点击的是" + content + ":" + mDatas.get(position).getName(), Snackbar.LENGTH_SHORT);
                View mView = snackbar.getView();
                mView.setBackgroundColor(Color.parseColor("#FA7199"));
                TextView text = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                text.setTextColor(Color.WHITE);
                text.setTextSize(25);
                snackbar.show();
            }
        });

        mRv.setAdapter(mAdapter);
        mDecoration = new ItemHeaderDecoration(mContext, mDatas);
        mRv.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);
        initData();
        return new SortDetailPresenter();
    }

    private void initData() {
        ArrayList<SortBean.CategoryOneArrayBean> rightList = getArguments().getParcelableArrayList("right");
        for (int i = 0; i < rightList.size(); i++) {
            RightBean head = new RightBean(rightList.get(i).getName());
            //头部设置为true
            head.setTitle(true);
            head.setTitleName(rightList.get(i).getName());
            head.setTag(String.valueOf(i));
            mDatas.add(head);
            List<SortBean.CategoryOneArrayBean.CategoryTwoArrayBean> categoryTwoArray = rightList.get(i).getCategoryTwoArray();
            for (int j = 0; j < categoryTwoArray.size(); j++) {
                RightBean body = new RightBean(categoryTwoArray.get(j).getName());
                body.setTag(String.valueOf(i));
                String name = rightList.get(i).getName();
                body.setTitleName(name);
                mDatas.add(body);
            }

        }

        mAdapter.notifyDataSetChanged();
        mDecoration.setData(mDatas);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void refreshView(int code, String data) {

    }

    public void setData(int n) {
        mIndex = n;
        mRv.stopScroll();
        smoothMoveToPosition(n);
    }
    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }

    /**
     * 滚动方法
     * @param n 指定滚动位置
     */
    private void smoothMoveToPosition(int n) {
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRv.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRv.getChildAt(n - firstItem).getTop();
            //位置在title上方
            mRv.smoothScrollBy(0, top);
        } else {
            mRv.smoothScrollToPosition(n);
            move = true;
        }
    }





    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                //左侧recyclerview传过来的位置
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                Log.d("n---->", String.valueOf(n));
                //在可见范围内的滚动事件
                if (0 <= n && n < mRv.getChildCount()) {
                    int top = mRv.getChildAt(n).getTop();
                    Log.d("top--->", String.valueOf(top));
                    mRv.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position, isScroll);
    }
}
