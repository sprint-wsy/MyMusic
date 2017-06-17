package com.silence.mymusic.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.silence.mymusic.R;
import com.silence.mymusic.base.BaseFragment;

/**
 * Created by wushiyu on 2017/6/17.
 */

public class RecommendFragment extends BaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_recommend;
    }
}
