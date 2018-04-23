package com.zucc.xwk_31401151.sharebookclient.api.presenter.impl;

import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.model.IHotSearchModel;
import com.zucc.xwk_31401151.sharebookclient.api.model.impl.HotSearchModelImpl;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.IHotSearchPresenter;
import com.zucc.xwk_31401151.sharebookclient.api.view.IHotSearchView;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/19
 * Description:
 */
public class HotSearchPresenterImpl implements IHotSearchPresenter, ApiCompleteListener {
    private IHotSearchView mHotSearchView;
    private IHotSearchModel mHotSearchModel;

    public HotSearchPresenterImpl(IHotSearchView view) {
        mHotSearchView = view;
        mHotSearchModel = new HotSearchModelImpl();
    }

    @Override
    public void loadHotSearch(int page) {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onComplected(Object result) {

    }

    @Override
    public void onFailed(BaseResponse msg) {

    }
}
