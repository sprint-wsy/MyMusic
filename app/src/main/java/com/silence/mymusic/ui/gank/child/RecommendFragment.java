package com.silence.mymusic.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.silence.mymusic.R;
import com.silence.mymusic.base.BaseFragment;

/**
 * Created by wushiyu on 2017/6/17.
 */

public class RecommendFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private LinearLayout mLoadingLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_recommend);
        mLoadingLayout = (LinearLayout) getView().findViewById(R.id.layout_loading);
    }

    @Override
    public int setContent() {
        return R.layout.fragment_recommend;
    }

    private void initRecycleView() {

    }
}
