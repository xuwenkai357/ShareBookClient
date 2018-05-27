package com.zucc.xwk_31401151.sharebookclient.api.presenter;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface IMyshareListPresenter {
    /**
     * 获取我的分享
     */
    void loadMyShareList(int userId);



    /**
     * 取消加载数据
     */
    void cancelLoading();
}
