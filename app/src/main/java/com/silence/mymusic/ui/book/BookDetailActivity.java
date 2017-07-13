package com.silence.mymusic.ui.book;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.silence.mymusic.bean.book.BookBean;
import com.silence.mymusic.bean.book.BookDetailBean;
import com.silence.mymusic.bean.movie.MovieDetailBean;
import com.silence.mymusic.bean.movie.PersonBean;
import com.silence.mymusic.bean.movie.SubjectsBean;
import com.silence.mymusic.ui.movie.MovieDetailActivity;
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
 * Created by wushiyu on 2017/7/13.
 */

public class BookDetailActivity extends AppCompatActivity {

    public static final String INTENT_BEAN = "book_bean";

    private BookBean mBookBean;
    private ImageView mHeaderBg, mImageToolbarBg, mImagePhoto;
    private Toolbar mToolbar;
    private TextView mTextRating, mTextRatingNumber, mTextAuthor;
    private TextView mTextPubdata, mTextPublisher;
    private TextView mTextAuthorIntro, mTextSummary, mTextCatelog;
    private String mMoreUrl;
    private NestedScrollView mScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initView();
        loadMovieDetailData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_detail, menu);
        return true;
    }

    private void loadMovieDetailData() {
        Call<BookDetailBean> call = HttpUtils.getInstance().getBookDetail(mBookBean.getId());
        call.enqueue(new Callback<BookDetailBean>() {
            @Override
            public void onResponse(Call<BookDetailBean> call, Response<BookDetailBean> response) {
                BookDetailBean data = response.body();
                mTextPubdata.setText(data.getPubdate());
                mTextPublisher.setText("出版社：" + data.getPublisher());
                mTextAuthorIntro.setText(data.getAuthor_intro());
                mTextSummary.setText(data.getSummary());
                mTextCatelog.setText(data.getCatalog());
                mMoreUrl = data.getAlt();
            }

            @Override
            public void onFailure(Call<BookDetailBean> call, Throwable t) {
                DebugUtil.i("loadMovieDetailData fail");
            }
        });
    }

    private void initView() {
        mScrollView = (NestedScrollView) findViewById(R.id.nested_scrollview);
        mHeaderBg = (ImageView) findViewById(R.id.book_header_bg);
        mImageToolbarBg = (ImageView) findViewById(R.id.image_toolbar_bg);
        mToolbar = (Toolbar) findViewById(R.id.book_tool_bar);

        mTextSummary = (TextView) findViewById(R.id.text_book_summary);
        mTextAuthorIntro = (TextView) findViewById(R.id.text_book_author_intro);
        mTextCatelog = (TextView) findViewById(R.id.text_book_catalog);

        mTextRating = (TextView) findViewById(R.id.text_book_rating_rate);
        mTextRatingNumber = (TextView) findViewById(R.id.text_book_rating_number);
        mTextAuthor = (TextView) findViewById(R.id.text_book_author);
        mTextPubdata = (TextView) findViewById(R.id.text_book_pubdate);
        mTextPublisher = (TextView) findViewById(R.id.text_book_publisher);
        mImagePhoto = (ImageView) findViewById(R.id.image_book_photo);

        initData();
        initToolbar();
        initHeaderbg();
    }

    private void initData() {
        mBookBean = getIntent().getParcelableExtra(INTENT_BEAN);
        mTextRating.setText("评分：" + mBookBean.getRating().getAverage());
        mTextRatingNumber.setText(mBookBean.getRating().getNumRaters() + "评分");
        mTextAuthor.setText(StringFormatUtil.formatGenres(mBookBean.getAuthor()));
        ImgLoadUtil.CommonLoadImage(this, mBookBean.getImages().getLarge(), mImagePhoto);
    }

    private void initHeaderbg() {
        Glide.with(this).load(mBookBean.getImages().getLarge())
                .error(R.drawable.stackblur_default)
                .bitmapTransform(new BlurTransformation(this,50,4))
                .into(mHeaderBg);

        //将一张跟背景图一样的图片移动到 Toolbar 的高度，并随着滑动改变透明图
        Glide.with(this).load(mBookBean.getImages().getLarge())
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
        mToolbar.setTitle(mBookBean.getTitle());
        mToolbar.setSubtitle(String.format("作者：%s", StringFormatUtil.formatGenres(mBookBean.getAuthor())));
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
                        WebViewActivity.loadUrl(BookDetailActivity.this, mMoreUrl, mBookBean.getTitle());
                        break;
                }
                return false;
            }
        });
    }


    public static void start(Context context, BookBean data, ImageView imageView) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(INTENT_BEAN, data);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                imageView, CommonUtils.getString(R.string.transition_book_img));
        ActivityCompat.startActivity((Activity) context, intent, options.toBundle());
    }

}
