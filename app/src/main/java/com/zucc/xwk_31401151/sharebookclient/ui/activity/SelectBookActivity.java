package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.impl.SelectBookListPresenterImpl;
import com.zucc.xwk_31401151.sharebookclient.api.view.ISelectBookListView;
import com.zucc.xwk_31401151.sharebookclient.bean.UserBean;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookListResponse;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.SelectBookListAdapter;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectBookActivity extends BaseActivity implements ISelectBookListView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;



    private GridLayoutManager mLayoutManager;
    private SelectBookListAdapter selectBookListAdapter;
    private List<BookInfoResponse> bookInfoResponses;
    private SelectBookListPresenterImpl selectBookListPresenter;

    private int spanCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_book);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);
        toolbar.setTitle("选择评论书籍");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        spanCount = (int) getResources().getInteger(R.integer.home_span_count);
        selectBookListPresenter = new SelectBookListPresenterImpl(this);
        bookInfoResponses = new ArrayList<>();


        //获取当前登录用户信息
        UserBean userBean=new UserBean();
        userBean = SaveUserUtil.getInstance().getUser(this);
        String userid = userBean.getUser_id();


        mLayoutManager = new GridLayoutManager(SelectBookActivity.this, spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return selectBookListAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);



        //设置adapter
        selectBookListAdapter = new SelectBookListAdapter(this,bookInfoResponses,spanCount);
        recyclerView.setAdapter(selectBookListAdapter);
        onRefresh(userid);

    }

    private void onRefresh(String userId) {
        Log.i("读取用户信息2",""+userId);
        selectBookListPresenter.loadSelectBookList(userId);
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
        if(result instanceof BookListResponse){
            final BookListResponse response = (BookListResponse) result;

            System.out.println(response.getBooks().get(1).getBook_info_id());
            bookInfoResponses.clear();
            Log.i("更新数据","执行clear");
            bookInfoResponses.addAll(response.getBooks());
            Log.i("更新数据","执行add");
            selectBookListAdapter.notifyDataSetChanged();
            Log.i("更新数据","执行更新");
        }

    }
}
