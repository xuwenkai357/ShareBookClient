package com.zucc.xwk_31401151.sharebookclient.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class ShareListResponse {
    List<ShareResponse> bookListModels;

    public List<ShareResponse> getBookListModels() {
        return bookListModels;
    }

    public void setBookListModels(List<ShareResponse> bookListModels) {
        this.bookListModels = bookListModels;
    }
}
