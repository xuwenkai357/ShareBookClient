package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.dataBean.response.MessageResponse;

import butterknife.BindView;

public class MessageDetailActivity extends BaseActivity {

    private TextView tv_body;
    private MessageResponse messageResponse;
    private Button btn_change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        initEvents();

    }

    protected void initEvents() {
        tv_body = (TextView) findViewById(R.id.tv_message_body);
        btn_change = (Button) findViewById(R.id.btn_change);
        messageResponse = (MessageResponse) getIntent().getSerializableExtra(MessageResponse.serialVersionName);
        tv_body.setText(messageResponse.getBody());
        if (messageResponse.getStatus().equals("1")){
            btn_change.setVisibility(View.GONE);
        }

    }

}
