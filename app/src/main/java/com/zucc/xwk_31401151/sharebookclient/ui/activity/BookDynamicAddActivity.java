package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.xwk_31401151.sharebookclient.AppConstant;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.bean.http.douban.BookInfoResponse;
import com.zucc.xwk_31401151.sharebookclient.model.BaseResModel;
import com.zucc.xwk_31401151.sharebookclient.model.LoginModel;
import com.zucc.xwk_31401151.sharebookclient.utils.common.OkHttpUtil;
import com.zucc.xwk_31401151.sharebookclient.utils.common.SaveUserUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class BookDynamicAddActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_message_body)
    EditText tvMessageBody;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.iv_book_img)
    ImageView ivBookImg;
    @BindView(R.id.tv_book_title)
    TextView tvBookTitle;
    @BindView(R.id.tv_book_info)
    TextView tvBookInfo;
    @BindView(R.id.add_book)
    LinearLayout addBook;

    String bookinfoid;
    String userid;
    String body;


    private BookInfoResponse mBookInfoResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_dynamic_add);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {

        toolbar.setTitle("添加评论");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_dark);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.inflateMenu(R.menu.menu_book_detail);





        mBookInfoResponse = (BookInfoResponse) getIntent().getSerializableExtra(BookInfoResponse.serialVersionName);
        if (mBookInfoResponse.getBook_info_id()!=null){
            addBook.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.VISIBLE);
            String edt = SaveUserUtil.getInstance().getEdt(BookDynamicAddActivity.this);
            tvMessageBody.setText(edt);
            Glide.with(BookDynamicAddActivity.this)
                    .load(mBookInfoResponse.getImage())
                    .into(ivBookImg);
            tvBookTitle.setText( mBookInfoResponse.getTitle());
            tvBookInfo.setText(mBookInfoResponse.getInfoString());
            bookinfoid = mBookInfoResponse.getBook_info_id();
        }
        else {
            addBook.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
            bookinfoid=null;
        }


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_up:
//                        Toast.makeText(BookDynamicAddActivity.this, "提交", Toast.LENGTH_SHORT).show();
                        if (bookinfoid==null) {
                            Toast.makeText(BookDynamicAddActivity.this,"请选择你看过的书籍",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            update();
                            finish();
                        }

                        break;
                }
                return true;
            }
        });


    }

    private void update() {
        body = tvMessageBody.getText().toString();
        userid = SaveUserUtil.getInstance().getUser(BookDynamicAddActivity.this).getUser_id();
        System.out.println(body+"  "+"  "+userid+"  "+bookinfoid);
        String uri ;
        uri = AppConstant.getUrl() + "dynamic/add";
        System.out.println(uri);
        Map<String,String> map = new HashMap<>();
        map.put("bookinfoid",bookinfoid);
        map.put("userid",userid);
        map.put("body",body);
        OkHttpUtil.post(uri, new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"OnFailure:",e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG,responseData);
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<BaseResModel>() {}.getType();
                final BaseResModel baseResModel = gson.fromJson(responseData,type);
                if (baseResModel!=null) {
                    System.out.println(baseResModel.desc + baseResModel.status);
                }
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      if (baseResModel != null && baseResModel.status == 1000){

                                          Toast.makeText(BookDynamicAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                          finish();
                                      }
                                      else if (baseResModel != null){

                                          Toast.makeText(BookDynamicAddActivity.this, baseResModel.desc, Toast.LENGTH_SHORT).show();

                                      }
                                      else{

                                          Toast.makeText(BookDynamicAddActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                                      }


                                  }
                              }

                );


            }


        },map);











    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        String edt = String.valueOf(tvMessageBody.getText());
        SaveUserUtil.getInstance().saveEdt(BookDynamicAddActivity.this,edt);
        Intent intent = new Intent(BookDynamicAddActivity.this,SelectBookActivity.class);
        startActivity(intent);
        finish();
    }
}
