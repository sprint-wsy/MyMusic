package com.silence.mymusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silence.mymusic.R;
import com.silence.mymusic.bean.movie.PersonBean;
import com.silence.mymusic.ui.webview.WebViewActivity;
import com.silence.mymusic.utils.ImgLoadUtil;

import java.util.List;

/**
 * Created by wushiyu on 2017/7/10.
 */

public class MovieDetailCastsAdapter extends RecyclerView.Adapter<MovieDetailCastsAdapter.PersonHolder> {

    private List<PersonBean> mData;
    private Context mContext;

    public MovieDetailCastsAdapter(Context context, List<PersonBean> data) {
        mContext = context;
        mData = data;
    }

    public void addData(List<PersonBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonHolder(LayoutInflater.from(mContext).inflate(R.layout.item_movie_detail_person, parent, false));
    }

    @Override
    public void onBindViewHolder(PersonHolder holder, final int position) {
        holder.mTextName.setText(mData.get(position).getName());
        holder.mTextType.setText(mData.get(position).getType());
        ImgLoadUtil.CommonLoadImage(mContext, mData.get(position).getAvatars().getSmall(), holder.mImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(mContext, mData.get(position).getAlt(), mData.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class PersonHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextName, mTextType;
        public PersonHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image_avatar);
            mTextName = (TextView) itemView.findViewById(R.id.text_person_name);
            mTextType = (TextView) itemView.findViewById(R.id.text_person_type);
        }
    }
}
