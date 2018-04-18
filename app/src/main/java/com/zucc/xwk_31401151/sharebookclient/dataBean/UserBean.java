package com.zucc.xwk_31401151.sharebookclient.dataBean;

/**
 * Created by Administrator on 2018/4/8.
 */

public class UserBean {

        public String user;

        public String token;

        public String uid;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "user='" + user + '\'' +
                ", token='" + token + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}

