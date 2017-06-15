package com.silence.mymusic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silence.mymusic.base.BaseActivity;
import com.silence.mymusic.utils.DebugUtil;
import com.silence.mymusic.utils.ImgLoadUtil;

import java.util.Random;

/**
 * Created by wushiyu on 2017/6/16.
 */

public class SplashActivity extends BaseActivity{

    private int[] mDrawables = new int[]{R.drawable.b_1, R.drawable.b_2,
            R.drawable.b_3, R.drawable.b_4, R.drawable.b_5, R.drawable.b_6,
    };
    private boolean isToMain = false;
    private Handler mHandler;
    private Runnable mRunnable;

    private ImageView mImagePic;
    private TextView mTextJump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mImagePic = (ImageView) findViewById(R.id.image_pic);
        mTextJump = (TextView) findViewById(R.id.text_jump);
        init();
    }

    private void init() {
        int i = new Random().nextInt(mDrawables.length);
        ImgLoadUtil.CommonLoadImage(this, mDrawables[i], mImagePic);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        };
        mHandler.postDelayed(mRunnable, 3000);

        mTextJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });
    }

    private void toMainActivity() {
        if (isToMain) {
            return;
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.screan_zoom_in, R.anim.screan_zoom_out);
            isToMain = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DebugUtil.i("onDestroy");
        //防止在闪屏界面按返回键退出后，还会拉起主界面
        mHandler.removeCallbacks(mRunnable);
    }
}
