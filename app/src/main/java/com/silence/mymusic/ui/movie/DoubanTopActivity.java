package com.silence.mymusic.ui.movie;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.silence.mymusic.R;
import com.silence.mymusic.adapter.DoubanTopAdapter;
import com.silence.mymusic.bean.movie.HotMovieBean;
import com.silence.mymusic.bean.movie.SubjectsBean;
import com.silence.mymusic.utils.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wushiyu on 2017/7/12.
 */

public class DoubanTopActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private View mFooterView;
    private DoubanTopAdapter mAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private List<SubjectsBean> mDataList;
    private Toolbar mToolbar;
    private LinearLayout mProgressBar, mRefreshLayout;
    private ImageView mImageProgress;
    private AnimationDrawable mAnimationDrawable;

    private int mStart = 0, mCount = 21;
    private int mLastVisibleItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban_top);
        initView();
        loadTopData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_top);
        mRefreshLayout = (LinearLayout) findViewById(R.id.layout_error_refresh);
        mProgressBar = (LinearLayout) findViewById(R.id.layout_progress_bar);
        mImageProgress = (ImageView) findViewById(R.id.image_progress);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_top);

        //加载动画
        mAnimationDrawable = (AnimationDrawable) mImageProgress.getDrawable();
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        setToolbar();
        mRefreshLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onRefresh();
            }
        });
        initRecycleView();
    }

    private void initRecycleView() {
        mDataList = new ArrayList<>();
        mAdapter = new DoubanTopAdapter(this, mDataList);
        mFooterView = LayoutInflater.from(this).inflate(R.layout.gank_item_footer, null, false);
        mAdapter.setFooter(mFooterView);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 1 ==
                        mAdapter.getItemCount()) {
                    mStart = mStart + mCount;
                    loadTopData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPositions(null)[0];
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
        mToolbar.setTitle("豆瓣电影Top250");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadTopData() {
        Call<HotMovieBean> call = HttpUtils.getInstance().getDoubanTop(mStart, mCount);
        call.enqueue(new Callback<HotMovieBean>() {
            @Override
            public void onResponse(Call<HotMovieBean> call, Response<HotMovieBean> response) {
                List<SubjectsBean> data = response.body().getSubjects();
                mAdapter.addData(data);
                showContentView();
            }

            @Override
            public void onFailure(Call<HotMovieBean> call, Throwable t) {
                showError();
                mStart = 0;
            }
        });
    }

    protected void showLoading() {
        if (mProgressBar.getVisibility() != View.VISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (mRecyclerView.getVisibility() != View.GONE) {
            mRecyclerView.setVisibility(View.GONE);
        }
        if (mRefreshLayout.getVisibility() != View.GONE) {
            mRefreshLayout.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if (mProgressBar.getVisibility() != View.GONE) {
            mProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mRefreshLayout.getVisibility() != View.GONE) {
            mRefreshLayout.setVisibility(View.GONE);
        }
        if (mRecyclerView.getVisibility() != View.VISIBLE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    protected void showError() {
        if (mProgressBar.getVisibility() != View.GONE) {
            mProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mRefreshLayout.getVisibility() != View.VISIBLE) {
            mRefreshLayout.setVisibility(View.VISIBLE);
        }
        if (mRecyclerView.getVisibility() != View.GONE) {
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    private void onRefresh() {
        showLoading();
        loadTopData();
    }
}
