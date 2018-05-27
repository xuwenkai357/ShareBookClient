package com.zucc.xwk_31401151.sharebookclient.api.model.impl;

import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.common.ServiceFactory;
import com.zucc.xwk_31401151.sharebookclient.api.common.service.ISelectBookListService;
import com.zucc.xwk_31401151.sharebookclient.api.model.ISelectBookListModel;
import com.zucc.xwk_31401151.sharebookclient.api.model.IShowDynamicListModel;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookListResponse;

import java.net.UnknownHostException;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/25.
 */

public class SelectBookListModelImpl implements ISelectBookListModel {

    @Override
    public void loadSelectBookList(String userid, ApiCompleteListener listener) {
        ISelectBookListService iSelectBookListService = ServiceFactory.createService(AppConstant.getUrl(), ISelectBookListService.class);
        iSelectBookListService.getSelectBookList(userid)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<BookListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            listener.onFailed(null);
                            return;
                        }
                        listener.onFailed(new BaseResponse(404, e.getMessage()));
                    }

                    @Override
                    public void onNext(Response<BookListResponse> bookListResponse) {
                        if (bookListResponse.isSuccessful()) {
                            listener.onComplected(bookListResponse.body());
                        } else {
                            listener.onFailed(new BaseResponse(bookListResponse.code(), bookListResponse.message()));
                        }

                    }
                });
    }

    @Override
    public void cancelLoading() {

    }
}
