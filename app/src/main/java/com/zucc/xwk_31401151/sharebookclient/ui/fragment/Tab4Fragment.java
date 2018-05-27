package com.zucc.xwk_31401151.sharebookclient.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.ui.activity.SearchResultActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4Fragment extends BaseFragment {


    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    Unbinder unbinder;

    String edt_search;
    @BindView(R.id.iv_fork_clear)
    ImageView ivForkClear;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.cardViewSearch)
    CardView cardViewSearch;

    public Tab4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);

        unbinder = ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    private void initUI() {

        etSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作

                    edt_search = etSearchContent.getText().toString();

                    if (edt_search.length() != 0) {

                        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                        intent.putExtra("q", edt_search);
                        intent.putExtra("enter", "search");
                        startActivity(intent);
                    } else if (edt_search.length()==0){
                        Toast.makeText(getActivity(),"请输入书名或ISBN号",Toast.LENGTH_LONG).show();
                    }


                }


                return false;
            }
        });

    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData(boolean isSavedNull) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        edt_search = etSearchContent.getText().toString();

        if (edt_search.length() != 0) {

            Intent intent = new Intent(getActivity(), SearchResultActivity.class);
            intent.putExtra("q", edt_search);
            intent.putExtra("enter", "search");
            startActivity(intent);
        } else if (edt_search.length()==0){
            Toast.makeText(getActivity(),"请输入书名或ISBN号",Toast.LENGTH_LONG).show();
        }

    }
}
