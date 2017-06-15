package com.silence.mymusic.utils;

import android.util.Log;

/**
 * Created by wushiyu on 2017/6/12.
 */

public class DebugUtil {

    public static final String TAG = "wushiyu";

    public static final boolean DEBUG = true;

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }
    public static void i(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }
    public static void d(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void e(String tag, String error) {
        if (DEBUG)
            Log.e(tag, error);
    }
    public static void e(String error) {
        if (DEBUG)
            Log.e(TAG, error);
    }

}
