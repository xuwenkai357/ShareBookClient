package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.impl.BookDetailPresenterImpl;
import com.zucc.xwk_31401151.sharebookclient.api.view.IBookDetailView;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookReviewResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookReviewsListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookSeriesListResponse;
import com.zucc.xwk_31401151.sharebookclient.model.LoginModel;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.BookDetailAdapter;
import com.zucc.xwk_31401151.sharebookclient.utils.common.OkHttpUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.Blur;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/19 0019
 * Description: 图书详情页
 */
public class BookDetailActivity extends BaseActivity implements IBookDetailView {
    private static final String COMMENT_FIELDS = "id,rating,author,title,updated,comments,summary,votes,useless";
    private static final int REVIEWS_COUNT = 5;
    private static final int PAGE = 0;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private BookDetailAdapter mDetailAdapter;
    private ImageView iv_book_img;
    private ImageView iv_book_bg;

    private BookInfoResponse mBookInfoResponse;
    private BookReviewsListResponse mReviewsListResponse;
    private BookSeriesListResponse mSeriesListResponse;

    private BookDetailPresenterImpl bookDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initEvents();
    }


    protected void initEvents() {
        bookDetailPresenter = new BookDetailPresenterImpl(this);
        mReviewsListResponse = new BookReviewsListResponse();
        mSeriesListResponse = new BookSeriesListResponse();
        mBookInfoResponse = (BookInfoResponse) getIntent().getSerializableExtra(BookInfoResponse.serialVersionName);
        mLayoutManager = new LinearLayoutManager(BookDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDetailAdapter = new BookDetailAdapter(mBookInfoResponse, mReviewsListResponse, mSeriesListResponse);
        mRecyclerView.setAdapter(mDetailAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //头部图片
        iv_book_img = (ImageView) findViewById(R.id.iv_book_img);
        iv_book_bg = (ImageView) findViewById(R.id.iv_book_bg);
        mCollapsingLayout.setTitle(mBookInfoResponse.getTitle());

        Bitmap book_img = getIntent().getParcelableExtra("book_img");
        if (book_img != null) {
            iv_book_img.setImageBitmap(book_img);
            iv_book_bg.setImageBitmap(Blur.apply(book_img));
            iv_book_bg.setAlpha(0.9f);
        } else {
            Glide.with(this)
                    .load(mBookInfoResponse.getImage())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            iv_book_img.setImageBitmap(resource);
                            iv_book_bg.setImageBitmap(Blur.apply(resource));
                            iv_book_bg.setAlpha(0.9f);
                        }
                    });
        }
        mFab.setOnClickListener(v -> {

            Toast.makeText(BookDetailActivity.this,"进入借书界面", Toast.LENGTH_SHORT).show();

            Bundle b = new Bundle();
            b.putSerializable(BookInfoResponse.serialVersionName, mBookInfoResponse);
            Intent intent = new Intent(UIUtils.getContext(), BorrowListActivity.class);
            intent.putExtras(b);
            UIUtils.startActivity(intent);



        });
        bookDetailPresenter.loadReviews(mBookInfoResponse.getBook_info_id(), PAGE * REVIEWS_COUNT, REVIEWS_COUNT, COMMENT_FIELDS);


    }


//    protected int getMenuID() {
//        return R.menu.menu_book_detail;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.action_up:
                StringBuilder sb = new StringBuilder();
                sb.append(getString(R.string.your_friend));
                sb.append(getString(R.string.share_book_1));
                sb.append(mBookInfoResponse.getTitle());
                sb.append(getString(R.string.share_book_2));
                UIUtils.share(this, sb.toString(), null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (item.getIcon() instanceof Animatable) {
                        ((Animatable) item.getIcon()).start();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMessage(String msg) {
//        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (mFab.getDrawable() instanceof Animatable) {
//                ((Animatable) mFab.getDrawable()).start();
//            }
        } else {
            //低于5.0，显示其他动画
//            showMessage(getString(R.string.loading));
        }
    }

    @Override
    public void hideProgress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (mFab.getDrawable() instanceof Animatable) {
//                ((Animatable) mFab.getDrawable()).stop();
//            }
        } else {
            //低于5.0，显示其他动画
        }
    }

    @Override
    public void updateView(Object result) {
        if (result instanceof BookReviewsListResponse) {
            final BookReviewsListResponse response = (BookReviewsListResponse) result;

            Log.i("response",response.getTotal()+"");

            mReviewsListResponse.setTotal(response.getTotal());
            mReviewsListResponse.getReviews().addAll(response.getReviews());
            mDetailAdapter.notifyDataSetChanged();
            if (mBookInfoResponse.getSeries() != null) {

            }
        } else if (result instanceof BookSeriesListResponse) {
            final BookSeriesListResponse response = (BookSeriesListResponse) result;
            mSeriesListResponse.setTotal(response.getTotal());
            mSeriesListResponse.getBooks().addAll(response.getBooks());
            mDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        bookDetailPresenter.cancelLoading();
//        if (mFab.getDrawable() instanceof Animatable) {
//            ((Animatable) mFab.getDrawable()).stop();
//        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }


}
