package com.silence.mymusic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.silence.mymusic.R;
import com.silence.mymusic.utils.ImgLoadUtil;


/**
 * Created by wushiyu on 2017/7/4.
 */

public class DialogAdapter extends BaseAdapter {

    public static final String[] titles = {"全部", "iOS", "App", "前端", "休息视频", "拓展资源"};
    public static final int[] icons = {R.drawable.home_title_all, R.drawable.home_title_ios, R.drawable.home_title_app,
            R.drawable.home_title_qian, R.drawable.home_title_movie, R.drawable.home_title_source};

    public DialogAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dialog_item, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.icon_custom_dialog);
            TextView textView = (TextView) view.findViewById(R.id.title_custom_dialog);
            ImgLoadUtil.CommonLoadImage(parent.getContext(), icons[position], imageView);
            textView.setText(titles[position]);
            return view;
        }
    }
}
