package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.impl.BorrowListPresenterImpl;
import com.zucc.xwk_31401151.sharebookclient.api.view.IBorrowListView;
import com.zucc.xwk_31401151.sharebookclient.bean.ShareListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.ShareResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.UserBean;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.BorrowListAdapter;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BorrowListActivity extends BaseActivity implements IBorrowListView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BookInfoResponse mBookInfoResponse;
    private GridLayoutManager mLayoutManager;
    private BorrowListAdapter borrowListAdapter;
    private List<ShareResponse> shareResponses;
    private BorrowListPresenterImpl borrowListPresenter;

    private int spanCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_list);
        ButterKnife.bind(this);
        initEvents();
    }

    protected void initEvents() {



        spanCount = (int) getResources().getInteger(R.integer.home_span_count);
        borrowListPresenter = new BorrowListPresenterImpl(this);
        shareResponses = new ArrayList<>();

        mBookInfoResponse = (BookInfoResponse) getIntent().getSerializableExtra(BookInfoResponse.serialVersionName);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);
        toolbar.setTitle("《"+mBookInfoResponse.getTitle()+"》可借阅列表");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //获取当前登录用户信息
        UserBean userBean=new UserBean();
        userBean = SaveUserUtil.getInstance().getUser(this);
        String userid = userBean.getUser_id();
        int userId = Integer.parseInt(userid);

        mLayoutManager = new GridLayoutManager(BorrowListActivity.this, spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return borrowListAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);



        //设置adapter
        borrowListAdapter = new BorrowListAdapter(this,shareResponses,spanCount);
        recyclerView.setAdapter(borrowListAdapter);
        onRefresh(mBookInfoResponse.getBook_info_id(), String.valueOf(userId));


    }

    private void onRefresh(String book_info_id,String userid) {
        Log.i("读取用户信息2",""+book_info_id);

        int book_info_id_int = Integer.parseInt(book_info_id);

        borrowListPresenter.loadBorrowList(book_info_id_int,userid);
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
        if(result instanceof ShareListResponse){
            final ShareListResponse response = (ShareListResponse) result;

//            System.out.println(response.getBookListModels().get(1).getBook_id());
            shareResponses.clear();
            Log.i("更新数据","执行clear");
            shareResponses.addAll(response.getBookListModels());
            Log.i("更新数据","执行add");
            borrowListAdapter.notifyDataSetChanged();
            Log.i("更新数据","执行更新");
        }

    }
}
