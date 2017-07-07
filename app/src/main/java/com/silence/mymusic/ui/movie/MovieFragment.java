package com.silence.mymusic.ui.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.silence.mymusic.R;
import com.silence.mymusic.adapter.MovieRecycleViewAdapter;
import com.silence.mymusic.base.BaseFragment;
import com.silence.mymusic.bean.movie.HotMovieBean;
import com.silence.mymusic.bean.movie.SubjectsBean;
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

public class MovieFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private View mHeaderView;
    private MovieRecycleViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<SubjectsBean> mDataList;
    private boolean mIsPrepared = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mIsPrepared = true;
    }

    @Override
    public int setContent() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (!mIsVisible || !mIsPrepared) {
            return;
        }
        loadMovieData();

    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        showLoading();
        loadMovieData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_movie);
        mDataList = new ArrayList<SubjectsBean>();
        mAdapter = new MovieRecycleViewAdapter(getContext(), mDataList);
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.movie_header, null, false);
        mAdapter.setHeader(mHeaderView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void loadMovieData() {
        Call<HotMovieBean> call = HttpUtils.getInstance().getHotMovie();
        call.enqueue(new Callback<HotMovieBean>() {
            @Override
            public void onResponse(Call<HotMovieBean> call, Response<HotMovieBean> response) {
                mAdapter.addData(response.body().getSubjects());
                showContentView();
            }

            @Override
            public void onFailure(Call<HotMovieBean> call, Throwable t) {
                DebugUtil.i("hot movie", "onFailure");
                showError();
            }
        });
    }
}
