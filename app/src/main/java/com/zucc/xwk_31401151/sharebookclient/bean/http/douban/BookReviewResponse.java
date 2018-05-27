package com.zucc.xwk_31401151.sharebookclient.bean.http.douban;


import com.zucc.xwk_31401151.sharebookclient.bean.UserBean;
import com.zucc.xwk_31401151.sharebookclient.bean.UserRatingBean;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/23 0023
 * Description:
 */
public class BookReviewResponse {
    String dynamic_id;
    String book_info_id;
    String user_id;
    String body;
    String create_time;
    String title;
    String author;
    String image;
    String user_name;
    String photo_url;


    public String getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(String dynamic_id) {
        this.dynamic_id = dynamic_id;
    }

    public String getBook_info_id() {
        return book_info_id;
    }

    public void setBook_info_id(String book_info_id) {
        this.book_info_id = book_info_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
