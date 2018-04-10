package com.zucc.xwk_31401151.sharebookclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/4/9.
 */

public class BaseResModel<T> {
    public T data;

    public static final int REQUEST_SUCCESS = 1000;
    @SerializedName("ecode")
    public int status;
    @SerializedName("emsg")
    public String desc = "";


    @Override
    public String toString() {
        return "BaseResModel{" + "status=" + status + ", desc='" + desc + '\'' + '}';
    }




}
