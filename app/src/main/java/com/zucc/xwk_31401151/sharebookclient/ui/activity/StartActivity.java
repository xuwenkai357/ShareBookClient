package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.model.LoginModel;
import com.zucc.xwk_31401151.sharebookclient.utils.common.OkHttpUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class StartActivity extends BaseActivity {

    private static final int GO_HOME = 0;
    private static final int GO_LOGIN = 1;
    String user_id;
    String token_st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        user_id = SaveUserUtil.getInstance().getUser(StartActivity.this).getUser_id();
        token_st = SaveUserUtil.getInstance().getUser(StartActivity.this).getToken();

        if (SaveUserUtil.getInstance().hasUser(this)){
            check();
        }
        else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN,2000);
        }
    }

    private void check() {
        String uri ;
        uri = AppConstant.getUrl() + "user/check";
        System.out.printf(uri);
        Map<String,String> map = new HashMap<>();
        map.put("userid",user_id);
        OkHttpUtil.post(uri, new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"OnFailure:",e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG,responseData);
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<LoginModel>() {}.getType();
                final LoginModel loginModel = gson.fromJson(responseData,type);
                if (loginModel!=null) {
                    System.out.println(loginModel.desc + loginModel.status);
                }
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      if (loginModel != null && loginModel.status == 1000 && loginModel.data != null){

                                          String userid = loginModel.data.user_id;
                                          String user = loginModel.data.user;
                                          String token = loginModel.data.token;

                                          System.out.println(userid+user+token);

                                          if (token.equals(token_st)){
                                              mHandler.sendEmptyMessageDelayed(GO_HOME,2000);
                                          }
                                          else {
                                              mHandler.sendEmptyMessageDelayed(GO_LOGIN,2000);
                                          }

                                      }
                                      else if (loginModel != null){
                                          mHandler.sendEmptyMessageDelayed(GO_LOGIN,2000);
                                      }
                                      else{
                                          mHandler.sendEmptyMessageDelayed(GO_LOGIN,2000);
                                      }
                                  }
                              }
                );
            }
        },map);
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
