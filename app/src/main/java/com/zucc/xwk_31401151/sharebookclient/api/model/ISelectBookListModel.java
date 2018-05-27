package com.zucc.xwk_31401151.sharebookclient.api.model;

import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;

/**
 * Created by Administrator on 2018/5/25.
 */

public interface ISelectBookListModel {

    void loadSelectBookList(String userid,ApiCompleteListener listener);
    /**
     * 取消加载数据
     */
    void cancelLoading();
}
