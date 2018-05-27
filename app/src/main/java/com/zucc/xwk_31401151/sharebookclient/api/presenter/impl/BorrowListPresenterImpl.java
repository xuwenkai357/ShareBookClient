package com.zucc.xwk_31401151.sharebookclient.api.presenter.impl;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.model.IBorrowListModel;
import com.zucc.xwk_31401151.sharebookclient.api.model.impl.BorrowListModelImpl;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.IBorrowListPresenter;
import com.zucc.xwk_31401151.sharebookclient.api.view.IBorrowListView;
import com.zucc.xwk_31401151.sharebookclient.bean.ShareListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;
import com.zucc.xwk_31401151.sharebookclient.utils.common.NetworkUtils;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

/**
 * Created by Administrator on 2018/5/24.
 */

public class BorrowListPresenterImpl implements IBorrowListPresenter,ApiCompleteListener {


    private IBorrowListView mBorrowListView;
    private IBorrowListModel mBorrowListModel;


    public BorrowListPresenterImpl(IBorrowListView view){
        mBorrowListView = view;
        mBorrowListModel= new BorrowListModelImpl();

    }




    @Override
    public void loadBorrowList(int book_info_id,String userid) {

        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mBorrowListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mBorrowListView.hideProgress();
        }
        mBorrowListView.showProgress();
        mBorrowListModel.loadBorrowList(book_info_id,userid,this);

    }

    @Override
    public void cancelLoading() {
        mBorrowListModel.cancelLoading();
    }

    @Override
    public void onComplected(Object result) {
        if (result instanceof ShareListResponse){
            mBorrowListView.updateView(result);
            mBorrowListView.hideProgress();
        }

    }

    @Override
    public void onFailed(BaseResponse msg) {
        mBorrowListView.hideProgress();
        if (msg==null){
            return;
        }
        mBorrowListView.showMessage(msg.getMsg());


    }
}
