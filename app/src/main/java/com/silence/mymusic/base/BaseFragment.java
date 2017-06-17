package com.silence.mymusic.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.silence.mymusic.R;

/**
 * Created by wushiyu on 2017/6/16.
 */

public abstract class BaseFragment extends Fragment {

    protected boolean mIsVisible = false;

    //加载中
    protected LinearLayout mLayoutProgress;
    //加载失败
    protected LinearLayout mLayoutRefresh;
    private ImageView mImageProgress;
    private AnimationDrawable mAnimationDrawable;

    protected RelativeLayout mContainer;
    //内容布局
    protected View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, null);
        mContentView  = getActivity().getLayoutInflater().inflate(setContent(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer = (RelativeLayout) view.findViewById(R.id.container);
        mContainer.addView(mContentView,params);
        return view;
    }

    public abstract int setContent();

    //Fragment的懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        loadData();
    }

    protected void onInvisible() {}

    protected void loadData() {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLayoutProgress = (LinearLayout) getView().findViewById(R.id.ll_progress_bar);
        mLayoutRefresh = (LinearLayout) getView().findViewById(R.id.ll_error_refresh);
        mImageProgress = (ImageView) getView().findViewById(R.id.image_progress);
        //加载动画
        mAnimationDrawable = (AnimationDrawable) mImageProgress.getDrawable();
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        mLayoutRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onRefresh();
            }
        });
        mContentView.setVisibility(View.GONE);
    }

    protected void onRefresh() {}

    /**
     * 显示加载中状态
     */
    protected void showLoading() {
        if (mLayoutProgress.getVisibility() != View.VISIBLE) {
            mLayoutProgress.setVisibility(View.VISIBLE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (mContentView.getVisibility() != View.GONE) {
            mContentView.setVisibility(View.GONE);
        }
        if (mLayoutRefresh.getVisibility() != View.GONE) {
            mLayoutRefresh.setVisibility(View.GONE);
        }
    }

    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (mLayoutProgress.getVisibility() != View.GONE) {
            mLayoutProgress.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mLayoutRefresh.getVisibility() != View.GONE) {
            mLayoutRefresh.setVisibility(View.GONE);
        }
        if (mContentView.getVisibility() != View.VISIBLE) {
            mContentView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        if (mLayoutProgress.getVisibility() != View.GONE) {
            mLayoutProgress.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mLayoutRefresh.getVisibility() != View.VISIBLE) {
            mLayoutRefresh.setVisibility(View.VISIBLE);
        }
        if (mContentView.getVisibility() != View.GONE) {
            mContentView.setVisibility(View.GONE);
        }
    }
}
