package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.impl.BookDetailPresenterImpl;
import com.zucc.xwk_31401151.sharebookclient.api.view.IBookDetailView;
import com.zucc.xwk_31401151.sharebookclient.bean.UserBean;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookReviewsListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookSeriesListResponse;
import com.zucc.xwk_31401151.sharebookclient.model.BaseResModel;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.BookDetailAdapter;
import com.zucc.xwk_31401151.sharebookclient.utils.common.OkHttpUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.Blur;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class AddBookDetailActivity extends BaseActivity implements IBookDetailView {
    private static final String COMMENT_FIELDS = "id,rating,author,title,updated,comments,summary,votes,useless";
    private static final String SERIES_FIELDS = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,summary,images,pages,price,binding,isbn13,series";
    private static final int REVIEWS_COUNT = 5;
    private static final int SERIES_COUNT = 6;
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
        setContentView(R.layout.activity_add_book_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
//        mToolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_action_clear));
        initEvents();
    }


    protected void initEvents() {
        bookDetailPresenter = new BookDetailPresenterImpl(this);
        mReviewsListResponse = new BookReviewsListResponse();
        mSeriesListResponse = new BookSeriesListResponse();
        mBookInfoResponse = (BookInfoResponse) getIntent().getSerializableExtra(BookInfoResponse.serialVersionName);
        mLayoutManager = new LinearLayoutManager(AddBookDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


//        List<BookReviewResponse> bookReviewResponses =new ArrayList<>();
//
//        BookReviewResponse bookReviewResponse1 = new BookReviewResponse();
//        bookReviewResponse1.setBook_info_id(mBookInfoResponse.getBook_info_id());
//        bookReviewResponse1.setDynamic_id("1");
//        bookReviewResponse1.setUser_id(18+"");
//        bookReviewResponse1.setBody(123+"");
//        bookReviewResponse1.setPhoto_url(null);
//        bookReviewResponse1.setCreate_time("2018-05-05");
//        bookReviewResponse1.setUser_name("123456");
//
//        bookReviewResponses.add(bookReviewResponse1);
//
//        mReviewsListResponse.setTotal(1);
//        mReviewsListResponse.setCount(1);
//        mReviewsListResponse.setStart(0);
//        mReviewsListResponse.setReviews(bookReviewResponses);



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
//            Intent intent = new Intent(BookDetailActivity.this, WebViewActivity.class);
//            intent.putExtra("url", mBookInfoResponse.getUrl());
//            startActivity(intent);


            //获取当前登录用户信息
            UserBean userBean=new UserBean();
            userBean = SaveUserUtil.getInstance().getUser(this);
            String userid = userBean.getUser_id();


            new MaterialDialog.Builder(AddBookDetailActivity.this)
                    .title("添加书籍确认")
                    .content("是否添加《"+mBookInfoResponse.getTitle()+"》该书籍？")
                    .iconRes(R.drawable.icon)
                    .positiveText("我已确认")
                    .negativeText("我再考虑")
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (which == DialogAction.POSITIVE) {
                                Toast.makeText(AddBookDetailActivity.this, "我已确认" , Toast.LENGTH_LONG).show();
                                String book_info_id = mBookInfoResponse.getBook_info_id();

                                update(book_info_id,userid);

                                Intent intent = new Intent(AddBookDetailActivity.this,MyShareListActivity.class);
                                startActivity(intent);
                                finish();


                            } else if (which == DialogAction.NEGATIVE) {
                                Toast.makeText(AddBookDetailActivity.this, "我再考虑考虑", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .show();







        });
        bookDetailPresenter.loadReviews(mBookInfoResponse.getBook_info_id(), PAGE * REVIEWS_COUNT, REVIEWS_COUNT, COMMENT_FIELDS);


    }

    private void update(String book_info_id, String userid) {
        String uri ;
        uri = AppConstant.getUrl() + "/booklist/add";
        System.out.println(uri);
        Map<String,String> map = new HashMap<>();
        System.out.println(book_info_id+"  "+book_info_id);
        map.put("book_info_id",book_info_id);
        map.put("userid",userid);

        OkHttpUtil.post(uri, new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("update","OnFailure:",e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.i("update",responseData);
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<BaseResModel>() {}.getType();
                final BaseResModel baseResModel = gson.fromJson(responseData,type);
                if (baseResModel!=null) {
                    System.out.printf(baseResModel.desc + baseResModel.status);
                }
            }

        },map);
    }


    protected int getMenuID() {
        return R.menu.menu_book_detail;
    }

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
        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_SHORT).show();
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
                bookDetailPresenter.loadSeries(mBookInfoResponse.getSeries().getId(), PAGE * SERIES_COUNT, 6, SERIES_FIELDS);
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

