package com.silence.mymusic.ui.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.silence.mymusic.R;
import com.silence.mymusic.adapter.BigImageViewPagerAdapter;

import java.util.List;

/**
 * Created by wushiyu on 2017/6/30.
 */

public class BigImageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    public static final String INTENT_BUNDLE = "bundle";
    public static final String INTENT_INDEX = "index";
    public static final String INTENT_IMAGE_URL = "image_urls";

    private ViewPager mViewPager;
    private TextView mTextIndex;
    private TextView mTextSave;
    private BigImageViewPagerAdapter mAdapter;
    private List<String> mImageUrls;
    private int mCounts, mIndex;    //图片总数和当前位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        getIntentData();
        initView();
    }

    private void initView() {
        mViewPager =(ViewPager) findViewById(R.id.viewpager_big_image);
        mTextIndex = (TextView) findViewById(R.id.text_big_image_index);
        mTextSave = (TextView) findViewById(R.id.text_big_image_save);
        mAdapter = new BigImageViewPagerAdapter(this, mImageUrls);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mIndex);
        mViewPager.setOnPageChangeListener(this);
        mTextIndex.setText((mIndex + 1) + " / " + mCounts);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(INTENT_BUNDLE);
        mImageUrls = bundle.getStringArrayList(INTENT_IMAGE_URL);
        mIndex = bundle.getInt(INTENT_INDEX);
        mCounts = mImageUrls.size();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndex = position;
        mTextIndex.setText((mIndex + 1) + " / " + mCounts);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
