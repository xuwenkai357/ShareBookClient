package com.zucc.xwk_31401151.sharebookclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.dataBean.response.MessageResponse;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.MessageDetailActivity;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

import java.util.List;

/**
 * Created by 帅气逼人唐志丰 on 2018/4/24.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private Context mContext;
    private int columns;
    private List<MessageResponse> messageResponses;

    public MessageAdapter(Context context,List<MessageResponse> messageResponses ,int columns ){
        this.mContext = context;
        this.messageResponses = messageResponses;
        this.columns = columns;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new MessageHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (messageResponses == null || messageResponses.isEmpty()) {
            return TYPE_EMPTY;
        } else {
            return TYPE_DEFAULT;
        }
    }

    public int getItemColumnSpan(int position) {
        switch (getItemViewType(position)) {
            case TYPE_DEFAULT:
                return 1;
            default:
                return columns;
        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageHolder){
            MessageResponse message = messageResponses.get(position);

            ((MessageHolder) holder).tv_message_body.setText(message.getBody());
            ((MessageHolder) holder).tv_message_create_time.setText(message.getCreate_time());
            if (message.getStatus().equals("1")) {
                ((MessageHolder) holder).iv_point.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putSerializable(MessageResponse.serialVersionName, message);
                    Intent intent = new Intent(UIUtils.getContext(), MessageDetailActivity.class);
                    intent.putExtras(b);
                    UIUtils.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (messageResponses.isEmpty()){
            return 1;
        }else {
            return messageResponses.size();
        }

    }

    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }

    class MessageHolder extends RecyclerView.ViewHolder {

        private TextView tv_message_body;
        private TextView tv_message_create_time;
        private ImageView iv_point;


        public MessageHolder(View itemView) {
            super(itemView);
            tv_message_body = (TextView) itemView.findViewById(R.id.tv_message_body);
            tv_message_create_time = (TextView) itemView.findViewById(R.id.tv_create_time);
            iv_point = (ImageView) itemView.findViewById(R.id.iv_message_point);


        }
    }
}
