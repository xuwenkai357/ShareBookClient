package com.zucc.xwk_31401151.sharebookclient.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jpeng.jptabbar.JPTabBar;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.SettingActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class Tab1Fragment extends BaseFragment implements View.OnClickListener {

    private JPTabBar mTabBar;

    private CircleImageView circleImageView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tab1,null);
        initUI(layout);
        return layout;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData(boolean isSavedNull) {

    }


    private void initUI(View layout) {

        circleImageView = layout.findViewById(R.id.iv_userimage);

        circleImageView.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if(view == circleImageView){
            Intent intent = new Intent(getActivity(),SettingActivity.class);
            startActivity(intent);
        }


    }
}
