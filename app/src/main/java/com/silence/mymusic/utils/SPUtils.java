package com.silence.mymusic.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.silence.mymusic.app.MyApplication;

/**
 * Created by wushiyu on 2017/6/12.
 */

public class SPUtils {

    private static final String CONFIG = "config";

    private static SharedPreferences getSharedPreference(String fileName) {
        return MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putString(key, value).apply();
    }

    public static String getString(String key, String defValue) {
        return getSharedPreference(CONFIG).getString(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreference(CONFIG).getBoolean(key, defValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue) {
        return getSharedPreference(CONFIG).getInt(key, defValue);
    }

    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putFloat(key, value).apply();
    }

    public static float getFloat(String key, float defValue) {
        return getSharedPreference(CONFIG).getFloat(key, defValue);
    }

    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreference(CONFIG).edit();
        editor.putLong(key, value).apply();
    }

    public static long getInt(String key, long defValue) {
        return getSharedPreference(CONFIG).getLong(key, defValue);
    }



}
