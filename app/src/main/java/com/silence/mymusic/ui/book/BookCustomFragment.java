package com.silence.mymusic.ui.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.silence.mymusic.R;
import com.silence.mymusic.adapter.BookAdapter;
import com.silence.mymusic.base.BaseFragment;
import com.silence.mymusic.bean.book.BookBean;
import com.silence.mymusic.bean.book.BookListBean;
import com.silence.mymusic.utils.CommonUtils;
import com.silence.mymusic.utils.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wushiyu on 2017/7/13.
 */

public class BookCustomFragment extends BaseFragment {

    public static final String BUNDLE_TYPE = "type";
    private String mType = "综合";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private BookAdapter mAdapter;
    private boolean mIsPrepared = false;
    private boolean mIsFirst = true;
    private int mStart = 0, mCount = 18;
    private int mLastVisibleItem;

    @Override
    public int setContent() {
        return R.layout.fragment_book_custom;
    }

    public static BookCustomFragment newInstance(String type) {
        BookCustomFragment fragment = new BookCustomFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(BUNDLE_TYPE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initView();
        mIsPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        mIsFirst = false;
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadBookData(false);
            }
        }, 500);
    }

    @Override
    protected void onRefresh() {
        showContentView();
        mSwipeRefreshLayout.setRefreshing(true);
        mStart = 0;
        loadBookData(false);
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mStart = 0;
                        loadBookData(false);
                    }
                }, 1000);
            }
        });

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_book);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<BookBean> bookBeen = new ArrayList<>();
        mAdapter = new BookAdapter(getContext(), bookBeen);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 1 ==
                        mAdapter.getItemCount()) {
                    mStart = mStart + mCount;
                    loadBookData(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPositions(null)[0];
            }
        });
    }

    private void loadBookData(final boolean isLoadMore) {
        Call<BookListBean> call = HttpUtils.getInstance().getBook(mType, mStart, mCount);
        call.enqueue(new Callback<BookListBean>() {
            @Override
            public void onResponse(Call<BookListBean> call, Response<BookListBean> response) {
                List<BookBean> data = response.body().getBooks();
                if (isLoadMore) {
                    mAdapter.addData(data);
                } else {
                    mAdapter.setData(data);
                }
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                mAdapter.addFooter();
            }

            @Override
            public void onFailure(Call<BookListBean> call, Throwable t) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                showError();
                mStart = 0;
            }
        });
    }
}
