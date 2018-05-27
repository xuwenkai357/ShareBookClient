package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.impl.BookDetailPresenterImpl;
import com.zucc.xwk_31401151.sharebookclient.api.view.IEBookDetailView;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookReviewsListResponse;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.BookReviewsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/26
 * Description:
 */
public class BookReviewsActivity extends BaseActivity implements IEBookDetailView, SwipeRefreshLayout.OnRefreshListener {
    private static final String COMMENT_FIELDS = "id,rating,author,title,updated,comments,summary,votes,useless";
    private static int count = 20;

    private int page = 0;
    private static String bookId;
    private static String bookName;

    private boolean isLoadAll;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager mLayoutManager;
    private BookReviewsAdapter mReviewsAdapter;
    private BookReviewsListResponse mReviews;
    private BookDetailPresenterImpl bookDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isLoadAll = savedInstanceState.getBoolean("isLoadAll");
        }
        setContentView(R.layout.activity_reviews);

        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initEvents();

    }


    protected void initEvents() {

        bookName = getIntent().getStringExtra("bookName");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);
        toolbar.setTitle("《"+bookName+"》的评论");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        bookDetailPresenter = new BookDetailPresenterImpl(this);
        mReviews = new BookReviewsListResponse();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        mLayoutManager = new LinearLayoutManager(BookReviewsActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mReviewsAdapter = new BookReviewsAdapter(mReviews);
        mRecyclerView.setAdapter(mReviewsAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        bookId = getIntent().getStringExtra("bookId");
        setTitle(bookName + getString(R.string.comment_of_book));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mReviewsAdapter.getItemCount()) {
                    onLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }


    @Override
    public void showMessage(String msg) {
//        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void updateView(Object result) {
        final BookReviewsListResponse response = (BookReviewsListResponse) result;
        if (page == 0) {
            mReviews.getReviews().clear();
        }
        mReviews.getReviews().addAll(response.getReviews());
        mReviewsAdapter.notifyDataSetChanged();

        if (response.getReviews().size() < count) {
            isLoadAll = true;
        } else {
            page++;
            isLoadAll = false;
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        bookDetailPresenter.loadReviews(bookId, page * count, count, COMMENT_FIELDS);
    }

    private void onLoadMore() {
        if (!isLoadAll) {
            bookDetailPresenter.loadReviews(bookId, page * count, count, COMMENT_FIELDS);
        } else {
//            showMessage(getString(R.string.no_more));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isLoadAll", isLoadAll);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        bookDetailPresenter.cancelLoading();
        super.onDestroy();
    }
}
