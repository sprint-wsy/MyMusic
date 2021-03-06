package com.silence.mymusic.ui.gank;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.silence.mymusic.R;
import com.silence.mymusic.adapter.BigImageViewPagerAdapter;
import com.silence.mymusic.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by wushiyu on 2017/6/30.
 */

public class BigImageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    public static final String INTENT_BUNDLE = "bundle";
    public static final String INTENT_INDEX = "index";
    public static final String INTENT_IMAGE_URL = "image_urls";

    private ViewPager mViewPager;
    private TextView mTextIndex;
    private TextView mTextSave;
    private BigImageViewPagerAdapter mAdapter;
    private List<String> mImageUrls;
    private int mCounts, mIndex;    //图片总数和当前位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        getIntentData();
        initView();
    }

    private void initView() {
        mViewPager =(ViewPager) findViewById(R.id.viewpager_big_image);
        mTextIndex = (TextView) findViewById(R.id.text_big_image_index);
        mTextSave = (TextView) findViewById(R.id.text_big_image_save);
        mAdapter = new BigImageViewPagerAdapter(this, mImageUrls);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mIndex);
        mViewPager.setOnPageChangeListener(this);
        mTextIndex.setText((mIndex + 1) + " / " + mCounts);
        mTextSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String imagePath = getImagePath(mImageUrls.get(mIndex));
                        if (!TextUtils.isEmpty(imagePath)) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                            if (bitmap != null) {
                                saveImage(bitmap);
                                BigImageActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showToast("保存成功");
                                    }
                                });
                            }
                        }
                    }
                }).start();
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(INTENT_BUNDLE);
        mImageUrls = bundle.getStringArrayList(INTENT_IMAGE_URL);
        mIndex = bundle.getInt(INTENT_INDEX);
        mCounts = mImageUrls.size();
    }

    private void saveImage(Bitmap bitmap) {
        File dir = new File(Environment.getExternalStorageDirectory(), "云Music");
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(dir, fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath(String url) {
        String path  = null;
        FutureTarget<File> future = Glide.with(BigImageActivity.this)
                .load(url)
                .downloadOnly(500, 500);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return path;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_fade_out, R.anim.push_fade_in);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndex = position;
        mTextIndex.setText((mIndex + 1) + " / " + mCounts);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
