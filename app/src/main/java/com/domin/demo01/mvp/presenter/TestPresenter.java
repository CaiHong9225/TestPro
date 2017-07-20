package com.domin.demo01.mvp.presenter;

import com.domin.demo01.mvp.basemvp.BaseModelCallBack;
import com.domin.demo01.mvp.model.TestModel;
import com.domin.demo01.mvp.view.TestContract;
/**
 * Created by wangQ on 2017/7/20.
 */

public class TestPresenter implements TestContract.Presenter {

    private TestModel mTestModel;
    private TestContract.View  mView;

    public TestPresenter(TestContract.View view) {
        mView = view;
        mTestModel = new TestModel();
    }

    @Override
    public void start() {

    }

    /**
     * 异步获取数据
     */
    @Override
    public void getHttpData() {
        mTestModel.getData(new BaseModelCallBack() {
            @Override
            public void onResponse(String response) {
                mView.updateUI(response);
            }

            @Override
            public void onFailure(Throwable t) {
                mView.updateFail(t);
            }
        });
    }
}
