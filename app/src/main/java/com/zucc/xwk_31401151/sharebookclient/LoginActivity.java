package com.zucc.xwk_31401151.sharebookclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.xwk_31401151.sharebookclient.dataBean.UserBean;
import com.zucc.xwk_31401151.sharebookclient.util.*;
import com.zucc.xwk_31401151.sharebookclient.model.*;

import org.json.JSONException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {


    String TAG = "LoginActivity";

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private Button mEmailSignInButton;


    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        initView();
    }



    public void initView(){
        mEmailSignInButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_sign_in_button:
                mProgressView.setVisibility(View.VISIBLE);
                login();


        }


    }

    private void login() {
        String uri ;
        uri = AppConstant.getUri() + "user/login";
        System.out.printf(uri);
        Map<String,String> map = new HashMap<>();
        if (mPasswordView.getText().toString().length()<6){
            Toast.makeText(LoginActivity.this,"密码太短", Toast.LENGTH_SHORT).show();
        }

        else{
            map.put("name",mEmailView.getText().toString());
            map.put("password",mPasswordView.getText().toString());

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
                        System.out.printf(loginModel.desc + loginModel.status);
                    }
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          if (loginModel != null && loginModel.status == 1000 && loginModel.data != null){

                                              //将登陆信息存在SharePrefences里
                                              SaveUserUtil.getInstance().saveUser(LoginActivity.this,loginModel.data.uid,loginModel.data.user,loginModel.data.token);

                                              mProgressView.setVisibility(View.GONE);
                                              Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                              Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
                                              startActivity(intent1);
                                              finish();
                                          }
                                          else if (loginModel != null){
                                              mProgressView.setVisibility(View.GONE);
                                              Toast.makeText(LoginActivity.this, loginModel.desc, Toast.LENGTH_SHORT).show();
                                          }
                                          else{
                                              Toast.makeText(LoginActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                                          }


                                      }
                                  }

                    );


                }


            },map);
        }
    }

}


