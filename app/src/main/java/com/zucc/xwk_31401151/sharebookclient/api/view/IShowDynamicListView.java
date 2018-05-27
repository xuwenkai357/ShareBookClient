package com.zucc.xwk_31401151.sharebookclient.api.view;

/**
 * Created by Administrator on 2018/5/25.
 */

public interface IShowDynamicListView {
    void showMessage(String msg);

    void showProgress();

    void hideProgress();

    void refreshData(Object result);

    void addData(Object result);
}
