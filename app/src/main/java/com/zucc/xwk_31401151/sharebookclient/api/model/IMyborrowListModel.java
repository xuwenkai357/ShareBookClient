package com.zucc.xwk_31401151.sharebookclient.api.model;

import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;

/**
 * Created by Administrator on 2018/5/23.
 */

public interface IMyborrowListModel {


    /**
     * 获取我的借阅
     */
    void loadMyborrowList(int userId, ApiCompleteListener listener);


    /**
     * 取消加载数据
     */
    void cancelLoading();



}
