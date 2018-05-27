package com.zucc.xwk_31401151.sharebookclient.api.presenter.impl;

import com.zucc.xwk_31401151.sharebookclient.BaseApplication;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.model.IShowDynamicListModel;
import com.zucc.xwk_31401151.sharebookclient.api.model.impl.ShowDynamicListModelImpl;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.IShowDynamicListPresenter;
import com.zucc.xwk_31401151.sharebookclient.api.view.IShowDynamicListView;
import com.zucc.xwk_31401151.sharebookclient.bean.ShowDynamicListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;
import com.zucc.xwk_31401151.sharebookclient.utils.common.NetworkUtils;

/**
 * Created by Administrator on 2018/5/25.
 */

public class ShowDynamicListPresenterImpl implements IShowDynamicListPresenter,ApiCompleteListener {

    private IShowDynamicListView mShowDynamicListView;
    private IShowDynamicListModel mShowDynamicListModel;

    public ShowDynamicListPresenterImpl(IShowDynamicListView view) {
        mShowDynamicListView = view;
        mShowDynamicListModel = new ShowDynamicListModelImpl();
    }

    /**
     * 加载数据
     */

    @Override
    public void loadShowDynamic(int start, int count) {

        if (!NetworkUtils.isConnected(BaseApplication.getApplication())) {
            mShowDynamicListView.showMessage(BaseApplication.getApplication().getString(R.string.poor_network));
            mShowDynamicListView.hideProgress();
//            return;
        }
        mShowDynamicListView.showProgress();
        mShowDynamicListModel.loadShowDynamicList( start, count, this);
    }


    @Override
    public void cancelLoading() {
        mShowDynamicListModel.cancelLoading();
    }

    @Override
    public void onComplected(Object result) {
        if (result instanceof ShowDynamicListResponse) {
            int index = Integer.parseInt(((ShowDynamicListResponse) result).getStart());
            if (index == 0) {
                mShowDynamicListView.refreshData(result);
            } else {
                mShowDynamicListView.addData(result);
            }
            mShowDynamicListView.hideProgress();
        }
    }

    @Override
    public void onFailed(BaseResponse msg) {
        mShowDynamicListView.hideProgress();
        if (msg == null) {
            return;
        }
        mShowDynamicListView.showMessage(msg.getMsg());
    }
}
