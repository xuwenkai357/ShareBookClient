package com.zucc.xwk_31401151.sharebookclient.api.common.service;

import com.zucc.xwk_31401151.sharebookclient.bean.MessageListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/22.
 */

public interface IMessageListService {
    @GET("/api/message/search")
    Observable<Response<MessageListResponse>> getMessageList(@Query("userid") String userid);
}
