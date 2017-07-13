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
import com.silence.mymusic.bean.book.BookBean;
import com.silence.mymusic.utils.ImgLoadUtil;

import java.util.List;

/**
 * Created by wushiyu on 2017/7/13.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder>{

    private static final int TYPE_FOOTER = 1001;
    private static final int TYPE_NORMAL = 1002;

    private Context mContext;
    private List<BookBean> mData;
    private View mFooter;

    public BookAdapter (Context context, List<BookBean> data) {
        mContext = context;
        mData = data;
    }

    public void setFooter(View view) {
        mFooter = view;
    }

    public void addFooter() {
        if (mFooter != null) {
            return;
        }
        mFooter = LayoutInflater.from(mContext).inflate(R.layout.book_item_footer, null, false);
        notifyDataSetChanged();
    }

    public void addData(List<BookBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<BookBean> data) {
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
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new BookHolder(mFooter);
        }
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false);
            return new BookHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        final BookBean data = mData.get(position);
        ImgLoadUtil.displayEspImage(data.getImages().getLarge(), holder.mImageView, 2);
        holder.mTextName.setText(data.getTitle());
        holder.mTextRate.setText("评分：" + data.getRating().getAverage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    @Override
    public void onViewAttachedToWindow(BookHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getItemViewType() == TYPE_FOOTER) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            }
        }
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextName, mTextRate;

        public BookHolder(View itemView) {
            super(itemView);
            if (itemView == mFooter) {
                return;
            }

            mImageView = (ImageView) itemView.findViewById(R.id.image_book_photo);
            mTextName = (TextView) itemView.findViewById(R.id.text_book_name);
            mTextRate = (TextView) itemView.findViewById(R.id.text_book_rate);
        }
    }

}
