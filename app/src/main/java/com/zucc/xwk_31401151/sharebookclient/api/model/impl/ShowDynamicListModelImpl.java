package com.zucc.xwk_31401151.sharebookclient.api.model.impl;

import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.common.ServiceFactory;
import com.zucc.xwk_31401151.sharebookclient.api.common.service.IShowDynamicListService;
import com.zucc.xwk_31401151.sharebookclient.api.model.IShowDynamicListModel;
import com.zucc.xwk_31401151.sharebookclient.bean.ShowDynamicListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;

import java.net.UnknownHostException;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/25.
 */

public class ShowDynamicListModelImpl implements IShowDynamicListModel{


    @Override
    public void loadShowDynamicList(int start, int count, ApiCompleteListener listener) {
        IShowDynamicListService showDynamicListService = ServiceFactory.createService(AppConstant.getUrl(),IShowDynamicListService.class);
        showDynamicListService.getMyshareList(start,count)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<ShowDynamicListResponse>>() {
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
                    public void onNext(Response<ShowDynamicListResponse> showDynamicListResponse) {
                        if (showDynamicListResponse.isSuccessful()){
                            listener.onComplected(showDynamicListResponse.body());
                        }
                        else {
                            listener.onFailed(new BaseResponse(showDynamicListResponse.code(),showDynamicListResponse.message()));
                        }

                    }
                });
    }

    @Override
    public void cancelLoading() {

    }
}
