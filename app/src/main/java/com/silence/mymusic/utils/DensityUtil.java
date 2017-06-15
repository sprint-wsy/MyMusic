package com.silence.mymusic.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wushiyu on 2017/6/12.
 */

public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位转化为 px
     */
    public static int dip2px(float dpValue) {
        float scale = CommonUtils.getResoure().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px 的单位转化为 dp
     */
    public static int px2dip(float pxValue) {
        float scale = CommonUtils.getResoure().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static ViewGroup.LayoutParams setViewMargin(View view, boolean isDp, int left, int right,
                                                       int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        if (isDp) {
            leftPx = dip2px(left);
            rightPx = dip2px(right);
            topPx = dip2px(top);
            bottomPx = dip2px(bottom);
        }

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = null;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        } else {
            marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
        }

        marginLayoutParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginLayoutParams);
        view.requestLayout();
        return marginLayoutParams;
    }
}
