package com.silence.mymusic.ui.gank.child;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.silence.mymusic.R;
import com.silence.mymusic.adapter.WelfareRecycleViewAdapter;
import com.silence.mymusic.base.BaseFragment;
import com.silence.mymusic.bean.GankIoDataBean;
import com.silence.mymusic.utils.DebugUtil;
import com.silence.mymusic.utils.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wushiyu on 2017/6/17.
 */

public class WelfareFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private WelfareRecycleViewAdapter mRecycleViewAdapter;
    private List<GankIoDataBean.ResultBean> mDataList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        loadWelfareData();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        showLoading();
        loadWelfareData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_welfare);
        mDataList = new ArrayList<GankIoDataBean.ResultBean>();
        mRecycleViewAdapter = new WelfareRecycleViewAdapter(mDataList);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mRecycleViewAdapter);
    }

    private void loadWelfareData() {
        Call<GankIoDataBean> call = HttpUtils.getInstance().getGankIoData("福利", 20, 1);
        call.enqueue(new Callback<GankIoDataBean>() {
            @Override
            public void onResponse(Call<GankIoDataBean> call, Response<GankIoDataBean> response) {
                List<GankIoDataBean.ResultBean> list = response.body().getResults();
                mRecycleViewAdapter.addData(list);
                mRecycleViewAdapter.notifyDataSetChanged();
                showContentView();
            }

            @Override
            public void onFailure(Call<GankIoDataBean> call, Throwable t) {
                DebugUtil.i("welfare failure");
                showError();
            }
        });
    }
}
