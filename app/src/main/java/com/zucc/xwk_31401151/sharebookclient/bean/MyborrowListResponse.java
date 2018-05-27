package com.zucc.xwk_31401151.sharebookclient.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class MyborrowListResponse {
    List<MyborrowResponse> myBorrowListModels;

    public List<MyborrowResponse> getMyBorrowListModels() {
        return myBorrowListModels;
    }

    public void setMyBorrowListModels(List<MyborrowResponse> myBorrowListModels) {
        this.myBorrowListModels = myBorrowListModels;
    }
}
