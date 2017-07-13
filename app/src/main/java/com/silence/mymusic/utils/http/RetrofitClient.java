package com.silence.mymusic.utils.http;

import com.silence.mymusic.bean.GankIoDataBean;
import com.silence.mymusic.bean.GankIoDayBean;
import com.silence.mymusic.bean.book.BookDetailBean;
import com.silence.mymusic.bean.book.BookListBean;
import com.silence.mymusic.bean.movie.HotMovieBean;
import com.silence.mymusic.bean.movie.MovieDetailBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wushiyu on 2017/6/20.
 */

public interface RetrofitClient {

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{count}/{page}")
    Call<GankIoDataBean> getGankIoData(@Path("type") String id, @Path("count") int count, @Path("page") int page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2015/08/06
     */
    @GET("day/{year}/{month}/{day}")
    Call<GankIoDayBean> getGankIoDay(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("v2/movie/in_theaters")
    Call<HotMovieBean> getHotMovie();

    /**
     * 获取电影详情
     * @param id 电影bean里的id
     */
    @GET("v2/movie/subject/{id}")
    Call<MovieDetailBean> getMovieDetail(@Path("id") String id);

    /**
     * 获取豆瓣电影top250
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("v2/movie/top250")
    Call<HotMovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 根据tag获取图书
     *
     * @param tag   搜索关键字
     * @param count 一次请求的数目 最多100
     */
    @GET("v2/book/search")
    Call<BookListBean> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    /**
     * 根据 id 获取书籍详情
     * @param id  书籍id
     */
    @GET("v2/book/{id}")
    Call<BookDetailBean> getBookDetail(@Path("id") String id);
}
