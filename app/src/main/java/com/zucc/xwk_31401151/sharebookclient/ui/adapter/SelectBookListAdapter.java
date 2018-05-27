package com.zucc.xwk_31401151.sharebookclient.ui.adapter;

import android.app.Activity;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.BaseActivity;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.BookDynamicAddActivity;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

import java.util.List;

import static com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils.startActivity;

/**
 * Created by Administrator on 2018/5/25.
 */

public class SelectBookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private List<BookInfoResponse> bookInfoResponses;
    private Context mContext;
    private int columns;

    public SelectBookListAdapter(Context context, List<BookInfoResponse> responses, int columns) {
        this.bookInfoResponses = responses;
        this.columns = columns;
        this.mContext = context;
    }


    @Override
    public int getItemViewType(int position) {
        if (bookInfoResponses == null || bookInfoResponses.isEmpty()) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_book_list, parent, false);
            return new SelectBookListHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SelectBookListHolder) {
            BookInfoResponse bookInfo = bookInfoResponses.get(position);

            Glide.with(mContext)
                    .load(bookInfo.getImage())
                    .into(((SelectBookListHolder) holder).iv_book_img);
            ((SelectBookListHolder) holder).tv_book_title.setText(bookInfo.getTitle());
            ((SelectBookListHolder) holder).tv_book_title.setText(bookInfo.getTitle());
            ((SelectBookListHolder) holder).tv_book_info.setText(bookInfo.getInfoString());
            ((SelectBookListHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putSerializable(BookInfoResponse.serialVersionName, bookInfo);
                    Bitmap bitmap;
                    GlideBitmapDrawable imageDrawable = (GlideBitmapDrawable) ((SelectBookListHolder) holder).iv_book_img.getDrawable();
                    if (imageDrawable != null) {
                        bitmap = imageDrawable.getBitmap();
                        b.putParcelable("book_img", bitmap);
                    }

                    Intent intent = new Intent(UIUtils.getContext(), BookDynamicAddActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);

                    if (Activity.class.isInstance(mContext)) {
                        // 转化为activity，然后finish就行了
                        Activity activity = (Activity) mContext;
                        activity.finish();
                    }

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (bookInfoResponses.isEmpty()) {
            return 1;
        }
        return bookInfoResponses.size();
    }
    class SelectBookListHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_book_img;
        private final TextView tv_book_title;
        private final TextView tv_book_info;


        public SelectBookListHolder(View itemView) {
            super(itemView);
            iv_book_img = (ImageView) itemView.findViewById(R.id.iv_book_img);
            tv_book_title = (TextView) itemView.findViewById(R.id.tv_book_title);
            tv_book_info = (TextView) itemView.findViewById(R.id.tv_book_info);
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
