package com.silence.mymusic;

import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private ImageView mImageMenu, mImageGank, mImageOne, mImageDou;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_content);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mImageMenu = (ImageView) findViewById(R.id.image_title_menu);
        mImageGank = (ImageView) findViewById(R.id.image_title_gank);
        mImageOne = (ImageView) findViewById(R.id.image_title_one);
        mImageDou = (ImageView) findViewById(R.id.image_title_dou);
    }

    private void initListener() {
        mImageMenu.setOnClickListener(this);
        mImageGank.setOnClickListener(this);
        mImageOne.setOnClickListener(this);
        mImageDou.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }

    }
}
