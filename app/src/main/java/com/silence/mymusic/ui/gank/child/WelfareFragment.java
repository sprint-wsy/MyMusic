package com.silence.mymusic.ui.gank.child;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
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
    private View mFooterView;
    private WelfareRecycleViewAdapter mRecycleViewAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private List<GankIoDataBean.ResultBean> mDataList;
    private int mPage = 1;
    private int mLastVisibleItem;
    private boolean mIsPrepared = false, mIsFirst = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
//        loadWelfareData();
        mIsPrepared = true;
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (!mIsVisible || !mIsPrepared || !mIsFirst) {
            return;
        }
        DebugUtil.i("welfare load data");
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
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.gank_item_footer, null, false);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mDataList = new ArrayList<GankIoDataBean.ResultBean>();
        mRecycleViewAdapter = new WelfareRecycleViewAdapter(getContext(), mDataList);
        mRecycleViewAdapter.setFooterView(mFooterView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
        //到底部自动加载更多
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 1 ==
                        mRecycleViewAdapter.getItemCount()) {
                    mPage ++;
                    loadWelfareData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPositions(null)[0];
            }
        });
    }

    private void loadWelfareData() {
        Call<GankIoDataBean> call = HttpUtils.getInstance().getGankIoData("福利", 20, mPage);
        call.enqueue(new Callback<GankIoDataBean>() {
            @Override
            public void onResponse(Call<GankIoDataBean> call, Response<GankIoDataBean> response) {
                List<GankIoDataBean.ResultBean> list = response.body().getResults();
                mRecycleViewAdapter.addData(list);
                showContentView();
                mIsFirst = false;
            }

            @Override
            public void onFailure(Call<GankIoDataBean> call, Throwable t) {
                DebugUtil.i("welfare failure");
                mPage = 1;
                showError();
            }
        });
    }
}
