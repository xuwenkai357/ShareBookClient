package com.zucc.xwk_31401151.sharebookclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.bean.ShowDynamicResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookReviewResponse;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.BaseActivity;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.BookDetailActivity;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/5/25.
 */

public class ShowDynamicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private List<ShowDynamicResponse> showDynamicResponses;
    private Context mContext;
    private int columns;

    public ShowDynamicListAdapter(Context context, List<ShowDynamicResponse> responses, int columns) {
        this.showDynamicResponses = responses;
        this.columns = columns;
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_dynamic_list, parent, false);
            return new ShowDynamicListHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (showDynamicResponses == null || showDynamicResponses.isEmpty()) {
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
        if (holder instanceof ShowDynamicListHolder){
            ShowDynamicResponse showDynamicResponse = showDynamicResponses.get(position);
            BookInfoResponse bookInfoResponse = showDynamicResponse.getBook();
            BookReviewResponse bookReviewResponse = showDynamicResponse.getBookReviewResponse();
            if (bookInfoResponse!=null){
                Glide.with(mContext)
                        .load(bookInfoResponse.getImage())
                        .into(((ShowDynamicListHolder) holder).iv_book_img);
                ((ShowDynamicListHolder) holder).tv_book_info.setText(bookInfoResponse.getAuthor());
                ((ShowDynamicListHolder) holder).tv_book_title.setText(bookInfoResponse.getTitle());
                ((ShowDynamicListHolder) holder).enter_book.setVisibility(View.VISIBLE);
            }
            else {
                ((ShowDynamicListHolder) holder).enter_book.setVisibility(View.GONE);
            }

            ((ShowDynamicListHolder) holder).tv_user_name.setText(bookReviewResponse.getUser_name());
            ((ShowDynamicListHolder) holder).tv_dynamic.setText(bookReviewResponse.getBody());

            ((ShowDynamicListHolder) holder).tv_update_time.setText(bookReviewResponse.getCreate_time());
            ((ShowDynamicListHolder) holder).enter_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putSerializable(BookInfoResponse.serialVersionName, bookInfoResponse);
                    Bitmap bitmap;
                    GlideBitmapDrawable imageDrawable = (GlideBitmapDrawable) ((ShowDynamicListHolder) holder).iv_book_img.getDrawable();
                    if (imageDrawable != null) {
                        bitmap = imageDrawable.getBitmap();
                        b.putParcelable("book_img", bitmap);
                    }
                    Intent intent = new Intent(UIUtils.getContext(), BookDetailActivity.class);
                    intent.putExtras(b);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (BaseActivity.activity == null) {
                            UIUtils.startActivity(intent);
                            return;
                        }
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(BaseActivity.activity, ((ShowDynamicListHolder) holder).iv_book_img, "book_img");
                        BaseActivity.activity.startActivity(intent, options.toBundle());
                    } else {
                        UIUtils.startActivity(intent);
                    }
                }
            });






        }

    }

    @Override
    public int getItemCount() {
        if (showDynamicResponses.isEmpty()) {
            return 1;
        }
        return showDynamicResponses.size();
    }


    class ShowDynamicListHolder extends RecyclerView.ViewHolder {


        private TextView tv_user_name;
        private TextView tv_dynamic;
        private LinearLayout enter_book;
        private ImageView iv_book_img;
        private TextView tv_book_title;
        private TextView tv_book_info;
        private TextView tv_update_time;

        public ShowDynamicListHolder(View itemView) {
            super(itemView);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_dynamic = itemView.findViewById(R.id.tv_dynamic);
            enter_book = itemView.findViewById(R.id.add_book);
            iv_book_img = itemView.findViewById(R.id.iv_book_img);
            tv_book_title = itemView.findViewById(R.id.tv_book_title);
            tv_book_info = itemView.findViewById(R.id.tv_book_info);
            tv_update_time = itemView.findViewById(R.id.tv_update_time);
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
