package com.silence.mymusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silence.mymusic.R;
import com.silence.mymusic.bean.movie.SubjectsBean;
import com.silence.mymusic.utils.ImgLoadUtil;
import com.silence.mymusic.utils.StringFormatUtil;

import java.util.List;

/**
 * Created by wushiyu on 2017/7/7.
 */

public class MovieRecycleViewAdapter extends RecyclerView.Adapter<MovieRecycleViewAdapter.MovieHolder> {

    private static final int TYPE_HEADER = 1001;
    private static final int TYPE_NORMAL = 1002;

    private Context mContext;
    private List<SubjectsBean> mData;
    private View mHeader;

    public MovieRecycleViewAdapter(Context context, List<SubjectsBean> data) {
        mContext = context;
        mData = data;
    }

    public void setHeader (View view) {
        mHeader = view;
    }

    public void addData (List<SubjectsBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeader != null && position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new MovieHolder(mHeader);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        int dataPosition = position;
        if (mHeader != null) {
            dataPosition --;
        }
        SubjectsBean data = mData.get(dataPosition);
        ImgLoadUtil.displayEspImage(data.getImages().getLarge(), holder.mImageMovie, 0);
        holder.mTextTitle.setText(data.getTitle());
        holder.mTextDirectors.setText(StringFormatUtil.formatName(data.getDirectors()));
        holder.mTextCasts.setText(StringFormatUtil.formatName(data.getCasts()));
        holder.mTextGenres.setText("类型：" + StringFormatUtil.formatGenres(data.getGenres()));
        holder.mTextGenres.setText("评分：" + data.getRating().getAverage());
    }



    @Override
    public int getItemCount() {
        if (mHeader != null) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView mImageMovie;
        private TextView mTextTitle, mTextDirectors, mTextCasts, mTextGenres, mTextRating;
        public MovieHolder(View itemView) {
            super(itemView);
            if (itemView == mHeader) {
                return;
            }
            mImageMovie = (ImageView) itemView.findViewById(R.id.image_movie_photo);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_movie_title);
            mTextDirectors = (TextView) itemView.findViewById(R.id.text_movie_directors);
            mTextCasts = (TextView) itemView.findViewById(R.id.text_movie_casts);
            mTextGenres = (TextView) itemView.findViewById(R.id.text_movie_genres);
            mTextRating = (TextView) itemView.findViewById(R.id.text_movie_rating);
        }
    }
}
