package com.silence.mymusic.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.silence.mymusic.R;
import com.silence.mymusic.adapter.CustomRecycleViewAdapter;
import com.silence.mymusic.base.BaseFragment;
import com.silence.mymusic.bean.GankIoDataBean;
import com.silence.mymusic.utils.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wushiyu on 2017/6/17.
 */

public class CustomFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private View mHeaderView;
    private CustomRecycleViewAdapter mAdapter;
    private List<GankIoDataBean.ResultBean> mDataList;
    private int mPage = 1;
    private String mType = "all";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        loadCustomData();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        showLoading();
        loadCustomData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_custom);
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.custom_header, null, false);
        mDataList = new ArrayList<GankIoDataBean.ResultBean>();
        mAdapter = new CustomRecycleViewAdapter(getContext(), mDataList);
        mAdapter.setHeader(mHeaderView);
        mAdapter.setAllType(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadCustomData() {
        Call<GankIoDataBean> call = HttpUtils.getInstance().getGankIoData(mType, 20, mPage);
        call.enqueue(new Callback<GankIoDataBean>() {
            @Override
            public void onResponse(Call<GankIoDataBean> call, Response<GankIoDataBean> response) {
                mAdapter.addData(response.body().getResults());
                showContentView();
            }

            @Override
            public void onFailure(Call<GankIoDataBean> call, Throwable t) {
                onRefresh();
            }
        });
    }
}
