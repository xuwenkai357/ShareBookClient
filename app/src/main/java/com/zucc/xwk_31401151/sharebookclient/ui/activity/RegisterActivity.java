package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.model.LoginModel;
import com.zucc.xwk_31401151.sharebookclient.util.OkHttpUtil;
import com.zucc.xwk_31401151.sharebookclient.util.SaveUserUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity" ;
    @BindView(R.id.register_email)
    AutoCompleteTextView registerEmail;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.password_again)
    EditText passwordAgain;
    @BindView(R.id.email_register_in_button)
    Button emailRegisterInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.email_register_in_button)
    public void onViewClicked() {
        register();
    }

    private void register() {
        String url ;
        url = AppConstant.getUrl() + "user/register";
        System.out.printf(url);
        Map<String,String> map = new HashMap<>();
        if (registerPassword.getText().length()<6){
            Toast.makeText(RegisterActivity.this,"密码太短", Toast.LENGTH_SHORT).show();
        }
        else if (!registerPassword.getText().toString().equals(passwordAgain.getText().toString())){
            System.out.printf(registerPassword.getText().toString());
            System.out.printf(passwordAgain.getText().toString());
            Toast.makeText(RegisterActivity.this,"密码不一致", Toast.LENGTH_SHORT).show();
        }
        else {
            map.put("name",registerEmail.getText().toString());
            map.put("password",registerPassword.getText().toString());

            OkHttpUtil.post(url, new okhttp3.Callback(){

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG,"OnFailure:",e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
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
                                              SaveUserUtil.getInstance().saveUser(RegisterActivity.this,loginModel.data.uid,loginModel.data.user,loginModel.data.token);


                                              Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                              Intent intent1 = new Intent(RegisterActivity.this,MainActivity.class);
                                              startActivity(intent1);
                                              finish();
                                          }
                                          else if (loginModel != null){

                                              Toast.makeText(RegisterActivity.this, loginModel.desc, Toast.LENGTH_SHORT).show();
                                          }
                                          else{
                                              Toast.makeText(RegisterActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                                          }


                                      }
                                  }

                    );


                }


            },map);
        }

    }
}
