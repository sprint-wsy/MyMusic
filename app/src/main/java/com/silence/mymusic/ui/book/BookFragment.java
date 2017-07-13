package com.silence.mymusic.ui.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.silence.mymusic.R;
import com.silence.mymusic.adapter.MyFragmentPagerAdapter;
import com.silence.mymusic.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by wushiyu on 2017/6/17.
 */

public class BookFragment extends BaseFragment {

    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
        initFragmentList();
        initView();
        showContentView();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_book;
    }

    private void initFragmentList() {
        mTitleList.add("综合");
        mTitleList.add("文学");
        mTitleList.add("生活");
        mFragments.add(BookCustomFragment.newInstance("综合"));
        mFragments.add(BookCustomFragment.newInstance("文学"));
        mFragments.add(BookCustomFragment.newInstance("生活"));
    }

    private void initView() {
        mViewPager = (ViewPager) getView().findViewById(R.id.viewpager_book);
        mTabLayout = (TabLayout) getView().findViewById(R.id.tab_book);
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mViewPager.setAdapter(myAdapter);
        mViewPager.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
