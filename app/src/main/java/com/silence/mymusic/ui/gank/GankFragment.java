package com.silence.mymusic.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.silence.mymusic.R;
import com.silence.mymusic.adapter.MyFragmentPagerAdapter;
import com.silence.mymusic.base.BaseFragment;
import com.silence.mymusic.ui.gank.child.AndroidFragment;
import com.silence.mymusic.ui.gank.child.CustomFragment;
import com.silence.mymusic.ui.gank.child.RecommendFragment;
import com.silence.mymusic.ui.gank.child.WelfareFragment;

import java.util.ArrayList;

/**
 * Created by wushiyu on 2017/6/17.
 */

public class GankFragment extends BaseFragment {

    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragmentList = new ArrayList<>(4);

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MyFragmentPagerAdapter mPagerAdapter;

    @Override
    public int setContent() {
        return R.layout.fragment_gank;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewPager = (ViewPager) getView().findViewById(R.id.viewpager_gank);
        mTabLayout = (TabLayout) getView().findViewById(R.id.tab_gank);
        showLoading();
        initFragmentList();

        mPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(),
                mFragmentList, mTitleList);
        mViewPager.setAdapter(mPagerAdapter);
        // 左右预加载页面的个数
        mViewPager.setOffscreenPageLimit(3);
        mPagerAdapter.notifyDataSetChanged();
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
        showContentView();
    }

    private void initFragmentList() {
        mTitleList.add("每日推荐");
        mTitleList.add("福利");
        mTitleList.add("干货定制");
        mTitleList.add("大安卓");
        mFragmentList.add(new RecommendFragment());
        mFragmentList.add(new WelfareFragment());
        mFragmentList.add(new CustomFragment());
        mFragmentList.add(new AndroidFragment());
    }

    public void jumpGankFragment(int position) {
        mViewPager.setCurrentItem(position, true);
    }
}
