package com.zucc.xwk_31401151.sharebookclient.api.presenter;

/**
 * Created by Administrator on 2018/5/23.
 */

public interface IMyborrowListPresenter {
    /**
     * 获取我的借阅
     */

    void loadMyBorrowList(int userId);



    /**
     * 取消加载数据
     */
    void cancelLoading();
}
