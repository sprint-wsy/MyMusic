package com.silence.mymusic.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.silence.mymusic.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by wushiyu on 2017/6/28.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.img_two_bi_one)
                .error(R.drawable.img_two_bi_one)
                .into(imageView);
    }
}
