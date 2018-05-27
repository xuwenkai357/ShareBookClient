package com.zucc.xwk_31401151.sharebookclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.bean.MyborrowResponse;
import com.zucc.xwk_31401151.sharebookclient.model.BaseResModel;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.MessageDetailActivity;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.MessageListActivity;
import com.zucc.xwk_31401151.sharebookclient.utils.common.OkHttpUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.UIUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/23.
 */

public class MyBorrowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private Context mContext;
    private int columns;
    private List<MyborrowResponse> myborrowResponses;

    public MyBorrowListAdapter(Context context,List<MyborrowResponse> myborrowResponses ,int columns ){
        this.mContext = context;
        this.myborrowResponses = myborrowResponses;
        this.columns = columns;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;


        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_borrow_book_list, parent, false);
            return new MyBorrowListHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (myborrowResponses == null || myborrowResponses.isEmpty()) {
            return TYPE_EMPTY;
        } else {
            return TYPE_DEFAULT;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof  MyBorrowListHolder){
            MyborrowResponse myborrowResponse = myborrowResponses.get(position);

            Glide.with(mContext)
                    .load(myborrowResponse.getImage())
                    .into(((MyBorrowListHolder) holder).iv_book_img);

            ((MyBorrowListHolder) holder).tv_book_title.setText(myborrowResponse.getTitle());
            ((MyBorrowListHolder) holder).tv_book_info.setText(myborrowResponse.getAuthor());
            ((MyBorrowListHolder) holder).tv_book_description.setText("书籍拥有者："+myborrowResponse.getOwner_name());
            if (myborrowResponse.getComplete_status().equals("0")&&myborrowResponse.getStatus().equals("2")&&(myborrowResponse.getBorrow_time()!=null)&&(myborrowResponse.getConfine_time()==null)){
                ((MyBorrowListHolder) holder).iv_cencel_btn.setVisibility(View.GONE);
                ((MyBorrowListHolder) holder).iv_cencel_btn.setImageResource(R.mipmap.cencel_btn);
                ((MyBorrowListHolder) holder).iv_change_btn.setImageResource(R.mipmap.confine_btn);
            }
            else if (myborrowResponse.getComplete_status().equals("0")&&myborrowResponse.getStatus().equals("3")&&(myborrowResponse.getConfine_time()!=null)&&(myborrowResponse.getReturn_time()==null)){
                ((MyBorrowListHolder) holder).iv_cencel_btn.setVisibility(View.GONE);
                ((MyBorrowListHolder) holder).iv_change_btn.setImageResource(R.mipmap.return_btn);
            }
            else if (myborrowResponse.getComplete_status().equals("0")&&myborrowResponse.getStatus().equals("4")&&(myborrowResponse.getReturn_time()!=null)){
                ((MyBorrowListHolder) holder).iv_cencel_btn.setVisibility(View.GONE);
                ((MyBorrowListHolder) holder).iv_change_btn.setImageResource(R.mipmap.wait_confine_btn);
            }
            else if (myborrowResponse.getComplete_status().equals("1")){
                ((MyBorrowListHolder) holder).relat_btn.setVisibility(View.GONE);
                ((MyBorrowListHolder) holder).iv_cencel_btn.setVisibility(View.GONE);
                ((MyBorrowListHolder) holder).iv_change_btn.setVisibility(View.GONE);
            }
            else {
                ((MyBorrowListHolder) holder).relat_btn.setVisibility(View.GONE);
                ((MyBorrowListHolder) holder).iv_cencel_btn.setVisibility(View.GONE);
                ((MyBorrowListHolder) holder).iv_change_btn.setVisibility(View.GONE);
            }




            ((MyBorrowListHolder) holder).iv_change_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Drawable.ConstantState now =  ((MyBorrowListHolder) holder).iv_change_btn.getDrawable().getCurrent().getConstantState();
                    Drawable.ConstantState t1= ContextCompat.getDrawable(mContext,R.mipmap.confine_btn).getConstantState();
                    Drawable.ConstantState t2= ContextCompat.getDrawable(mContext,R.mipmap.return_btn).getConstantState();
                    Drawable.ConstantState t3= ContextCompat.getDrawable(mContext,R.mipmap.wait_confine_btn).getConstantState();

                    if (now.equals(t1)){
                        Log.i("图片对比","t1相同");
                    }
                    else if (now.equals(t2)){
                        Log.i("图片对比","t2相同");
                    }
                    else if (now.equals(t3)){
                        Log.i("图片对比","t3相同");
                    }


                    if (now.equals(t1) ) {
//                        myborrowResponse.getStatus().equals("2")&&(myborrowResponse.getBorrow_time()!=null)
                        new MaterialDialog.Builder(mContext)
                                .title("取书确认")
                                .iconRes(R.drawable.icon)
                                .content("请输入ISBN码，并点击确定")
//                                .widgetColor(Color.BLUE)//输入框光标的颜色
                                .inputType(InputType.TYPE_CLASS_PHONE)//可以输入的类型-电话号码
                                //前2个一个是hint一个是预输入的文字
                                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {

                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                                        Log.i("yqy", "输入的是：" + input);
                                        String input_toString = String.valueOf(input);
                                        Log.i("yqy", "对比的是：" + myborrowResponse.getIsbn13());
                                        if (input_toString.equals(myborrowResponse.getIsbn13())){
                                            Toast.makeText(mContext,"输入的ISBN码与该书的ISBN码相同",Toast.LENGTH_LONG).show();
                                            String api = "borrowlist/udtconfine";
                                            String borrow_id = myborrowResponse.getBorrow_id();
                                            String book_id = myborrowResponse.getBook_id();

                                            //交互更新服务端数据
                                            update(api,borrow_id,book_id);
                                            Toast.makeText(mContext,"取书成功，请开始阅读",Toast.LENGTH_LONG).show();
                                            ((MyBorrowListHolder) holder).iv_change_btn.setImageResource(R.mipmap.return_btn);
                                            ((MyBorrowListHolder) holder).iv_cencel_btn.setVisibility(View.GONE);

                                        }
                                        else {
                                            Toast.makeText(mContext,"输入的ISBN码与该书的ISBN码不相同，请确认书籍条形码上方的ISBN码",Toast.LENGTH_LONG).show();
                                        }

                                    }
                                })
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (dialog.getInputEditText().length() <=10) {

                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                                        }else {
                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                        }
                                    }
                                })
                                .show();

                    }
                    else if (now.equals(t2)  ){
//                        myborrowResponse.getStatus().equals("3")&&(myborrowResponse.getConfine_time()!=null)
                        new MaterialDialog.Builder(mContext)
                                .title("还书确认")
                                .iconRes(R.drawable.icon)
                                .content("请输入ISBN码，并点击确定")
//                                .widgetColor(Color.BLUE)//输入框光标的颜色
                                .inputType(InputType.TYPE_CLASS_PHONE)//可以输入的类型-电话号码
                                //前2个一个是hint一个是预输入的文字
                                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {

                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                                        Log.i("yqy", "输入的是：" + input);
                                        String input_toString = String.valueOf(input);
                                        Log.i("yqy", "对比的是：" + myborrowResponse.getIsbn13());
                                        if (input_toString.equals(myborrowResponse.getIsbn13())){
                                            Toast.makeText(mContext,"输入的ISBN码与该书的ISBN码相同",Toast.LENGTH_LONG).show();
                                            String api = "borrowlist/udtreturn";
                                            String borrow_id = myborrowResponse.getBorrow_id();
                                            String book_id = myborrowResponse.getBook_id();

                                            //交互更新服务端数据
                                            update(api,borrow_id,book_id);
                                            Toast.makeText(mContext,"还书成功，请等待拥有者确认",Toast.LENGTH_LONG).show();
                                            ((MyBorrowListHolder) holder).iv_cencel_btn.setVisibility(View.GONE);
                                            ((MyBorrowListHolder) holder).iv_change_btn.setImageResource(R.mipmap.wait_confine_btn);
                                        }
                                        else {
                                            Toast.makeText(mContext,"输入的ISBN码与该书的ISBN码不相同，请确认书籍条形码上方的ISBN码",Toast.LENGTH_LONG).show();
                                        }

                                    }
                                })
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (dialog.getInputEditText().length() <=10) {

                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                                        }else {
                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                        }
                                    }
                                })
                                .show();

                    }

                }
            });

        }




    }

    private void update(String api, String borrow_id, String book_id) {
        String uri ;
        uri = AppConstant.getUrl() + api;
        System.out.println(uri);
        Map<String,String> map = new HashMap<>();
        System.out.println(borrow_id+"  "+book_id);
        map.put("borrow_id",borrow_id);
        map.put("book_id",book_id);

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
        if (myborrowResponses.isEmpty()){
            return 1;
        }else {
            return myborrowResponses.size();
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




    class MyBorrowListHolder extends RecyclerView.ViewHolder {
        private TextView tv_book_title;
        private TextView tv_book_info;
        private TextView tv_book_description;
        private ImageView iv_book_img;
        private ImageView iv_cencel_btn;
        private ImageView iv_change_btn;
        private RelativeLayout relat_btn;


        public MyBorrowListHolder(View view) {
            super(view);
            tv_book_title = view.findViewById(R.id.tv_book_title);
            tv_book_info = view.findViewById(R.id.tv_book_info);
            tv_book_description = view.findViewById(R.id.tv_book_description);
            iv_book_img = view.findViewById(R.id.iv_book_img);
            iv_cencel_btn = view.findViewById(R.id.iv_cencel_btn);
            iv_change_btn = view.findViewById(R.id.iv_change_btn);
            relat_btn = view.findViewById(R.id.relat_btn);

        }
    }



}
