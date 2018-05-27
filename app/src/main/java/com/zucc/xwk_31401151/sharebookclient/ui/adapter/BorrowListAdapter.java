package com.zucc.xwk_31401151.sharebookclient.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
import com.zucc.xwk_31401151.sharebookclient.bean.UserBean;
import com.zucc.xwk_31401151.sharebookclient.model.BaseResModel;
import com.zucc.xwk_31401151.sharebookclient.utils.common.OkHttpUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/24.
 */

public class BorrowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private Context mContext;
    private int columns;

    private List<ShareResponse> shareResponses;

    public BorrowListAdapter(Context context,List<ShareResponse> shareResponses ,int columns ){
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyShareListHolder) {
            ShareResponse shareResponse = shareResponses.get(position);

            Glide.with(mContext)
                    .load(shareResponse.getImage())
                    .into(((MyShareListHolder) holder).iv_book_img);

            ((MyShareListHolder) holder).tv_book_title.setText(shareResponse.getTitle());
            ((MyShareListHolder) holder).tv_book_info.setText(shareResponse.getAuthor());
            ((MyShareListHolder) holder).tv_book_description.setText("书籍拥有者：" + shareResponse.getOwner_name());
            ((MyShareListHolder) holder).iv_change_btn.setImageResource(R.mipmap.borrow_btn);

            //获取当前登录用户信息
            UserBean userBean=new UserBean();
            userBean = SaveUserUtil.getInstance().getUser(mContext);
            String userid = userBean.getUser_id();



            ((MyShareListHolder) holder).iv_change_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //根据图片的比较判断现在的状态
                    Drawable.ConstantState now =  ((MyShareListHolder) holder).iv_change_btn.getDrawable().getCurrent().getConstantState();
                    Drawable.ConstantState t1 = ContextCompat.getDrawable(mContext, R.mipmap.borrow_btn).getConstantState();
                    Drawable.ConstantState t2 = ContextCompat.getDrawable(mContext, R.mipmap.borrowed_now_btn).getConstantState();

                    if (now.equals(t1) ) {
//                        myborrowResponse.getStatus().equals("2")&&(myborrowResponse.getBorrow_time()!=null)
                        new MaterialDialog.Builder(mContext)
                                .title("书籍借阅确认")
                                .content("是否借阅《"+shareResponse.getTitle()+"》该书籍？")
                                .iconRes(R.drawable.icon)
                                .positiveText("我已确认")
                                .negativeText("我再考虑")
                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.POSITIVE) {
                                            Toast.makeText(mContext, "我已确认" , Toast.LENGTH_LONG).show();
                                            String book_id = shareResponse.getBook_id();
                                            String owner_id = shareResponse.getOwner_id();
                                            String book_info_id = shareResponse.getBook_info_id();

                                            System.out.println(userid+"  "+book_id+ "   "+book_info_id+"  "+owner_id);


                                            update(userid,book_id,book_info_id,owner_id);
                                            ((MyShareListHolder) holder).iv_change_btn.setImageResource(R.mipmap.borrowed_now_btn);


                                        } else if (which == DialogAction.NEGATIVE) {
                                            Toast.makeText(mContext, "我再考虑考虑", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .show();

                    }

                }
            });



        }


    }

    private void update(String userid, String book_id, String book_info_id, String owner_id) {

        String uri ;
        uri = AppConstant.getUrl() +"/borrowlist/borrow";
        System.out.println(uri);
        Map<String,String> map = new HashMap<>();
        System.out.println(userid+"  "+book_id+ "   "+book_info_id+"  "+owner_id);
        map.put("userid",userid);
        map.put("book_id",book_id);
        map.put("book_info_id",book_info_id);
        map.put("owner_id",owner_id);

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

    public int getItemColumnSpan(int position) {
        switch (getItemViewType(position)) {
            case TYPE_DEFAULT:
                return 1;
            default:
                return columns;
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
