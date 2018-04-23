package com.zucc.xwk_31401151.sharebookclient.api.model.impl;

import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.api.ApiCompleteListener;
import com.zucc.xwk_31401151.sharebookclient.api.common.ServiceFactory;
import com.zucc.xwk_31401151.sharebookclient.api.common.service.IBookReviewsService;
import com.zucc.xwk_31401151.sharebookclient.api.common.service.IBookSeriesService;
import com.zucc.xwk_31401151.sharebookclient.api.model.IBookDetailModel;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BaseResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookReviewsListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookSeriesListResponse;

import java.net.UnknownHostException;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/23 0023
 * Description: 图书详情页数据模型
 */
public class BookDetailModelImpl implements IBookDetailModel {

    @Override
    public void loadReviewsList(String bookId, int start, int count, String fields, final ApiCompleteListener listener) {
        IBookReviewsService iBookReviewsService = ServiceFactory.createService(AppConstant.getDoubanUrl(), IBookReviewsService.class);
        iBookReviewsService.getBookReviews(bookId, start, count, fields)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<BookReviewsListResponse>>() {
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
                    public void onNext(Response<BookReviewsListResponse> bookReviewsResponse) {
                        if (bookReviewsResponse.isSuccessful()) {
                            listener.onComplected(bookReviewsResponse.body());
                        } else {
                            listener.onFailed(new BaseResponse(bookReviewsResponse.code(), bookReviewsResponse.message()));
                        }

                    }
                });
    }

    @Override
    public void loadSeriesList(String SeriesId, int start, int count, String fields, final ApiCompleteListener listener) {
        IBookSeriesService iBookSeriesService = ServiceFactory.createService(AppConstant.getDoubanUrl(), IBookSeriesService.class);
        iBookSeriesService.getBookSeries(SeriesId, start, count, fields)
                .subscribeOn(Schedulers.newThread())    //请求在新的线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<BookSeriesListResponse>>() {
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
                    public void onNext(Response<BookSeriesListResponse> bookSeriesResponse) {
                        if (bookSeriesResponse.isSuccessful()) {
                            listener.onComplected(bookSeriesResponse.body());
                        } else {
                            listener.onFailed(new BaseResponse(bookSeriesResponse.code(), bookSeriesResponse.message()));
                        }

                    }
                });
    }

    @Override
    public void cancelLoading() {

    }
}
