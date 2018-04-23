package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zucc.xwk_31401151.sharebookclient.R;

public class SettingActivity extends BaseActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUI();
    }

    public void initUI(){
        mButton = findViewById(R.id.exit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                System.out.print("传送成功");
                sendBroadcast(intent);
            }
        });
    }
}
