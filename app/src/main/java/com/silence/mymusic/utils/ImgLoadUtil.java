package com.silence.mymusic.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.silence.mymusic.R;

import java.util.Random;

/**
 * Created by wushiyu on 2017/6/15.
 */

public class ImgLoadUtil {

    private static ImgLoadUtil instance;

    // 六图的随机图
    private static int[] homeSix = {R.drawable.home_six_1, R.drawable.home_six_2, R.drawable.home_six_3, R.drawable.home_six_4,
            R.drawable.home_six_5, R.drawable.home_six_6, R.drawable.home_six_7, R.drawable.home_six_8, R.drawable.home_six_9,
            R.drawable.home_six_10, R.drawable.home_six_11, R.drawable.home_six_12, R.drawable.home_six_13, R.drawable.home_six_14,
            R.drawable.home_six_15, R.drawable.home_six_16, R.drawable.home_six_17, R.drawable.home_six_18, R.drawable.home_six_19,
            R.drawable.home_six_20, R.drawable.home_six_21, R.drawable.home_six_22, R.drawable.home_six_23
    };

    // 2张图的随机图
    private static int[] homeTwo = {R.drawable.home_two_one, R.drawable.home_two_two, R.drawable.home_two_three, R.drawable.home_two_four,
            R.drawable.home_two_five, R.drawable.home_two_six, R.drawable.home_two_eleven, R.drawable.home_two_eight, R.drawable.home_two_nine
    };

    // 一张图的随机图
    private static int[] homeOne = {R.drawable.home_one_1, R.drawable.home_one_2, R.drawable.home_one_3,
            R.drawable.home_one_4, R.drawable.home_one_5, R.drawable.home_one_6, R.drawable.home_one_7,
            R.drawable.home_one_9, R.drawable.home_one_10, R.drawable.home_one_11, R.drawable.home_one_12
    };

    private static int randomOne = 0;
    private static int randomTwo = 0;
    private static int randomSix = 0;

    private ImgLoadUtil() {
        randomOne = new Random().nextInt(homeOne.length);
        randomTwo = new Random().nextInt(homeTwo.length);
        randomSix = new Random().nextInt(homeSix.length);
    }

//    public static ImgLoadUtil getInstance() {
//        if (instance == null) {
//            synchronized (ImgLoadUtil.class) {
//                if (instance == null) {
//                    instance = new ImgLoadUtil();
//                }
//            }
//        }
//        return instance;
//    }

    public static void CommonLoadImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).into(imageView);
    }

    public static void displayRandom(int imgNumber, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(getRandomPic(imgNumber))
                .placeholder(getDefaultPic(imgNumber))
                .error(getDefaultPic(imgNumber))
                .crossFade(1500)
                .into(imageView);
    }

    public static void displayWelfare(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.img_default_meizi)
                .error(R.drawable.img_default_meizi)
                .crossFade(500)
                .into(imageView);
    }

    private static int getDefaultPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                return R.drawable.img_two_bi_one;
            case 2:
                return R.drawable.img_four_bi_three;
            case 3:
                return R.drawable.img_one_bi_one;
            default:
                return R.drawable.img_four_bi_three;
        }
    }

    public static int getRandomPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                randomOne ++;
                if (randomOne >= homeOne.length)
                    randomOne = 0;
                return  homeOne[randomOne];
            case 2:
                randomTwo ++;
                if (randomTwo >= homeTwo.length)
                    randomTwo = 0;
                return homeTwo[randomTwo];
            case 3:
                randomSix ++;
                if (randomSix >= homeSix.length)
                    randomSix = 0;
                return homeSix[randomSix];
            default:
                return homeOne[randomOne];
        }
    }



}
