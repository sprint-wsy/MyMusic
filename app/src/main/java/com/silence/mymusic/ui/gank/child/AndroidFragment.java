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

public class AndroidFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private View mFooterView;
    private CustomRecycleViewAdapter mAdapter;
    private List<GankIoDataBean.ResultBean> mData;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItem;  //用于上拉刷新
    private int mPage = 1;
    private boolean mIsPrepared = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
//        loadAndroidData();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_android;
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (!mIsVisible || !mIsPrepared) {
            loadAndroidData();
        }
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        showLoading();
        loadAndroidData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_android);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mData = new ArrayList<GankIoDataBean.ResultBean>();
        mAdapter = new CustomRecycleViewAdapter(getContext(), mData);
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.gank_item_footer, mRecyclerView, false);
        mAdapter.setFooter(mFooterView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 1 ==
                        mAdapter.getItemCount()) {
                    mPage ++;
                    loadAndroidData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    private void loadAndroidData() {
        Call<GankIoDataBean> call = HttpUtils.getInstance().getGankIoData("Android", 20, mPage);
        call.enqueue(new Callback<GankIoDataBean>() {
            @Override
            public void onResponse(Call<GankIoDataBean> call, Response<GankIoDataBean> response) {
                mAdapter.addData(response.body().getResults());
                showContentView();
            }

            @Override
            public void onFailure(Call<GankIoDataBean> call, Throwable t) {
                showError();
                mPage = 1;
            }
        });
    }

}
