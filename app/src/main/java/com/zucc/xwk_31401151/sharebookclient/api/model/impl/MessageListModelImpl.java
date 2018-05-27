package com.zucc.xwk_31401151.sharebookclient.api.model.impl;

import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.common.ServiceFactory;
import com.zucc.xwk_31401151.sharebookclient.api.common.service.IMessageListService;
import com.zucc.xwk_31401151.sharebookclient.api.model.IMessageListModel;
import com.zucc.xwk_31401151.sharebookclient.bean.MessageListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;

import java.net.UnknownHostException;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/22.
 */

public class MessageListModelImpl implements IMessageListModel {
    @Override
    public void loadMessageList(String userId, ApiCompleteListener listener) {
        IMessageListService iMessageListService = ServiceFactory.createService(AppConstant.getUrl(),IMessageListService.class);
        iMessageListService.getMessageList(userId)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<MessageListResponse>>() {
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
                    public void onNext(Response<MessageListResponse> messageListResponse) {
                        if (messageListResponse.isSuccessful()){
                            listener.onComplected(messageListResponse.body());
                        }
                        else {
                            listener.onFailed(new BaseResponse(messageListResponse.code(),messageListResponse.message()));
                        }
                    }
                });

    }

    @Override
    public void cancelLoading() {

    }
}
