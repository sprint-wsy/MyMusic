package com.silence.mymusic.utils;

import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wushiyu on 2017/6/12.
 */

public class TimeUtil {

    private static long timeMilliseconds;

    public static String getTranslateTime(String time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date nowTime = new Date();
        String currDate = sdf1.format(nowTime);
        long currentMilliseconds = nowTime.getTime();

        Date date = null;
        try {
            date = sdf1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            timeMilliseconds = date.getTime();
        }

        long timeDifferent = currentMilliseconds - timeMilliseconds;

        if (timeDifferent < 60000) {  //一分钟之内
            return "刚刚";
        }
        if (timeDifferent < (60 * 60 * 1000)) {   //一小时之内
            long longMinute = timeDifferent / 60000;
            int minute = (int) (longMinute % 100);
            return minute + "分钟之前";
        }
        long l = 24 * 60 * 60 * 1000;  //每天的毫秒数
        if (timeDifferent < l) {  //小于一天
            long longHour = timeDifferent / (60 * 60 * 1000);
            int hour = (int) (longHour % 100);
            return hour + "小时之前";
        }
        if (timeDifferent >= l) {
            String currYear = currDate.substring(0, 4);
            String year = time.substring(0, 4);
            if (!year.equals(currYear)) {
                return time.substring(0, 10);
            }
            return time.substring(5, 10);
        }
        return time;
    }

    /**
     * 获取当前日期
     */
    public static String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        return date;
    }

    public static ArrayList<String> getTodayDateList() {
        String data = getTodayDate();
        String[] split = data.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        ArrayList<String> list = new ArrayList<>();
        list.add(year);
        list.add(month);
        list.add(day);
        return list;
    }


    /**
     * 获取当前时间是否大于12：30
     */
    public static boolean isRightTime() {
        Time t = new Time();
        t.setToNow();
        int hour = t.hour;
        int minute = t.minute;
        return hour > 12 || (hour == 12 && minute >= 30);
    }

    /**
     * 得到上一天的时间
     */
    public static ArrayList<String> getLastTime(String year, String month, String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));

        int inDay = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, inDay - 1);

        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(calendar.get(Calendar.YEAR)));
        list.add(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        list.add(String.valueOf(calendar.get(Calendar.DATE)));
        return list;
    }



}
