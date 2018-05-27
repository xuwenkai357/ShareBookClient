package com.zucc.xwk_31401151.sharebookclient.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.bean.ShareResponse;
import com.zucc.xwk_31401151.sharebookclient.model.BaseResModel;
import com.zucc.xwk_31401151.sharebookclient.utils.common.OkHttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/24.
 */

public class MyShareListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private Context mContext;
    private int columns;

    private List<ShareResponse> shareResponses;

    public MyShareListAdapter(Context context,List<ShareResponse> shareResponses ,int columns ){
        this.mContext = context;
        this.shareResponses = shareResponses;
        this.columns = columns;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;


        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share_book_list, parent, false);
            return new MyShareListHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (shareResponses == null || shareResponses.isEmpty()) {
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
        if(holder instanceof  MyShareListHolder) {
            ShareResponse shareResponse = shareResponses.get(position);

            Glide.with(mContext)
                    .load(shareResponse.getImage())
                    .into(((MyShareListHolder) holder).iv_book_img);

            ((MyShareListHolder) holder).tv_book_title.setText(shareResponse.getTitle());
            ((MyShareListHolder) holder).tv_book_info.setText(shareResponse.getAuthor());
            if(shareResponse.getBorrower_name()!=null) {
                ((MyShareListHolder) holder).tv_book_description.setText("书籍借阅者：" + shareResponse.getBorrower_name());
            }
            else
            {
                ((MyShareListHolder) holder).tv_book_description.setText("该书暂时无人借阅");
            }

            if (shareResponse.getStatus().equals("1")) {

                ((MyShareListHolder) holder).iv_change_btn.setImageResource(R.mipmap.cencel_borrow_btn);
            } else if (shareResponse.getStatus().equals("0")) {

                ((MyShareListHolder) holder).iv_change_btn.setImageResource(R.mipmap.start_borrow_btn);
            } else if (shareResponse.getStatus().equals("2")||shareResponse.getStatus().equals("3") ) {

                ((MyShareListHolder) holder).iv_change_btn.setImageResource(R.mipmap.borrowed_btn);
            } else if (shareResponse.getStatus().equals("4") ) {

                ((MyShareListHolder) holder).iv_change_btn.setImageResource(R.mipmap.return_confine_btn);
            }


            ((MyShareListHolder) holder).iv_change_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //根据图片的比较判断现在的状态
                    Drawable.ConstantState now =  ((MyShareListHolder) holder).iv_change_btn.getDrawable().getCurrent().getConstantState();
                    Drawable.ConstantState t1 = ContextCompat.getDrawable(mContext, R.mipmap.cencel_borrow_btn).getConstantState();
                    Drawable.ConstantState t2 = ContextCompat.getDrawable(mContext, R.mipmap.start_borrow_btn).getConstantState();
                    Drawable.ConstantState t3 = ContextCompat.getDrawable(mContext, R.mipmap.borrowed_btn).getConstantState();
                    Drawable.ConstantState t4 = ContextCompat.getDrawable(mContext, R.mipmap.return_confine_btn).getConstantState();

                    if (now.equals(t1) ) {
//                        myborrowResponse.getStatus().equals("2")&&(myborrowResponse.getBorrow_time()!=null)
                        new MaterialDialog.Builder(mContext)
                                .title("取消书籍借阅确认")
                                .content("是否取消《"+shareResponse.getTitle()+"》该书籍的借阅？")
                                .iconRes(R.drawable.icon)
                                .positiveText("我已确认")
                                .negativeText("我再考虑")
                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.POSITIVE) {
                                            Toast.makeText(mContext, "我已确认" , Toast.LENGTH_LONG).show();
                                            String book_id = shareResponse.getBook_id();
                                            String status = "0";

                                            String borrow_id;
                                            if (shareResponse.getBorrow_id()!=null){
                                                borrow_id = shareResponse.getBorrow_id();
                                            }
                                            else {
                                                borrow_id = null;
                                            }

                                            update(book_id,status,borrow_id);
                                            ((MyShareListHolder) holder).iv_change_btn.setImageResource(R.mipmap.start_borrow_btn);


                                        } else if (which == DialogAction.NEGATIVE) {
                                            Toast.makeText(mContext, "我再考虑考虑", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .show();

                    }
                    else if (now.equals(t2)  ){
//                        myborrowResponse.getStatus().equals("3")&&(myborrowResponse.getConfine_time()!=null)
                        new MaterialDialog.Builder(mContext)
                                .title("开始书籍借阅确认")
                                .content("是否开始《"+shareResponse.getTitle()+"》该书籍的借阅？")
                                .iconRes(R.drawable.icon)
                                .positiveText("我已确认")
                                .negativeText("我再考虑")
                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.POSITIVE) {
                                            Toast.makeText(mContext, "我已确认" , Toast.LENGTH_LONG).show();
                                            String book_id = shareResponse.getBook_id();
                                            String status = "1";

                                            String borrow_id;
                                            if (shareResponse.getBorrow_id()!=null){
                                                borrow_id = shareResponse.getBorrow_id();
                                            }
                                            else {
                                                borrow_id = null;
                                            }



                                            update(book_id,status,borrow_id);
                                            ((MyShareListHolder) holder).iv_change_btn.setImageResource(R.mipmap.cencel_borrow_btn);


                                        } else if (which == DialogAction.NEGATIVE) {
                                            Toast.makeText(mContext, "我再考虑考虑", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .show();

                    }
                    else if (now.equals(t4)  ){
//                        myborrowResponse.getStatus().equals("3")&&(myborrowResponse.getConfine_time()!=null)
                        new MaterialDialog.Builder(mContext)
                                .title("书籍归还确认")
                                .content("是否确认收到《"+shareResponse.getTitle()+"》该书籍的归还？")
                                .iconRes(R.drawable.icon)
                                .positiveText("我已收到")
                                .negativeText("我还未收到")
                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.POSITIVE) {
                                            Toast.makeText(mContext, "我已收到" , Toast.LENGTH_LONG).show();
                                            String book_id = shareResponse.getBook_id();
                                            String status = "1";

                                            String borrow_id;
                                            if (shareResponse.getBorrow_id()!=null){
                                                borrow_id = shareResponse.getBorrow_id();
                                            }
                                            else {
                                                borrow_id = null;
                                            }

                                            update(book_id,status,borrow_id);
                                            ((MyShareListHolder) holder).iv_change_btn.setImageResource(R.mipmap.cencel_borrow_btn);


                                        } else if (which == DialogAction.NEGATIVE) {
                                            Toast.makeText(mContext, "我还未收到", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .show();

                    }






                }
            });



        }


    }

    private void update(String book_id, String status,String borrow_id) {

        String uri ;
        uri = AppConstant.getUrl() +"/booklist/update";
        System.out.println(uri);
        Map<String,String> map = new HashMap<>();
        System.out.println(status+"  "+book_id);
        map.put("book_id",book_id);
        map.put("status",status);
        map.put("borrow_id",borrow_id);

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

    @Override
    public int getItemCount() {
        if (shareResponses.isEmpty()){
            return 1;
        }else {
            return shareResponses.size();
        }
    }




    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }

    private class MyShareListHolder extends RecyclerView.ViewHolder {

        private TextView tv_book_title;
        private TextView tv_book_info;
        private TextView tv_book_description;
        private ImageView iv_book_img;

        private ImageView iv_change_btn;


        public MyShareListHolder(View view) {
            super(view);

            tv_book_title = view.findViewById(R.id.tv_book_title);
            tv_book_info = view.findViewById(R.id.tv_book_info);
            tv_book_description = view.findViewById(R.id.tv_book_description);
            iv_book_img = view.findViewById(R.id.iv_book_img);

            iv_change_btn = view.findViewById(R.id.iv_change_btn);
        }
    }
}
