package com.zucc.xwk_31401151.sharebookclient.api.model.impl;

import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.common.ServiceFactory;
import com.zucc.xwk_31401151.sharebookclient.api.common.service.IBorrowListService;
import com.zucc.xwk_31401151.sharebookclient.api.model.IBorrowListModel;
import com.zucc.xwk_31401151.sharebookclient.bean.ShareListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;

import java.net.UnknownHostException;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/24.
 */

public class BorrowListModelImpl implements IBorrowListModel {
    @Override
    public void loadBorrowList(int book_info_id,String userid, ApiCompleteListener listener) {
        IBorrowListService borrowListService = ServiceFactory.createService(AppConstant.getUrl(),IBorrowListService.class);
        borrowListService.getBorrowList(book_info_id,userid)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<ShareListResponse>>() {
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
                    public void onNext(Response<ShareListResponse> shareListResponse) {
                        if (shareListResponse.isSuccessful()){
                            listener.onComplected(shareListResponse.body());
                        }
                        else {
                            listener.onFailed(new BaseResponse(shareListResponse.code(),shareListResponse.message()));
                        }

                    }
                });
    }

    @Override
    public void cancelLoading() {

    }
}
