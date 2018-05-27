package com.zucc.xwk_31401151.sharebookclient.api.presenter.impl;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.model.ISelectBookListModel;
import com.zucc.xwk_31401151.sharebookclient.api.model.impl.SelectBookListModelImpl;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.ISelectBookListPresenter;
import com.zucc.xwk_31401151.sharebookclient.api.view.ISelectBookListView;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookListResponse;
import com.zucc.xwk_31401151.sharebookclient.utils.common.NetworkUtils;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

/**
 * Created by Administrator on 2018/5/25.
 */

public class SelectBookListPresenterImpl implements ISelectBookListPresenter,ApiCompleteListener  {

    private ISelectBookListView mSelectBookListView;
    private ISelectBookListModel mSelectBookListModel;

    public SelectBookListPresenterImpl(ISelectBookListView view){
        mSelectBookListView = view;
        mSelectBookListModel= new SelectBookListModelImpl();

    }


    @Override
    public void loadSelectBookList(String userid) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mSelectBookListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mSelectBookListView.hideProgress();
        }
        mSelectBookListView.showProgress();
        mSelectBookListModel.loadSelectBookList(userid,this);

    }



    @Override
    public void cancelLoading() {
        mSelectBookListModel.cancelLoading();
    }

    @Override
    public void onComplected(Object result) {
        if (result instanceof BookListResponse) {
            mSelectBookListView.updateView(result);
            mSelectBookListView.hideProgress();
        }
    }


    @Override
    public void onFailed(BaseResponse msg) {
        mSelectBookListView.hideProgress();
        if (msg==null){
            return;
        }
        mSelectBookListView.showMessage(msg.getMsg());
    }
}
