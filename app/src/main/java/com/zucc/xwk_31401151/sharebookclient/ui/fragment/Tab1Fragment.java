package com.zucc.xwk_31401151.sharebookclient.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jpeng.jptabbar.JPTabBar;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.MyBorrowListActivity;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.MyShareListActivity;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.SettingActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class Tab1Fragment extends BaseFragment implements View.OnClickListener {

    private JPTabBar mTabBar;

    private CircleImageView circleImageView;
    private TextView tv_username;
    private CardView cd_myshare;
    private CardView cd_myborrow;


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

        tv_username = layout.findViewById(R.id.tv_user_name);
        //获取当前登录用户信息
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sp.getString("USER_NAME","");
        tv_username.setText(username);



        cd_myshare = layout.findViewById(R.id.cd_myshare);

        cd_myshare.setOnClickListener(this);



        cd_myborrow = layout.findViewById(R.id.cd_myborrow);

        cd_myborrow.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        if(view == circleImageView){
            Intent intent = new Intent(getActivity(),SettingActivity.class);
            startActivity(intent);
        }
        if (view == cd_myshare){
            Intent intent = new Intent(getActivity(),MyShareListActivity.class);
            startActivity(intent);
        }
        if (view==cd_myborrow){
            Intent intent = new Intent(getActivity(),MyBorrowListActivity.class);
            startActivity(intent);
        }


    }
}
