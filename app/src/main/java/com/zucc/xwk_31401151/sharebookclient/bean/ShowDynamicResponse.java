package com.zucc.xwk_31401151.sharebookclient.bean;

import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookReviewResponse;

/**
 * Created by Administrator on 2018/5/25.
 */

public class ShowDynamicResponse {
    BookReviewResponse bookReviewResponse;
    BookInfoResponse book;

    public BookReviewResponse getBookReviewResponse() {
        return bookReviewResponse;
    }

    public void setBookReviewResponse(BookReviewResponse bookReviewResponse) {
        this.bookReviewResponse = bookReviewResponse;
    }

    public BookInfoResponse getBook() {
        return book;
    }

    public void setBook(BookInfoResponse book) {
        this.book = book;
    }
}
