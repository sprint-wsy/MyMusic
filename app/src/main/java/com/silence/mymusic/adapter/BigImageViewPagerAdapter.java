package com.silence.mymusic.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.silence.mymusic.R;
import com.silence.mymusic.utils.ImgLoadUtil;
import com.silence.mymusic.utils.ToastUtil;

import java.util.List;

/**
 * Created by wushiyu on 2017/6/30.
 */

public class BigImageViewPagerAdapter extends PagerAdapter {

    private List<String> mImageUrls;
    private Context mContext;

    public BigImageViewPagerAdapter(Context context, List<String> imageUrls) {
        super();
        mContext = context;
        mImageUrls = imageUrls;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.big_image_item, container, false);
        PhotoView photoView = (PhotoView) itemView.findViewById(R.id.image_big);
        final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.loading_big_image);
        Glide.with(mContext)
                .load(mImageUrls.get(position))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        ToastUtil.showToast("资源加载异常");
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(photoView);
        // 启用图片缩放功能
        photoView.enable();
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)mContext).finish();
                ((Activity)mContext).overridePendingTransition(R.anim.push_fade_out, R.anim.push_fade_in);
            }
        });
        container.addView(itemView);
        return itemView;
    }


    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
