package com.zucc.xwk_31401151.sharebookclient.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.zucc.xwk_31401151.sharebookclient.ui.fragment.Adapter;
import com.zucc.xwk_31401151.sharebookclient.R;
import com.zucc.xwk_31401151.sharebookclient.ui.fragment.Tab1Fragment;
import com.zucc.xwk_31401151.sharebookclient.ui.fragment.Tab2Fragment;
import com.zucc.xwk_31401151.sharebookclient.ui.fragment.Tab3Fragment;
import com.zucc.xwk_31401151.sharebookclient.ui.fragment.Tab4Fragment;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnTabSelectListener{

    @Titles
    private static final String[] mTitles = {"个人","动态","推荐","书房"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.mipmap.tab1_selected,R.mipmap.tab2_selected,R.mipmap.tab3_selected,R.mipmap.tab4_selected};

    @NorIcons
    private static final int[] mNormalIcons = {R.mipmap.tab1_normal, R.mipmap.tab2_normal, R.mipmap.tab3_normal, R.mipmap.tab4_normal};

    private List<Fragment> list = new ArrayList<>();

    private ViewPager mPager;

    private JPTabBar mTabbar;

    private Tab1Fragment mTab1;

    private Tab2Fragment mTab2;

    private Tab3Fragment mTab3;

    private Tab4Fragment mTab4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabbar = (JPTabBar) findViewById(R.id.tabbar);
        mPager = (ViewPager) findViewById(R.id.view_pager);

        mTab1 = new Tab1Fragment();
        mTab2 = new Tab2Fragment();
        mTab3 = new Tab3Fragment();
        mTab4 = new Tab4Fragment();
        mTabbar.setGradientEnable(true);
        mTabbar.setPageAnimateEnable(true);
        mTabbar.setTabListener(this);

        list.add(mTab1);
        list.add(mTab2);
        list.add(mTab3);
        list.add(mTab4);

        mPager.setAdapter(new Adapter(getSupportFragmentManager(),list));
        mTabbar.setContainer(mPager);
        mTabbar.setTabListener(this);
        if (mTabbar.getMiddleView()!=null){
            mTabbar.getMiddleView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,"123",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,CaptureActivity.class);
                    startActivity(intent);

                }
            });
        }
    }



    @Override
    public void onTabSelect(int index) {

    }

    @Override
    public boolean onInterruptSelect(int index) {
        return false;
    }
}
