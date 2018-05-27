package com.zucc.xwk_31401151.sharebookclient.api.common.service;

import com.zucc.xwk_31401151.sharebookclient.bean.ShareListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface IBorrowListService {
    @GET("/api/booklist/all")
    Observable<Response<ShareListResponse>> getBorrowList(@Query("book_info_id") int book_info_id,@Query("userid") String userid);
}
