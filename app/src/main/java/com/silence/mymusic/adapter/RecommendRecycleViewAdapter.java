package com.silence.mymusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wushiyu on 2017/6/19.
 */

public class RecommendRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;    //头布局
    public static final int TYPE_TITLE = 1;     //标题
    public static final int TYPE_ONE = 2;       //一张图
    public static final int TYPE_TWO = 3;       //两张图
    public static final int TYPE_THREE = 4;     //三张图
    public static final int TYPE_FOOTER = 5;    //尾部布局

    private View mHeaderView;
    private View mFooterView;

    public void setHeaderView(View view) {
        mHeaderView = view;
    }

    public void setFooterView(View view) {
        mFooterView = view;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        } else if (mFooterView != null && position == (getItemCount() -1 )) {
            return TYPE_FOOTER;
        } else {

        }

        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
