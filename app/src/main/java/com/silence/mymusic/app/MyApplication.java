package com.silence.mymusic.app;

import android.app.Application;

/**
 * Created by wushiyu on 2017/6/12.
 */

public class MyApplication extends Application {

    private static MyApplication mMyApplication;

    public static MyApplication getInstance() {
        if (mMyApplication == null) {
            synchronized (MyApplication.class) {
                if (mMyApplication == null) {
                    mMyApplication = new MyApplication();
                }
            }
        }
        return mMyApplication;
    }

}
