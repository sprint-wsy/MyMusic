package com.silence.mymusic.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.silence.mymusic.app.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by wushiyu on 2017/6/12.
 */

public class CommonUtils {

    /**
     * 随机颜色
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;
        int green = random.nextInt(150) + 50;
        int blue = random.nextInt(150) + 50;
        return Color.rgb(red, green, blue);
    }

    static public int getScreenWidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }

    static public int getScreenHeightPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.heightPixels;
    }

    public static Resources getResoure() {
        return MyApplication.getInstance().getResources();
    }

    public static Drawable getDrawable(int resId) {
        return ResourcesCompat.getDrawable(getResoure(), resId, null);
    }

    public static int getColor(int resId) {
        return ResourcesCompat.getColor(getResoure(),resId, null);
    }

    public static String getString(int resId) {
        return getResoure().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return getResoure().getStringArray(resId);
    }

    public static float getDimens(int resId) {
        return getResoure().getDimension(resId);
    }

}
