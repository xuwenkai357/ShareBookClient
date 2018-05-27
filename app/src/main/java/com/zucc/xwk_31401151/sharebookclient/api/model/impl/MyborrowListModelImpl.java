package com.zucc.xwk_31401151.sharebookclient.api.model.impl;

import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.common.ServiceFactory;
import com.zucc.xwk_31401151.sharebookclient.api.common.service.IMyBorrowListService;
import com.zucc.xwk_31401151.sharebookclient.api.common.service.IMyshareListService;
import com.zucc.xwk_31401151.sharebookclient.api.model.IMyborrowListModel;
import com.zucc.xwk_31401151.sharebookclient.bean.MessageListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.MyborrowListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.ShareListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;

import java.net.UnknownHostException;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/23.
 */

public class MyborrowListModelImpl implements IMyborrowListModel {
    @Override
    public void loadMyborrowList(int userId, ApiCompleteListener listener) {
        IMyBorrowListService iMyBorrowListService = ServiceFactory.createService(AppConstant.getUrl(),IMyBorrowListService.class);
        iMyBorrowListService.getMyborrowList(userId)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<MyborrowListResponse>>() {
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
                    public void onNext(Response<MyborrowListResponse> myborrowListResponse) {
                        if (myborrowListResponse.isSuccessful()){
                            listener.onComplected(myborrowListResponse.body());
                        }
                        else {
                            listener.onFailed(new BaseResponse(myborrowListResponse.code(),myborrowListResponse.message()));
                        }

                    }
                });
    }


    @Override
    public void cancelLoading() {

    }
}
