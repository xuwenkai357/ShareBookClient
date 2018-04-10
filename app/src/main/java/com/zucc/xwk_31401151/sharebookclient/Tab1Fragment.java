package com.zucc.xwk_31401151.sharebookclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jpeng.jptabbar.JPTabBar;


public class Tab1Fragment extends Fragment implements View.OnClickListener {

    private JPTabBar mTabBar;
    private Button mSettingBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tab1,null);
        initUI(layout);
        return layout;
    }


    private void initUI(View layout) {
        mSettingBtn = layout.findViewById(R.id.show);
        mSettingBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if ( view == mSettingBtn){
            Intent intent = new Intent(getActivity(),SettingActivity.class);
            startActivity(intent);
        }

    }
}
