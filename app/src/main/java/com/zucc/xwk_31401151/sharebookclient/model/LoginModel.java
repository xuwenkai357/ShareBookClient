package com.zucc.xwk_31401151.sharebookclient.model;

import com.google.gson.annotations.SerializedName;
import com.zucc.xwk_31401151.sharebookclient.dataBean.UserBean;


/**
 * Created by Administrator on 2018/4/9.
 */

public class LoginModel {


    public int status;

    public String desc = "";

    public UserBean data;

    public static class UserBean {
        public String user;

        public String token;

        public String uid;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "status=" + status +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}