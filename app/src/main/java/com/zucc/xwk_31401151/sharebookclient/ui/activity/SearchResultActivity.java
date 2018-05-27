package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.impl.BookListPresenterImpl;
import com.zucc.xwk_31401151.sharebookclient.api.view.IBookListView;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookListResponse;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.AddBookListAdapter;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.BookListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity implements IBookListView, SwipeRefreshLayout.OnRefreshListener {

    private static final String fields = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,summary,images,pages,price,binding,isbn13,alt";

    private int count = 20;
    private int page = 0;
    private String q;
    private String enter;

    private boolean isLoadAll;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
//    @BindView(R.id.swipe_refresh_widget)
//    SwipeRefreshLayout mSwipeRefreshLayout;

    private GridLayoutManager mLayoutManager;
    private AddBookListAdapter mListAdapter;
    private BookListAdapter mBookListAdapter;
    private List<BookInfoResponse> bookInfoResponses;
    private BookListPresenterImpl bookListPresenter;
    private int spanCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isLoadAll = savedInstanceState.getBoolean("isLoadAll");
        }

        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initEvents();
    }

    protected void initEvents() {
        q = getIntent().getStringExtra("q");
        enter = getIntent().getStringExtra("enter");

        toolbar.setTitle(q+"的搜索结果");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        spanCount = (int) getResources().getInteger(R.integer.home_span_count);
        bookListPresenter = new BookListPresenterImpl(this);
        bookInfoResponses = new ArrayList<>();
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
//                R.color.recycler_color3, R.color.recycler_color4);

        if (enter.equals("capture")) {

            mLayoutManager = new GridLayoutManager(SearchResultActivity.this, spanCount);
            mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return mListAdapter.getItemColumnSpan(position);
                }
            });
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);

            //设置adapter
            mListAdapter = new AddBookListAdapter(this, bookInfoResponses, spanCount);
            mRecyclerView.setAdapter(mListAdapter);
        }
        else if (enter.equals("search")){
            mLayoutManager = new GridLayoutManager(SearchResultActivity.this, spanCount);
            mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return mBookListAdapter.getItemColumnSpan(position);
                }
            });
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);

            //设置adapter
            mBookListAdapter = new BookListAdapter(this, bookInfoResponses, spanCount);
            mRecyclerView.setAdapter(mBookListAdapter);
        }




        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (enter.equals("capture")) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mListAdapter.getItemCount()) {
                        onLoadMore();
                    }
                }
                else if (enter.equals("search")){
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mBookListAdapter.getItemCount()) {
                        onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
//        mSwipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void showMessage(String msg) {
//        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(true);
//            }
//        });
    }

    @Override
    public void hideProgress() {
//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }

    @Override
    public void refreshData(Object result) {
        bookInfoResponses.clear();
        bookInfoResponses.addAll(((BookListResponse) result).getBooks());

        if (enter.equals("capture")) {
            mListAdapter.notifyDataSetChanged();
        }
        else if (enter.equals("search")){
            mBookListAdapter.notifyDataSetChanged();
        }

        if (((BookListResponse) result).getTotal() > page * count) {
            isLoadAll = false;
        } else {
            isLoadAll = true;
        }
        page++;
    }

    @Override
    public void addData(Object result) {
        bookInfoResponses.addAll(((BookListResponse) result).getBooks());

        if (enter.equals("capture")) {
            mListAdapter.notifyDataSetChanged();
        }
        else if (enter.equals("search")){
            mBookListAdapter.notifyDataSetChanged();
        }

        if (((BookListResponse) result).getBooks().size() > page * count) {
            page++;
            isLoadAll = false;
        } else {
            isLoadAll = true;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        bookListPresenter.loadBooks(q, null, 0, count, fields);
    }

    private void onLoadMore() {
//        if (!isLoadAll) {
//            if (!mSwipeRefreshLayout.isRefreshing()) {
//                bookListPresenter.loadBooks(q, null, page * count, count, fields);
//            }
//        } else {
//            showMessage(getResources().getString(R.string.no_more));
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isLoadAll", isLoadAll);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        bookListPresenter.cancelLoading();
        super.onDestroy();
    }


}
