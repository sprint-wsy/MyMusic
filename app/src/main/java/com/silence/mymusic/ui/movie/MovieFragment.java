package com.silence.mymusic.ui.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.silence.mymusic.R;
import com.silence.mymusic.base.BaseFragment;
import com.silence.mymusic.bean.movie.HotMovieBean;
import com.silence.mymusic.utils.DebugUtil;
import com.silence.mymusic.utils.http.HttpUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wushiyu on 2017/6/17.
 */

public class MovieFragment extends BaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Call<HotMovieBean> call = HttpUtils.getInstance().getHotMovie();
        call.enqueue(new Callback<HotMovieBean>() {
            @Override
            public void onResponse(Call<HotMovieBean> call, Response<HotMovieBean> response) {
                DebugUtil.i("hot movie ", response.body().getSubjects().toString());
            }

            @Override
            public void onFailure(Call<HotMovieBean> call, Throwable t) {
                DebugUtil.i("hot movie", "onFailure");
            }
        });
    }

    @Override
    public int setContent() {
        return R.layout.fragment_movie;
    }
}
