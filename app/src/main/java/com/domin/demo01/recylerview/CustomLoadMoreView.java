package com.domin.demo01.recylerview;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.domin.demo01.R;
/**
 * Created by wangQ on 2017/7/3.
 */

public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.rv_with_footer_loading;
    }

    @Override
    public boolean isLoadEndGone() {
        return true;
    }

    /**
     * 如果返回true，数据全部加载完毕后会隐藏加载更多
     * 如果返回false，数据全部加载完毕后会显示getLoadEndViewId()布局
     */

    @Override
    protected int getLoadingViewId() {
        return R.id.rv_with_footer_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.rv_with_footer_loading_err;
    }

    @Override
    protected int getLoadEndViewId() {
        return 0;
    }
}
