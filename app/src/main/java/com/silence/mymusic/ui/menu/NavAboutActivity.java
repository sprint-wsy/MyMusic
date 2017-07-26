package com.silence.mymusic.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silence.mymusic.R;
import com.silence.mymusic.base.BaseActivity;
import com.silence.mymusic.utils.ImgLoadUtil;

/**
 * Created by wushiyu on 2017/7/26.
 */

public class NavAboutActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ImageView mIcon;
    private TextView mTextGankio, mTextDouban;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_about);
        mIcon = (ImageView) findViewById(R.id.image_icon);
        mTextGankio = (TextView) findViewById(R.id.text_gankio);
        mTextDouban = (TextView) findViewById(R.id.text_douban);

        setToolbar();
        ImgLoadUtil.CommonLoadImage(this, R.drawable.ic_cloudreader, mIcon);
        mTextGankio.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTextGankio.getPaint().setAntiAlias(true);//抗锯齿
        mTextDouban.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTextDouban.getPaint().setAntiAlias(true);//抗锯齿
        setListener();
    }

    private void setListener() {
        mTextGankio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://gank.io/api");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mTextDouban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://developers.douban.com/wiki/?title=terms");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
        mToolbar.setTitle("关于 云Music");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    static public void start(Context context) {
        Intent intent = new Intent(context, NavAboutActivity.class);
        context.startActivity(intent);
    }
}
