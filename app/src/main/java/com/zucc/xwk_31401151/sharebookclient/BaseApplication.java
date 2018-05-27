package com.zucc.xwk_31401151.sharebookclient;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.zucc.xwk_31401151.sharebookclient.ui.activity.Base2Activity;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.BaseActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by hymanme on 2016/1/5 0005.
 * Application
 */
public class BaseApplication extends Application {
    public final static String TAG = "BaseApplication";
    public final static boolean DEBUG = true;
    private static BaseApplication application;
    private static int mainTid;
    /**
     * Activity集合，来管理所有的Activity
     */
    private static List<Base2Activity> activities;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activities = new LinkedList<>();
        application = this;
        mainTid = android.os.Process.myTid();
    }

    /**
     * 获取application
     *
     * @return
     */
    public static Context getApplication() {
        return application;
    }

    /**
     * 获取主线程ID
     *
     * @return
     */
    public static int getMainTid() {
        return mainTid;
    }

    /**
     * 添加一个Activity
     *
     * @param activity
     */
    public void addActivity(Base2Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束一个Activity
     *
     * @param activity
     */
    public void removeActivity(Base2Activity activity) {
        activities.remove(activity);
    }

    /**
     * 结束当前所有Activity
     */
    public static void clearActivities() {
        ListIterator<Base2Activity> iterator = activities.listIterator();
        Base2Activity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应运程序
     */
    public static void quiteApplication() {
        clearActivities();
        System.exit(0);
    }
}
