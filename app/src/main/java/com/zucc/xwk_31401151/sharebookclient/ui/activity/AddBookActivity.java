package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zucc.xwk_31401151.sharebookclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBookActivity extends BaseActivity  {


    @BindView(R.id.isbn_key)
    TextView isbnKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);
        String q = getIntent().getStringExtra("q");
        isbnKey.setText(q);
    }
}
