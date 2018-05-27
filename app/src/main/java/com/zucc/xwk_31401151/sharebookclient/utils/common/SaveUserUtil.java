package com.zucc.xwk_31401151.sharebookclient.utils.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.zucc.xwk_31401151.sharebookclient.bean.UserBean;


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

    public void saveUser(Context context,String user_id,String user,String token,String username,String phone){
        SharedPreferences sp = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        //Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_ID",user_id);
        editor.putString("USER",user);
        editor.putString("TOKEN",token);
        editor.putString("USER_NAME",username);
        editor.putString("PHONE",phone);
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
        userBean.setUser_id(sp.getString("USER_ID",""));
        userBean.setUser(sp.getString("USER",""));
        userBean.setToken(sp.getString("TOKEN",""));
        userBean.setUser_name(sp.getString("USER_NAME",""));
        userBean.setPhone(sp.getString("PHONE",""));

        return userBean;
    }

    public void saveEdt(Context context,String edt){
        SharedPreferences sp = context.getSharedPreferences("edt",Context.MODE_PRIVATE);
        //Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("EDT",edt);
        editor.commit();
    }

    public String getEdt(Context context){
        SharedPreferences sp = context.getSharedPreferences("edt",Context.MODE_PRIVATE);
        String edt = sp.getString("EDT","");

        return edt;
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
