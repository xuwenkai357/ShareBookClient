package com.zucc.xwk_31401151.sharebookclient.api.presenter.impl;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.model.IMessageListModel;
import com.zucc.xwk_31401151.sharebookclient.api.model.impl.MessageListModelImpl;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.IMessageListPresenter;
import com.zucc.xwk_31401151.sharebookclient.api.view.IMessageListView;
import com.zucc.xwk_31401151.sharebookclient.bean.MessageListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;
import com.zucc.xwk_31401151.sharebookclient.utils.common.NetworkUtils;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

/**
 * Created by Administrator on 2018/5/22.
 */

public class MessageListPresenterImpl implements IMessageListPresenter,ApiCompleteListener {

    private IMessageListView mMessageListView;
    private IMessageListModel mMessageListModel;

    public MessageListPresenterImpl(IMessageListView view){
        mMessageListView = view;
        mMessageListModel = new MessageListModelImpl();

    }



    @Override
    public void loadMessages(String userId) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mMessageListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mMessageListView.hideProgress();
        }
        mMessageListView.showProgress();
        mMessageListModel.loadMessageList(userId,this);

    }

    @Override
    public void cancelLoading() {

        mMessageListModel.cancelLoading();

    }

    /**
     * 访问接口成功
     *
     * @param result 返回结果
     */

    @Override
    public void onComplected(Object result) {
        if (result instanceof MessageListResponse){
            mMessageListView.updateView(result);
            mMessageListView.hideProgress();
        }

    }

    /**
     * 取消正在加载的http请求
     */

    @Override
    public void onFailed(BaseResponse msg) {
        mMessageListView.hideProgress();
        if (msg == null) {
            return;
        }
        mMessageListView.showMessage(msg.getMsg());

    }
}
