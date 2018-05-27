package com.zucc.xwk_31401151.sharebookclient.api.presenter;

import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface IBorrowListPresenter {

    /**
     * 获取我的分享
     */
    void loadBorrowList(int book_info_id,String userid);

    /**
     * 取消加载数据
     */
    void cancelLoading();

}
