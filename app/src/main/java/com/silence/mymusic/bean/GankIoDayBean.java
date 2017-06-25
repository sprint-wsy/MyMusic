package com.silence.mymusic.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wushiyu on 2017/6/24.
 */

public class GankIoDayBean {

    private List<String> category;
    private boolean error;
    private ResultsBean results;

    public List<String> getCategory() {
        return category;
    }

    public boolean isError() {
        return error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public static class ResultsBean {

        private List<ItemBean> Android;

        private List<ItemBean> iOS;

        @SerializedName("前端")
        private List<ItemBean> front;

        @SerializedName("App")
        private List<ItemBean> app;

        @SerializedName("休息视频")
        private List<ItemBean> restMovie;

        @SerializedName("拓展资源")
        private List<ItemBean> resource;

        @SerializedName("瞎推荐")
        private List<ItemBean> recommend;

        @SerializedName("福利")
        private List<ItemBean> welfare;

        public List<ItemBean> getAndroid() {
            return Android;
        }

        public List<ItemBean> getiOS() {
            return iOS;
        }

        public List<ItemBean> getFront() {
            return front;
        }

        public List<ItemBean> getApp() {
            return app;
        }

        public List<ItemBean> getRestMovie() {
            return restMovie;
        }

        public List<ItemBean> getResource() {
            return resource;
        }

        public List<ItemBean> getRecommend() {
            return recommend;
        }

        public List<ItemBean> getWelfare() {
            return welfare;
        }
    }

}
