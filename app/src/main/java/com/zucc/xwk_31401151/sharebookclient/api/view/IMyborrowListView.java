package com.zucc.xwk_31401151.sharebookclient.api.view;

/**
 * Created by Administrator on 2018/5/23.
 */

public interface IMyborrowListView {
    void showMessage(String msg);

    void showProgress();

    void hideProgress();

    void updateView(Object result);
}
