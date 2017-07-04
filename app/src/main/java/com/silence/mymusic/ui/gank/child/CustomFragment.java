package com.silence.mymusic.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.silence.mymusic.R;
import com.silence.mymusic.adapter.CustomRecycleViewAdapter;
import com.silence.mymusic.adapter.DialogAdapter;
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
    private View mFooterView;
    private TextView mHeaderName;
    private LinearLayout mHeaderChoose;
    private CustomRecycleViewAdapter mAdapter;
    private List<GankIoDataBean.ResultBean> mDataList;
    private int mPage = 1;
    private String mType = "all";
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItem;  //用于上拉刷新

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        loadCustomData(false);
    }

    @Override
    public int setContent() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        showLoading();
        loadCustomData(false);
    }

    private void initView() {
        initHeader();
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_custom);
        mDataList = new ArrayList<GankIoDataBean.ResultBean>();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomRecycleViewAdapter(getContext(), mDataList);
        mAdapter.setHeader(mHeaderView);
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
                    loadCustomData(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    private void initHeader() {
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.custom_header, null, false);
        mHeaderName = (TextView) mHeaderView.findViewById(R.id.text_name);
        mHeaderChoose = (LinearLayout) mHeaderView.findViewById(R.id.layout_choose_category);
        mHeaderChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        DialogAdapter adapter = new DialogAdapter();
        DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(new ListHolder())
                .setGravity(Gravity.BOTTOM)
                .setAdapter(adapter)
                .setHeader(R.layout.custom_dialog_header)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        mHeaderName.setText(item.toString());
                        if ("全部".equals(item.toString())) {
                            mType = "all";
                        } else {
                            mType = item.toString();
                        }
                        dialog.dismiss();
                        showLoading();
                        loadCustomData(false);
                    }
                })
                .create();
        dialog.show();
    }


    /**
     *
     * @param isLoadMore 是通过上拉加载更多，还是选择分类后重新加载
     */
    private void loadCustomData(final boolean isLoadMore) {
        Call<GankIoDataBean> call = HttpUtils.getInstance().getGankIoData(mType, 20, mPage);
        call.enqueue(new Callback<GankIoDataBean>() {
            @Override
            public void onResponse(Call<GankIoDataBean> call, Response<GankIoDataBean> response) {
                if (isLoadMore) {
                    mAdapter.addData(response.body().getResults());
                } else {
                    mAdapter.setData(response.body().getResults());
                }
                showContentView();
            }

            @Override
            public void onFailure(Call<GankIoDataBean> call, Throwable t) {
                showError();
                if (isLoadMore) {
                    mPage --;
                } else {
                    mPage = 1;
                }
            }
        });
    }


}
