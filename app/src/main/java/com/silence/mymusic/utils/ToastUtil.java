package com.silence.mymusic.utils;

import android.widget.Toast;

import com.silence.mymusic.app.MyApplication;

/**
 * Created by wushiyu on 2017/6/15.
 */

public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

}
