package com.silence.mymusic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.silence.mymusic.R;
import com.silence.mymusic.bean.GankIoDataBean;
import com.silence.mymusic.ui.gank.BigImageActivity;
import com.silence.mymusic.utils.ImgLoadUtil;

import java.util.ArrayList;
import java.util.List;

import static com.silence.mymusic.ui.gank.BigImageActivity.INTENT_BUNDLE;
import static com.silence.mymusic.ui.gank.BigImageActivity.INTENT_IMAGE_URL;
import static com.silence.mymusic.ui.gank.BigImageActivity.INTENT_INDEX;

/**
 * Created by wushiyu on 2017/6/29.
 */

public class WelfareRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_IMAGE = 1001;
    public static final int TYPE_FOOTER = 1002;

    private Context mContext;
    private View mFooterView;

    private List<GankIoDataBean.ResultBean> mDataList;
    private ArrayList<String> mImageUrls;

    public WelfareRecycleViewAdapter(Context context, List<GankIoDataBean.ResultBean> list) {
        super();
        mContext = context;
        mDataList = list;
        mImageUrls = new ArrayList<String>();
        for (GankIoDataBean.ResultBean bean : mDataList) {
            mImageUrls.add(bean.getUrl());
        }
    }

    public void setFooterView (View view) {
        mFooterView = view;
    }

    public void addData(List<GankIoDataBean.ResultBean> list) {
        mDataList.addAll(list);
        for (GankIoDataBean.ResultBean bean : list) {
            mImageUrls.add(bean.getUrl());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mFooterView != null && position == mDataList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_IMAGE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_item, parent, false);
            return new WelfareHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mFooterView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_IMAGE) {
            ImgLoadUtil.displayWelfare(mDataList.get(position).getUrl(),((WelfareHolder)holder).mImageView);
            ((WelfareHolder)holder).mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BigImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(INTENT_INDEX, position);
                    bundle.putStringArrayList(INTENT_IMAGE_URL, mImageUrls);
                    intent.putExtra(INTENT_BUNDLE, bundle);
                    mContext.startActivity(intent);
                    ((Activity)mContext).overridePendingTransition(R.anim.push_fade_out, R.anim.push_fade_in);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mFooterView != null) {
            return mDataList.size() + 1;
        }
        return mDataList.size();
    }

    //当ViewType为Footer时，占用一整行
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getItemViewType() == TYPE_FOOTER) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    private class WelfareHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        public WelfareHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image_welfare);
        }
    }

    private class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }
}
