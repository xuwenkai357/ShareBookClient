package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.impl.MyshareListPresenterImpl;
import com.zucc.xwk_31401151.sharebookclient.api.view.IMyshareListView;
import com.zucc.xwk_31401151.sharebookclient.bean.ShareListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.ShareResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.UserBean;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.MyShareListAdapter;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyShareListActivity extends BaseActivity implements IMyshareListView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private GridLayoutManager mLayoutManager;
    private MyShareListAdapter myShareListAdapter;
    private List<ShareResponse> shareResponses;
    private MyshareListPresenterImpl myshareListPresenter;

    private int spanCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_share_list);
        ButterKnife.bind(this);
        initEvents();
    }

    protected void initEvents() {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);
        toolbar.setTitle("我的分享");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        spanCount = (int) getResources().getInteger(R.integer.home_span_count);
        myshareListPresenter = new MyshareListPresenterImpl(this);
        shareResponses = new ArrayList<>();


        //获取当前登录用户信息
        UserBean userBean=new UserBean();
        userBean = SaveUserUtil.getInstance().getUser(this);
        String userid = userBean.getUser_id();
        int userId = Integer.parseInt(userid);

        mLayoutManager = new GridLayoutManager(MyShareListActivity.this, spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return myShareListAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);



        //设置adapter
        myShareListAdapter = new MyShareListAdapter(this,shareResponses,spanCount);
        recyclerView.setAdapter(myShareListAdapter);
        onRefresh(userId);


    }

    private void onRefresh(int userId) {
        Log.i("读取用户信息2",""+userId);
        myshareListPresenter.loadMyShareList(userId);
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void updateView(Object result) {

            final ShareListResponse response = (ShareListResponse) result;


            shareResponses.clear();
            Log.i("更新数据","执行clear");
            shareResponses.addAll(response.getBookListModels());
            Log.i("更新数据","执行add");
            myShareListAdapter.notifyDataSetChanged();
            Log.i("更新数据","执行更新");

    }
}
