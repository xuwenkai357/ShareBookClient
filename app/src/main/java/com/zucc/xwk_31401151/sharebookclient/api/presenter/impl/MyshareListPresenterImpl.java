package com.zucc.xwk_31401151.sharebookclient.api.presenter.impl;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.model.IMyborrowListModel;
import com.zucc.xwk_31401151.sharebookclient.api.model.IMyshareListModel;
import com.zucc.xwk_31401151.sharebookclient.api.model.impl.MyborrowListModelImpl;
import com.zucc.xwk_31401151.sharebookclient.api.model.impl.MyshareListModelImpl;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.IMyshareListPresenter;
import com.zucc.xwk_31401151.sharebookclient.api.view.IMyborrowListView;
import com.zucc.xwk_31401151.sharebookclient.api.view.IMyshareListView;
import com.zucc.xwk_31401151.sharebookclient.bean.ShareListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;
import com.zucc.xwk_31401151.sharebookclient.utils.common.NetworkUtils;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

/**
 * Created by Administrator on 2018/5/24.
 */

public class MyshareListPresenterImpl implements IMyshareListPresenter,ApiCompleteListener {


    private IMyshareListView mMyshareListView;
    private IMyshareListModel mMyshareListModel;

    public MyshareListPresenterImpl(IMyshareListView view){
        mMyshareListView = view;
        mMyshareListModel= new MyshareListModelImpl();

    }


    @Override
    public void loadMyShareList(int userId) {

        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mMyshareListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mMyshareListView.hideProgress();
        }
        mMyshareListView.showProgress();
        mMyshareListModel.loadMyShareList(userId,this);

    }

    @Override
    public void cancelLoading() {
        mMyshareListModel.cancelLoading();

    }

    @Override
    public void onComplected(Object result) {
        if (result instanceof ShareListResponse){
            mMyshareListView.updateView(result);
            mMyshareListView.hideProgress();
        }
    }

    @Override
    public void onFailed(BaseResponse msg) {
        mMyshareListView.hideProgress();
        if (msg==null){
            return;
        }
        mMyshareListView.showMessage(msg.getMsg());

    }
}
