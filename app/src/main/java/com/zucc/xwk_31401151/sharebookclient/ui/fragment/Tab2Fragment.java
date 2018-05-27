package com.zucc.xwk_31401151.sharebookclient.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.api.presenter.impl.ShowDynamicListPresenterImpl;
import com.zucc.xwk_31401151.sharebookclient.api.view.IShowDynamicListView;
import com.zucc.xwk_31401151.sharebookclient.bean.ShowDynamicListResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.ShowDynamicResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.BookDynamicAddActivity;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.ShowDynamicListAdapter;
import com.zucc.xwk_31401151.sharebookclient.utils.common.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2Fragment extends BaseFragment implements IShowDynamicListView, SwipeRefreshLayout.OnRefreshListener {

    private static int count = 20;
    private static int page = 0;

    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private GridLayoutManager mLayoutManager;
    private ShowDynamicListAdapter showDynamicListAdapter;
    private List<ShowDynamicResponse> showDynamicResponse;
    private ShowDynamicListPresenterImpl showDynamicListPresenter;

    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (showDynamicResponse == null || showDynamicResponse.size() == 0) {
            page = 0;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_tab2, container, false);
//        String result = getArguments().getString("tag");
//        if (!TextUtils.isEmpty(result)) {
//            tag = result;
//        }
    }

    @Override
    protected void initEvents() {
        int spanCount = getResources().getInteger(R.integer.home_span_count);
        showDynamicListPresenter = new ShowDynamicListPresenterImpl(this);
        showDynamicResponse = new ArrayList<>();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        //设置布局管理器
        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return showDynamicListAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        showDynamicListAdapter = new ShowDynamicListAdapter(getActivity(), showDynamicResponse, spanCount);
        mRecyclerView.setAdapter(showDynamicListAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerViewScrollDetector());
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initData(boolean isSavedNull) {
        if (isSavedNull) {
            onRefresh();
        }

    }



    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        BookInfoResponse bookInfo = new BookInfoResponse();
        Bundle b = new Bundle();
        b.putSerializable(BookInfoResponse.serialVersionName, bookInfo);
        Intent intent = new Intent(getActivity(), BookDynamicAddActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        showDynamicListPresenter.loadShowDynamic(0,count);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void refreshData(Object result) {
        if (result instanceof ShowDynamicListResponse) {
            showDynamicResponse.clear();
            showDynamicResponse.addAll(((ShowDynamicListResponse) result).getShowDynamics());
            showDynamicListAdapter.notifyDataSetChanged();
            page++;
        }
    }

    @Override
    public void addData(Object result) {
        final int start = showDynamicResponse.size();
        showDynamicResponse.addAll(((ShowDynamicListResponse) result).getShowDynamics());
        showDynamicListAdapter.notifyItemRangeInserted(start, showDynamicResponse.size());
        page++;

    }

    public void onLoadMore() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            showDynamicListPresenter.loadShowDynamic(page * count, count);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && showDynamicResponse != null && showDynamicResponse.isEmpty()) {
            onRefresh();
        }
    }

    class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {
        private int lastVisibleItem;
        private int mScrollThreshold = DensityUtils.dp2px(getActivity(), 1);

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == showDynamicListAdapter.getItemCount()) {
                onLoadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;

//            if (isSignificantDelta) {
//                if (dy > 0) {
//                    ((MainActivity) getActivity()).hideFloatingBar();
//                } else {
//                    ((MainActivity) getActivity()).showFloatingBar();
//                }
//            }

            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    }



}
