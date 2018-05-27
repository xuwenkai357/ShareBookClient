package com.zucc.xwk_31401151.sharebookclient.api.common.service;



import com.zucc.xwk_31401151.sharebookclient.bean.MyborrowListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/23.
 */

public interface IMyBorrowListService {
    @GET("/api/borrowlist/all")
    Observable<Response<MyborrowListResponse>> getMyborrowList(@Query("userid") int userid);
}
