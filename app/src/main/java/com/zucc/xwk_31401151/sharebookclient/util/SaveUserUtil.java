package com.zucc.xwk_31401151.sharebookclient.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zucc.xwk_31401151.sharebookclient.dataBean.UserBean;

/**
 * 保存用户信息的管理类
 * Created by Administrator on 2018/4/17.
 */



public class SaveUserUtil {
    private static SaveUserUtil instance;

    private SaveUserUtil(){

    }

    public static SaveUserUtil getInstance(){
        if (instance == null){
            instance = new SaveUserUtil();
        }
        return instance;
    }

    /**
     * 保存自动登录的用户信息
     */

    public void saveUser(Context context,String user_id,String username,String token){
        SharedPreferences sp = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        //Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_ID",user_id);
        editor.putString("USER_NAME",username);
        editor.putString("TOKEN",token);
        editor.commit();
    }

    /**
     * 获取用户信息model
     *
     * @param context
     */

    public UserBean getUser(Context context){
        SharedPreferences sp = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        UserBean userBean = new UserBean();
        userBean.setUid(sp.getString("USER_ID",""));
        userBean.setUser(sp.getString("USER_NAME",""));
        userBean.setToken(sp.getString("TOKEN",""));

        return userBean;
    }


    /**
     * User中是否有数据
     */

    public boolean hasUser(Context context){
        UserBean userBean = getUser(context);
        if (userBean!= null){
            if(!TextUtils.isEmpty(userBean.getUser())){
                return true;
            }
            else
                return false;
        }

        return false;
    }

}
