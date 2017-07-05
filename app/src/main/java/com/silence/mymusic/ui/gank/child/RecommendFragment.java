package com.silence.mymusic.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silence.mymusic.R;
import com.silence.mymusic.adapter.RecommendRecycleViewAdapter;
import com.silence.mymusic.base.BaseFragment;
import com.silence.mymusic.bean.GankIoDataBean;
import com.silence.mymusic.bean.GankIoDayBean;
import com.silence.mymusic.bean.ItemBean;
import com.silence.mymusic.ui.webview.WebViewActivity;
import com.silence.mymusic.utils.BannerImageLoader;
import com.silence.mymusic.utils.DebugUtil;
import com.silence.mymusic.utils.ImgLoadUtil;
import com.silence.mymusic.utils.http.HttpUtils;
import com.silence.mymusic.utils.http.RetrofitClient;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wushiyu on 2017/6/17.
 */

public class RecommendFragment extends BaseFragment {

    private static final String URL_FREE_READ = "https://gank.io/xiandu";

    private RecyclerView mRecyclerView;
    private LinearLayout mLoadingLayout;
    private View mHeaderView;
    private ImageButton mButtonFreeRead, mButtonMovieHot;
    private ImageView mButtonDaily;
    private TextView mTextDaily;
    private Banner mBanner;
    private RecommendRecycleViewAdapter mRecycleViewAdapter;
    private boolean mIsPrepared = false;

    private List<List<ItemBean>> mDataLists;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleView();
//        mIsPrepared = true;
//        loadData();
        loadBanner();
        showData();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_recommend;
    }

//    @Override
//    protected void loadData() {
//        super.loadData();
//        if (!mIsVisible || !mIsPrepared) {
//            return;
//        }
//        loadBanner();
//        showData();
//    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        showContentView();
        showRecommendLoading(true);
        loadBanner();
        showData();
    }

    private void initRecycleView() {
        initHeader();
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_recommend);
        mLoadingLayout = (LinearLayout) getView().findViewById(R.id.layout_loading);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        List<List<ItemBean>> items = new ArrayList<>(0);
        mRecycleViewAdapter = new RecommendRecycleViewAdapter(items);
        mRecycleViewAdapter.setHeaderView(mHeaderView);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
        showRecommendLoading(false);
        showContentView();
    }

    private void initHeader() {
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.recommend_header, null);
        mButtonFreeRead = (ImageButton) mHeaderView.findViewById(R.id.button_free_read);
        mButtonDaily = (ImageView) mHeaderView.findViewById(R.id.button_daily);
        mButtonMovieHot = (ImageButton) mHeaderView.findViewById(R.id.button_movie_hot);
        mTextDaily = (TextView) mHeaderView.findViewById(R.id.text_daily);
        mButtonMovieHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(getContext(), URL_FREE_READ,"加载中...");
            }
        });

    }

    private void showRecommendLoading(boolean isLoading) {
        if (isLoading) {
            mLoadingLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mLoadingLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载轮播图
     */
    private void loadBanner() {
        mBanner = (Banner) mHeaderView.findViewById(R.id.banner);
        mBanner.setImageLoader(new BannerImageLoader());
        ArrayList<Integer> imageList = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            imageList.add(ImgLoadUtil.getRandomPic(1));
        }
        mBanner.setImages(imageList);
        mBanner.isAutoPlay(false);
//        mBanner.setDelayTime(5000);
        mBanner.start();
    }

    private void showData() {
        Call<GankIoDayBean> call1 = HttpUtils.getInstance().getGankIoDay("2017", "06", "09");
        call1.enqueue(new Callback<GankIoDayBean>() {
            @Override
            public void onResponse(Call<GankIoDayBean> call, Response<GankIoDayBean> response) {
                mDataLists = new ArrayList<List<ItemBean>>();
                GankIoDayBean.ResultsBean results = response.body().getResults();
                DebugUtil.i(response.body().getCategory().toString());
                if (results.getAndroid() != null && results.getAndroid().size() > 0) {
                    addList(results.getAndroid(), "Android");
                }
                if (results.getWelfare() != null && results.getWelfare().size() > 0) {
                    addList(results.getWelfare(), "福利");
                }
                if (results.getiOS() != null && results.getiOS().size() > 0) {
                    addList(results.getiOS(), "IOS");
                }
                if (results.getRestMovie() != null && results.getRestMovie().size() > 0) {
                    addList(results.getRestMovie(), "休息视频");
                    DebugUtil.i(results.getRestMovie().get(0).get_id());
                }
                if (results.getResource() != null && results.getResource().size() > 0) {
                    addList(results.getResource(), "拓展资源");
                }
                if (results.getRecommend() != null && results.getRecommend().size() > 0) {
                    addList(results.getRecommend(), "瞎推荐");
                }
                if (results.getFront() != null && results.getFront().size() > 0) {
                    addList(results.getFront(), "前端");
                }
                if (results.getApp() != null && results.getApp().size() > 0) {
                    addList(results.getApp(), "App");
                }
                mRecycleViewAdapter.setData(mDataLists);
                mRecycleViewAdapter.notifyDataSetChanged();
                showRecommendLoading(false);
            }

            @Override
            public void onFailure(Call<GankIoDayBean> call, Throwable t) {
                showError();
                DebugUtil.i("get gank day error");
            }
        });
    }

    private void addList(List<ItemBean> arrayList, String typeTitle) {
        // title item 数据
        ItemBean bean = new ItemBean();
        bean.setType_title(typeTitle);
        ArrayList<ItemBean> androidBeen = new ArrayList<>();
        androidBeen.add(bean);
        mDataLists.add(androidBeen);

        int androidSize = arrayList.size();
        if (androidSize > 0 && androidSize < 4) {
            mDataLists.add(arrayList);
        } else if (androidSize >= 4) {         //6个的数据切成两个3个数据的 List

            ArrayList<ItemBean> list1 = new ArrayList<>();
            ArrayList<ItemBean> list2 = new ArrayList<>();
            for (int i = 0; i < androidSize; i++) {
                if (i < 3) {
                    list1.add(arrayList.get(i));
                } else if (i < 6) {
                    list2.add(arrayList.get(i));
                }
            }
            mDataLists.add(list1);
            mDataLists.add(list2);
        }
    }
}
