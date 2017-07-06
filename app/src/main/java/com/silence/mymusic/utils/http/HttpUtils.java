package com.silence.mymusic.utils.http;

import com.silence.mymusic.bean.GankIoDataBean;
import com.silence.mymusic.bean.GankIoDayBean;
import com.silence.mymusic.bean.movie.HotMovieBean;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wushiyu on 2017/6/19.
 */

public class HttpUtils {

    private static HttpUtils sHttpUtils;

    public final static String API_GANKIO = "http://gank.io/api/";
    public final static String API_DOUBAN = "https://api.douban.com/";

    private static Retrofit sGankRetrofit  = new Retrofit.Builder()
            .baseUrl(API_GANKIO)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static Retrofit sDoubanRetrofit = new Retrofit.Builder()
            .baseUrl(API_DOUBAN)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static RetrofitClient sGankRetrofitClient = sGankRetrofit.create(RetrofitClient.class);
    private static RetrofitClient sDoubanRetrofitClient = sDoubanRetrofit.create(RetrofitClient.class);

    public static HttpUtils getInstance() {
        if (sHttpUtils == null) {
            synchronized (HttpUtils.class) {
                if (sHttpUtils == null) {
                    sHttpUtils = new HttpUtils();
                }
            }
        }
        return sHttpUtils;
    }


    public Call<GankIoDataBean> getGankIoData(String type, int count, int page) {
        Call<GankIoDataBean> call = sGankRetrofitClient.getGankIoData(type, count, page);
        return call;
    }

    public Call<GankIoDayBean> getGankIoDay(String year, String month, String day) {
        Call<GankIoDayBean> call = sGankRetrofitClient.getGankIoDay(year, month, day);
        return call;
    }

    public Call<HotMovieBean> getHotMovie() {
        Call<HotMovieBean> call = sDoubanRetrofitClient.getHotMovie();
        return call;
    }

}
