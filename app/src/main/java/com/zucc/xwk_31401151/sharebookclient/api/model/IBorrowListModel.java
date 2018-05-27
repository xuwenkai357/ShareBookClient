package com.zucc.xwk_31401151.sharebookclient.api.model;

import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface IBorrowListModel {

    /**
     * 获取我的分享
     */
    void loadBorrowList(int book_info_id,String userid,ApiCompleteListener listener);

    /**
     * 取消加载数据
     */
    void cancelLoading();

}
