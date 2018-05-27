package com.zucc.xwk_31401151.sharebookclient.api.presenter.impl;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.model.IMyborrowListModel;
import com.zucc.xwk_31401151.sharebookclient.api.model.impl.MyborrowListModelImpl;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.IMyborrowListPresenter;
import com.zucc.xwk_31401151.sharebookclient.api.view.IMyborrowListView;
import com.zucc.xwk_31401151.sharebookclient.bean.MyborrowListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;
import com.zucc.xwk_31401151.sharebookclient.utils.common.NetworkUtils;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

/**
 * Created by Administrator on 2018/5/23.
 */

public class MyborrowListPresenterImpl implements IMyborrowListPresenter,ApiCompleteListener {

    private IMyborrowListView mMyborrowListView;
    private IMyborrowListModel mMyborrowListModel;

    public MyborrowListPresenterImpl(IMyborrowListView view){
        mMyborrowListView = view;
        mMyborrowListModel= new MyborrowListModelImpl();

    }



    @Override
    public void loadMyBorrowList(int userId) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mMyborrowListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mMyborrowListView.hideProgress();
        }
        mMyborrowListView.showProgress();
        mMyborrowListModel.loadMyborrowList(userId,this);



    }


    @Override
    public void cancelLoading() {

        mMyborrowListModel.cancelLoading();

    }

    @Override
    public void onComplected(Object result) {

        if (result instanceof MyborrowListResponse){
            mMyborrowListView.updateView(result);
            mMyborrowListView.hideProgress();
        }

    }

    @Override
    public void onFailed(BaseResponse msg) {
        mMyborrowListView.hideProgress();
        if (msg==null){
            return;
        }
        mMyborrowListView.showMessage(msg.getMsg());

    }
}
