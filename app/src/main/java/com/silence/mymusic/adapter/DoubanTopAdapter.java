package com.silence.mymusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silence.mymusic.R;
import com.silence.mymusic.bean.movie.SubjectsBean;
import com.silence.mymusic.ui.movie.MovieDetailActivity;
import com.silence.mymusic.utils.ImgLoadUtil;

import java.util.List;

/**
 * Created by wushiyu on 2017/7/12.
 */

public class DoubanTopAdapter extends RecyclerView.Adapter<DoubanTopAdapter.TopHolder> {

    private static final int TYPE_FOOTER = 1001;
    private static final int TYPE_NORMAL = 1002;

    private Context mContext;
    private List<SubjectsBean> mData;
    private View mFooter;

    public DoubanTopAdapter (Context context, List<SubjectsBean> data) {
        mContext = context;
        mData = data;
    }

    public void setFooter(View view) {
        mFooter = view;
    }

    public void addData(List<SubjectsBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<SubjectsBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mFooter != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public TopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new TopHolder(mFooter);
        }
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.douban_top_item, parent, false);
            return new TopHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final TopHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        final SubjectsBean data = mData.get(position);
        ImgLoadUtil.displayEspImage(data.getImages().getLarge(), holder.mImageView, 0);
        holder.mTextName.setText(data.getTitle());
        holder.mTextRate.setText("评分：" + data.getRating().getAverage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailActivity.start(mContext, data, holder.mImageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mFooter == null) {
            return mData.size();
        }
        return mData.size() + 1;
    }

    //当ViewType为Footer时，占用一整行
    @Override
    public void onViewAttachedToWindow(TopHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getItemViewType() == TYPE_FOOTER) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            }
        }
    }

    public class TopHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextName, mTextRate;

        public TopHolder(View itemView) {
            super(itemView);
            if (itemView == mFooter) {
                return;
            }

            mImageView = (ImageView) itemView.findViewById(R.id.image_top_photo);
            mTextName = (TextView) itemView.findViewById(R.id.text_top_name);
            mTextRate = (TextView) itemView.findViewById(R.id.text_top_rate);
        }
    }
}
