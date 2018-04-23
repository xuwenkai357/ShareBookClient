package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.util.SaveUserUtil;

public class StartActivity extends BaseActivity {

    private static final int GO_HOME = 0;
    private static final int GO_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (SaveUserUtil.getInstance().hasUser(this)){
            mHandler.sendEmptyMessageDelayed(GO_HOME,2000);
        }
        else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN,2000);
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_HOME:
                    //todo
                    Intent intent = new Intent(StartActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GO_LOGIN:
                    Intent intent2 = new Intent(StartActivity.this,LoginActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        }
    };




}
