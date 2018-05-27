package com.zucc.xwk_31401151.sharebookclient.utils.common;

/**
 * Created by Administrator on 2018/4/17.
 */

public class TextUtils {
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}
