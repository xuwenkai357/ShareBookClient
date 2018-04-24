package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.dataBean.response.MessageResponse;
import com.zucc.xwk_31401151.sharebookclient.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageListActivity extends BaseActivity  {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private GridLayoutManager mLayoutManager;
    private MessageAdapter messageAdapter;
    private List<MessageResponse> messageResponses;

    private MessageResponse messageResponse1;
    private MessageResponse messageResponse2;

    private int spanCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initEvents();
    }

    protected void initEvents() {
        setTitle("站内信");
        spanCount = (int) getResources().getInteger(R.integer.home_span_count);
        messageResponses = new ArrayList<>();

        //
        messageResponse1 = new MessageResponse();
        messageResponse2 = new MessageResponse();

        messageResponse1.setStatus("1");
        messageResponse2.setStatus("0");
        messageResponse1.setCreate_time("2018-04-21");
        messageResponse2.setCreate_time("2018-04-21");
        messageResponse1.setBody("亲爱的用户，您好！\n  你所共享的书籍《百年孤独》被 亲爱的你 用户借阅，该用户的联系方式为13345678907。\n请在短时间内联系对方，以便方便借书。谢谢！");
        messageResponse2.setBody("亲爱的用户，您好！\n  你所共享的书籍《百年孤独》被 亲爱的你 用户借阅，该用户的联系方式为13345678907。\n请在短时间内联系对方，以便方便借书。谢谢！");

        messageResponses.add(messageResponse1);
        messageResponses.add(messageResponse2);
        //

        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

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
        messageAdapter = new MessageAdapter(this,messageResponses,spanCount);
        mRecyclerView.setAdapter(messageAdapter);
    }


}
