package com.silence.mymusic;

import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.silence.mymusic.adapter.MyFragmentPagerAdapter;
import com.silence.mymusic.ui.book.BookFragment;
import com.silence.mymusic.ui.gank.GankFragment;
import com.silence.mymusic.ui.movie.MovieFragment;
import com.silence.mymusic.utils.ToastUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    private Toolbar mToolbar;
    private ImageView mImageMenu, mImageGank, mImageOne, mImageDou;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private GankFragment mGankFragment;
    private MovieFragment mMovieFragment;
    private BookFragment mBookFragment;

    private Handler mHandler = new Handler();
    private boolean isRealyExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initContentFragment();
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

    private void initContentFragment() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        mGankFragment = new GankFragment();
        mMovieFragment = new MovieFragment();
        mBookFragment = new BookFragment();
        fragmentList.add(mGankFragment);
        fragmentList.add(mMovieFragment);
        fragmentList.add(mBookFragment);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        mImageGank.setSelected(true);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image_title_gank:
                if (mViewPager.getCurrentItem() != 0) {
                    mImageGank.setSelected(true);
                    mImageOne.setSelected(false);
                    mImageDou.setSelected(false);
                    mViewPager.setCurrentItem(0);
                }
                break;
            case R.id.image_title_one:
                if (mViewPager.getCurrentItem() != 1) {
                    mImageGank.setSelected(false);
                    mImageOne.setSelected(true);
                    mImageDou.setSelected(false);
                    mViewPager.setCurrentItem(1);
                }
                break;
            case R.id.image_title_dou:
                if (mViewPager.getCurrentItem() != 2) {
                    mImageGank.setSelected(false);
                    mImageOne.setSelected(false);
                    mImageDou.setSelected(true);
                    mViewPager.setCurrentItem(2);
                }
                break;

        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:
                mImageGank.setSelected(true);
                mImageOne.setSelected(false);
                mImageDou.setSelected(false);
                break;
            case 1:
                mImageOne.setSelected(true);
                mImageGank.setSelected(false);
                mImageDou.setSelected(false);
                break;
            case 2:
                mImageDou.setSelected(true);
                mImageGank.setSelected(false);
                mImageOne.setSelected(false);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        if (isRealyExit) {
            finish();
        } else {
            isRealyExit = true;
            ToastUtil.showToast("再按一次退出");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isRealyExit = false;
                }
            }, 3000);
        }

    }
}
