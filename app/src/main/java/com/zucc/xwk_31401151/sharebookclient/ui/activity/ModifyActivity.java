package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.zucc.xwk_31401151.sharebookclient.bean.UserBean;
import com.zucc.xwk_31401151.sharebookclient.model.LoginModel;
import com.zucc.xwk_31401151.sharebookclient.utils.common.OkHttpUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ModifyActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.register_email)
    AutoCompleteTextView registerEmail;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_new_password)
    EditText registerNewPassword;
    @BindView(R.id.password_again)
    EditText passwordAgain;
    @BindView(R.id.modify_in_button)
    Button modifyInButton;

    public final static String PHONE_PATTERN="^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9]))\\d{8}$";

    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);
        toolbar.setTitle("修改个人信息");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        UserBean userBean = SaveUserUtil.getInstance().getUser(ModifyActivity.this);
        String user_name = userBean.getUser_name();
        String phone_st = userBean.getPhone();
        userid=userBean.getUser_id();
        registerEmail.setText(user_name);
        phone.setText(phone_st);




    }

    @OnClick(R.id.modify_in_button)
    public void onViewClicked() {

        if (registerEmail.getText().toString().isEmpty()){
            Toast.makeText(ModifyActivity.this,"请输入用户昵称",Toast.LENGTH_LONG).show();
        }else if (!phone.getText().toString().matches(PHONE_PATTERN)){
            Toast.makeText(ModifyActivity.this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
        }
        else if (registerPassword.getText().toString().isEmpty()){
            Toast.makeText(ModifyActivity.this,"请输入现在使用的密码",Toast.LENGTH_LONG).show();
        } else if ((!registerNewPassword.getText().toString().isEmpty())&&registerNewPassword.getText().length() < 6) {
            Toast.makeText(ModifyActivity.this, "密码太短", Toast.LENGTH_SHORT).show();
        } else if ((!registerNewPassword.getText().toString().isEmpty())&&!registerNewPassword.getText().toString().equals(passwordAgain.getText().toString())) {
            System.out.printf(registerNewPassword.getText().toString());
            System.out.printf(passwordAgain.getText().toString());
            Toast.makeText(ModifyActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            modify();
        }

    }

    private void modify() {
        String uri ;
        uri = AppConstant.getUrl() + "user/modify";
        System.out.printf(uri);
        Map<String,String> map = new HashMap<>();
        map.put("userid",userid);
        map.put("user_name",registerEmail.getText().toString());
        map.put("phone",phone.getText().toString());
        map.put("password",registerPassword.getText().toString());
        map.put("newpassword",registerNewPassword.getText().toString());

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
                                          String user_name = loginModel.data.user_name;
                                          String phone = loginModel.data.phone;

                                          System.out.println(userid+user+token);

                                          //将登陆信息存在SharePrefences里
                                          SaveUserUtil.getInstance().saveUser(ModifyActivity.this,userid,user,token,user_name,phone);


                                          Toast.makeText(ModifyActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                          Intent intent1 = new Intent(ModifyActivity.this,MainActivity.class);
                                          startActivity(intent1);
                                          finish();
                                      }
                                      else if (loginModel != null){

                                          Toast.makeText(ModifyActivity.this, loginModel.desc, Toast.LENGTH_SHORT).show();

                                      }
                                      else{

                                          Toast.makeText(ModifyActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                                      }


                                  }
                              }

                );


            }


        },map);

    }
}
