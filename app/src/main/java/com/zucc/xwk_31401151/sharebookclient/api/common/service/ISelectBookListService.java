package com.zucc.xwk_31401151.sharebookclient.api.common.service;

import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/25.
 */

public interface ISelectBookListService {

    @GET("/api/bookinfo/select")
    Observable<Response<BookListResponse>> getSelectBookList(@Query("userid") String userid);

}
