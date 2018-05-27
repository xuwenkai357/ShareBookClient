package com.zucc.xwk_31401151.sharebookclient.api.model;

import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;

/**
 * Created by Administrator on 2018/5/22.
 */

public interface IMessageListModel {
    /**
     * 获取用户站内信
     */
    void loadMessageList(String userId, ApiCompleteListener listener);

    /**
     * 取消加载数据
     */
    void cancelLoading();



}
