package com.silence.mymusic.ui.movie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.silence.mymusic.R;
import com.silence.mymusic.adapter.MovieDetailCastsAdapter;
import com.silence.mymusic.bean.movie.MovieDetailBean;
import com.silence.mymusic.bean.movie.PersonBean;
import com.silence.mymusic.bean.movie.SubjectsBean;
import com.silence.mymusic.ui.webview.WebViewActivity;
import com.silence.mymusic.utils.CommonUtils;
import com.silence.mymusic.utils.DebugUtil;
import com.silence.mymusic.utils.ImgLoadUtil;
import com.silence.mymusic.utils.StringFormatUtil;
import com.silence.mymusic.utils.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wushiyu on 2017/7/10.
 */

public class MovieDetailActivity extends AppCompatActivity {

    public static final String INTENT_BEAN = "subject";

    private SubjectsBean mSubjectsBean;
    private ImageView mHeaderBg, mImageToolbarBg, mImagePhoto;
    private Toolbar mToolbar;
    private TextView mTextRating, mTextRatingNumber, mTextDirectors, mTextCasts, mTextGenres;
    private TextView mTextDay, mTextCity;
    private TextView mTextAnotherTitle, mTextSummary;
    private String mMoreUrl;
    private NestedScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private MovieDetailCastsAdapter mAdapter;
    private List<PersonBean> mPersonData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initView();
        loadMovieDetailData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_detail, menu);
        return true;
    }

    private void loadMovieDetailData() {
        Call<MovieDetailBean> call = HttpUtils.getInstance().getMovieDetail(mSubjectsBean.getId());
        call.enqueue(new Callback<MovieDetailBean>() {
            @Override
            public void onResponse(Call<MovieDetailBean> call, Response<MovieDetailBean> response) {
                MovieDetailBean data = response.body();
                mTextDay.setText("上映日期：" + data.getYear());
                mTextCity.setText("制片国家/地区：" + StringFormatUtil.formatGenres(data.getCountries()));
                mTextAnotherTitle.setText(StringFormatUtil.formatGenres(data.getAka()));
                mTextSummary.setText(data.getSummary());
                mMoreUrl = data.getMobile_url();
                setPersonList(data);
            }

            @Override
            public void onFailure(Call<MovieDetailBean> call, Throwable t) {
                DebugUtil.i("loadMovieDetailData fail");
            }
        });
    }

    private void initView() {
        mScrollView = (NestedScrollView) findViewById(R.id.nested_scrollview);
        mHeaderBg = (ImageView) findViewById(R.id.movie_header_bg);
        mImageToolbarBg = (ImageView) findViewById(R.id.image_toolbar_bg);
        mToolbar = (Toolbar) findViewById(R.id.movie_tool_bar);
        mTextSummary = (TextView) findViewById(R.id.text_movie_summary);
        mTextAnotherTitle = (TextView) findViewById(R.id.text_movie_another_title);
        mTextRating = (TextView) findViewById(R.id.text_movie_rating_rat);
        mTextRatingNumber = (TextView) findViewById(R.id.text_movie_rating_number);
        mTextDirectors = (TextView) findViewById(R.id.text_movie_directors);
        mTextCasts = (TextView) findViewById(R.id.text_movie_casts);
        mTextGenres = (TextView) findViewById(R.id.text_movie_genres);
        mTextDay = (TextView) findViewById(R.id.text_movie_day);
        mTextCity = (TextView) findViewById(R.id.text_movie_city);
        mImagePhoto = (ImageView) findViewById(R.id.image_movie_photo);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_cast);
        mPersonData = new ArrayList<>();
        mAdapter = new MovieDetailCastsAdapter(this, mPersonData);

        initData();
        initToolbar();
        initHeaderbg();
    }

    private void initData() {
        mSubjectsBean = getIntent().getParcelableExtra(INTENT_BEAN);
        mTextRating.setText("评分：" + mSubjectsBean.getRating().getAverage());
        mTextRatingNumber.setText(mSubjectsBean.getCollect_count() + "评分");
        mTextDirectors.setText(StringFormatUtil.formatName(mSubjectsBean.getDirectors()));
        mTextCasts.setText(StringFormatUtil.formatName(mSubjectsBean.getCasts()));
        mTextGenres.setText(StringFormatUtil.formatGenres(mSubjectsBean.getGenres()));
        ImgLoadUtil.CommonLoadImage(this, mSubjectsBean.getImages().getLarge(), mImagePhoto);
    }

    private void initHeaderbg() {
        Glide.with(this).load(mSubjectsBean.getImages().getLarge())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(this,50,4))
                .into(mHeaderBg);

        //将一张跟背景图一样的图片移动到 Toolbar 的高度，并随着滑动改变透明图
        Glide.with(this).load(mSubjectsBean.getImages().getLarge())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(this,50,4))
                .into(mImageToolbarBg);
        int toolbarHeight = mToolbar.getLayoutParams().height;
        DebugUtil.i("toolbarHeight " + toolbarHeight);
        final int marginTop = mImageToolbarBg.getLayoutParams().height - toolbarHeight;
        DebugUtil.i("marginTop " + marginTop);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mImageToolbarBg.getLayoutParams();
        params.setMargins(0, -marginTop, 0, 0);
        mImageToolbarBg.setImageAlpha(0);

        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                DebugUtil.i("NestedScrollView" + scrollY);
                int y = scrollY;
                if (y > marginTop) {
                    y = marginTop;
                }
                int alpha = y * 255 / marginTop;
                DebugUtil.i("alpha" + alpha);
                mImageToolbarBg.setImageAlpha(alpha);

            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }

        mToolbar.setTitleTextAppearance(this, R.style.ToolBar_Title);
        mToolbar.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);
        mToolbar.setTitle(mSubjectsBean.getTitle());
        mToolbar.setSubtitle(String.format("主演：%s", StringFormatUtil.formatName(mSubjectsBean.getCasts())));
        mToolbar.inflateMenu(R.menu.movie_detail);
        mToolbar.setOverflowIcon(CommonUtils.getDrawable(R.drawable.actionbar_more));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionbar_more:
                        WebViewActivity.loadUrl(MovieDetailActivity.this, mMoreUrl, mSubjectsBean.getTitle());
                        break;
                }
                return false;
            }
        });
    }

    private void setPersonList(MovieDetailBean movieDetailBean) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<PersonBean> directors = movieDetailBean.getDirectors();
        for (PersonBean bean: directors) {
            bean.setType("导演");
        }
        List<PersonBean> casts = movieDetailBean.getCasts();
        for (PersonBean bean: casts) {
            bean.setType("演员");
        }
        mAdapter.addData(directors);
        mAdapter.addData(casts);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static void start(Context context, SubjectsBean data, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(INTENT_BEAN, data);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                imageView, CommonUtils.getString(R.string.transition_movie_img));
        ActivityCompat.startActivity((Activity) context, intent, options.toBundle());
    }
}
