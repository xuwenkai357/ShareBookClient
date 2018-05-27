package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.bean.MessageResponse;
import com.zucc.xwk_31401151.sharebookclient.model.BaseResModel;
import com.zucc.xwk_31401151.sharebookclient.model.LoginModel;
import com.zucc.xwk_31401151.sharebookclient.utils.common.OkHttpUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class MessageDetailActivity extends BaseActivity {

    private TextView tv_body;
    private MessageResponse messageResponse;
    private Button btn_change;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        initEvents();

    }

    protected void initEvents() {

        toolbar= (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);
        toolbar.setTitle("站内信详细内容");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_body = (TextView) findViewById(R.id.tv_message_body);
        btn_change = (Button) findViewById(R.id.btn_change);
        messageResponse = (MessageResponse) getIntent().getSerializableExtra(MessageResponse.serialVersionName);
        String message_id = messageResponse.getMessage_id();
        System.out.println(message_id);
        tv_body.setText(messageResponse.getBody());
        if (messageResponse.getStatus().equals("0")){
            btn_change.setVisibility(View.GONE);
        }
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MessageDetailActivity.this)
                        .title("借阅者信息确认")
                        .content("请确认借阅者的信息以及联系方式，确保您能将书籍借阅出去")
                        .iconRes(R.drawable.icon)
                        .positiveText("我已确认")
                        .negativeText("我再等等")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                    Toast.makeText(MessageDetailActivity.this, "我已确认" + message_id+"", Toast.LENGTH_LONG).show();
                                    updateMessage(message_id);

                                    finish();
                                } else if (which == DialogAction.NEGATIVE) {
                                    Toast.makeText(MessageDetailActivity.this, "我再等等", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .show();
            }
        });



    }

    private void updateMessage(String message_id){
        String uri ;
        uri = AppConstant.getUrl() + "message/update";
        System.out.println(uri);
        Map<String,String> map = new HashMap<>();
        System.out.println(message_id);
        map.put("messageid",message_id);

        OkHttpUtil.post(uri, new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                    Log.i(TAG,"OnFailure:",e);
                }
             @Override
             public void onResponse(Call call, Response response) throws IOException {
                 String responseData = response.body().string();
                 Log.i(TAG,responseData);
                 Gson gson = new Gson();
                 java.lang.reflect.Type type = new TypeToken<BaseResModel>() {}.getType();
                 final BaseResModel baseResModel = gson.fromJson(responseData,type);
                 if (baseResModel!=null) {
                     System.out.printf(baseResModel.desc + baseResModel.status);
                 }
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         if (baseResModel != null && baseResModel.status == 1000){
                             Toast.makeText(MessageDetailActivity.this, "确认成功", Toast.LENGTH_SHORT).show();

                         }
                         else if (baseResModel != null){
                             Toast.makeText(MessageDetailActivity.this, baseResModel.desc, Toast.LENGTH_SHORT).show();
                         }
                         else{
                             Toast.makeText(MessageDetailActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                         }
                     }
                               }
                    );

                }

            },map);
        }



}
