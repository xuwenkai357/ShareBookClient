package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.impl.MessageListPresenterImpl;
import com.zucc.xwk_31401151.sharebookclient.api.view.IMessageListView;
import com.zucc.xwk_31401151.sharebookclient.bean.MessageListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.MessageResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.UserBean;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.MessageAdapter;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageListActivity extends BaseActivity implements IMessageListView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private GridLayoutManager mLayoutManager;
    private MessageAdapter messageAdapter;
    private List<MessageResponse> messageResponses;
    private MessageListPresenterImpl messageListPresenter;


    private int spanCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initEvents();
    }

    protected void initEvents() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);
        toolbar.setTitle("我的站内信");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spanCount = (int) getResources().getInteger(R.integer.home_span_count);
        messageListPresenter = new MessageListPresenterImpl(this);
        messageResponses = new ArrayList<>();


        //获取当前登录用户信息
        UserBean userBean = new UserBean();
        userBean = SaveUserUtil.getInstance().getUser(this);
        String userid = userBean.getUser_id();


        mLayoutManager = new GridLayoutManager(MessageListActivity.this, spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return messageAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //设置adapter
        messageAdapter = new MessageAdapter(this, messageResponses, spanCount);
        mRecyclerView.setAdapter(messageAdapter);
        onRefresh(userid);
    }

    private void onRefresh(String userid) {

        Log.i("读取用户信息2", userid);
        messageListPresenter.loadMessages(userid);
    }


    @Override
    public void showMessage(String msg) {
//        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void updateView(Object result) {

        final MessageListResponse response = (MessageListResponse) result;
        Log.i("MessageResponse", response.getTotal() + "");

        System.out.println(response.getMessages());
        messageResponses.clear();
        Log.i("更新数据", "执行clear");
        messageResponses.addAll(response.getMessages());
        Log.i("更新数据", "执行add");
        messageAdapter.notifyDataSetChanged();
        Log.i("更新数据", "执行更新");
    }


}
