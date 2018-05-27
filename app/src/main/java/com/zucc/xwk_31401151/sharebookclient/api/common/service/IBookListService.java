package com.zucc.xwk_31401151.sharebookclient.api.common.service;

import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/5
 * Description:
 */
public interface IBookListService {
    @GET("/api/bookinfo/searchlocal")
    Observable<Response<BookListResponse>> getBookList(@Query("keyword") String keyword, @Query("start") int start, @Query("count") int count);
}
