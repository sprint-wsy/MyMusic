package com.silence.mymusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silence.mymusic.R;
import com.silence.mymusic.bean.GankIoDataBean;
import com.silence.mymusic.utils.ImgLoadUtil;

import java.util.List;

/**
 * Created by wushiyu on 2017/7/3.
 */

public class CustomRecycleViewAdapter extends RecyclerView.Adapter {

    public static final int TYPE_HEADER = 1001;
    public static final int TYPE_NORMAL = 1002;

    private View mHeader;
    private List<GankIoDataBean.ResultBean> mData;
    private Context mContext;
    private boolean mIsAllType;

    public CustomRecycleViewAdapter(Context context, List<GankIoDataBean.ResultBean> data) {
        super();
        mContext = context;
        mData = data;
    }

    public void setHeader(View view) {
        mHeader = view;
    }

    public void addData(List<GankIoDataBean.ResultBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setAllType(boolean isAllType) {
        mIsAllType = isAllType;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeader != null && position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new CustomHeaderHolder(mHeader);
        }
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item, parent, false);
            return new CustomHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        int dataPosition = position;
        if (mHeader != null) {
            dataPosition --;
        }

        GankIoDataBean.ResultBean data = mData.get(dataPosition);

        if ("福利".equals(data.getType())) {
            ((CustomHolder)holder).mLayoutNoWelfare.setVisibility(View.GONE);
            ((CustomHolder)holder).mImageWelfare.setVisibility(View.VISIBLE);
            ImgLoadUtil.displayWelfare(data.getUrl(),((CustomHolder)holder).mImageWelfare);
        } else {
            ((CustomHolder)holder).mLayoutNoWelfare.setVisibility(View.VISIBLE);
            ((CustomHolder)holder).mImageWelfare.setVisibility(View.GONE);
        }

        if (mIsAllType) {
            ((CustomHolder)holder).mTextType.setVisibility(View.VISIBLE);
            ((CustomHolder)holder).mTextType.setText("  ·  " + data.getType());
        } else {
            ((CustomHolder)holder).mTextType.setVisibility(View.GONE);
        }

        if (data.getImages() != null && data.getImages().size() > 0) {
            ((CustomHolder)holder).mImagePic.setVisibility(View.VISIBLE);
            ImgLoadUtil.displayGif(data.getImages().get(0), ((CustomHolder)holder).mImagePic);
        } else {
            ((CustomHolder)holder).mImagePic.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(data.getWho())) {
            ((CustomHolder)holder).mTextWho.setText("佚名");
        } else {
            ((CustomHolder)holder).mTextWho.setText(data.getWho());
        }

        ((CustomHolder)holder).mTextDesc.setText(data.getDesc());


    }

    @Override
    public int getItemCount() {
        if (mHeader == null) {
            return mData.size();
        }
        return mData.size() + 1;
    }

    private class CustomHolder extends RecyclerView.ViewHolder {
        TextView mTextDesc, mTextType, mTextTime, mTextWho;
        ImageView mImageWelfare, mImagePic;
        LinearLayout mLayoutNoWelfare;

        public CustomHolder(View itemView) {
            super(itemView);
            mTextDesc = (TextView) itemView.findViewById(R.id.text_custom_desc);
            mTextType = (TextView) itemView.findViewById(R.id.text_custom_type);
            mTextTime = (TextView) itemView.findViewById(R.id.text_custom_time);
            mTextWho = (TextView) itemView.findViewById(R.id.text_custom_who);
            mImageWelfare = (ImageView) itemView.findViewById(R.id.image_custom_welfare);
            mImagePic = (ImageView) itemView.findViewById(R.id.image_custom_pic);
            mLayoutNoWelfare = (LinearLayout) itemView.findViewById(R.id.layout_custom_no_welfare);
        }
    }

    private class CustomHeaderHolder extends RecyclerView.ViewHolder {
        TextView mTextName;
        LinearLayout mLayoutChoose;

        public CustomHeaderHolder(View itemView) {
            super(itemView);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mLayoutChoose = (LinearLayout) itemView.findViewById(R.id.layout_choose_category);
        }
    }
}
