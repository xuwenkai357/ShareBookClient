package com.zucc.xwk_31401151.sharebookclient.api.presenter;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/5
 * Description:
 */
public interface IBookListPresenter {
    void loadBooks(String q, String tag, int start, int count, String fields);


    void cancelLoading();
}
