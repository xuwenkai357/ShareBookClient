package com.zucc.xwk_31401151.sharebookclient.api.presenter;

/**
 * Created by Administrator on 2018/5/25.
 */

public interface IShowDynamicListPresenter {
    void loadShowDynamic(int start, int count);

    void cancelLoading();
}
