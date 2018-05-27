package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.zucc.xwk_31401151.sharebookclient.R;

public class SettingActivity extends BaseActivity {

    private Button mButton;
    private Button messageButton;
    private Button modifyButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUI();
    }

    public void initUI(){


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);
        toolbar.setTitle("个人信息");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mButton = findViewById(R.id.exit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                System.out.print("传送成功");
                sendBroadcast(intent);
            }
        });
        messageButton = findViewById(R.id.message);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SettingActivity.this,MessageListActivity.class);
                startActivity(intent2);
            }
        });

        modifyButton = findViewById(R.id.modify);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(SettingActivity.this,ModifyActivity.class);
                startActivity(intent3);
            }
        });
    }
}
