package com.zucc.xwk_31401151.sharebookclient.api.common.service;

import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookReviewsListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/2
 * Description:
 */
public interface IBookReviewsService {
    @GET("/api/dynamic/search")
    Observable<Response<BookReviewsListResponse>> getBookReviews(@Query("bookinfoid") String bookinfoid, @Query("start") int start, @Query("count") int count);
//    @GET("book/{bookId}/reviews")
//    Observable<Response<BookReviewsListResponse>> getBookReviews(@Path("bookId") String bookId, @Query("start") int start, @Query("count") int count, @Query("fields") String fields);

}
