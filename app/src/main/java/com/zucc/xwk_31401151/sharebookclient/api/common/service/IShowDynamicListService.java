package com.zucc.xwk_31401151.sharebookclient.api.common.service;

import com.zucc.xwk_31401151.sharebookclient.bean.ShowDynamicListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/25.
 */

public interface IShowDynamicListService {

    @GET("/api/dynamic/loadall")
    Observable<Response<ShowDynamicListResponse>> getMyshareList(@Query("start") int start, @Query("count") int count);


}
