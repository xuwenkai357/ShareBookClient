package com.zucc.xwk_31401151.sharebookclient.api.presenter;

/**
 * Created by Administrator on 2018/5/22.
 */

public interface IMessageListPresenter {
    /**
     * 获取用户站内信
     */

    void loadMessages(String userId);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
