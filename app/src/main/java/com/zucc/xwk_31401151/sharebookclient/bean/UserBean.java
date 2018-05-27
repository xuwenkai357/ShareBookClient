package com.zucc.xwk_31401151.sharebookclient.bean;

/**
 * Created by Administrator on 2018/4/8.
 */

public class UserBean {

        public String user;

        public String token;

        public String user_id;

        public String user_name;

        public String phone;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "user='" + user + '\'' +
                ", token='" + token + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}


