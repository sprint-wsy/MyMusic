package com.silence.mymusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.silence.mymusic.R;
import com.silence.mymusic.bean.ItemBean;
import com.silence.mymusic.ui.gank.child.RecommendFragment;
import com.silence.mymusic.ui.webview.WebViewActivity;
import com.silence.mymusic.utils.CommonUtils;
import com.silence.mymusic.utils.ImgLoadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wushiyu on 2017/6/19.
 */

public class RecommendRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<List<ItemBean>> mItemBeanList;
    private RecommendFragment mRecommendFragment;

    public RecommendRecycleViewAdapter(RecommendFragment fragment, List<List<ItemBean>> list) {
        super();
        mRecommendFragment = fragment;
        mItemBeanList = (ArrayList<List<ItemBean>>) list;
    }

    public static final int TYPE_HEADER = 1000;    //头布局
    public static final int TYPE_TITLE = 1001;     //标题
    public static final int TYPE_ONE = 1002;       //一张图
    public static final int TYPE_TWO = 1003;       //两张图
    public static final int TYPE_THREE = 1004;     //三张图
    public static final int TYPE_FOOTER = 1005;    //尾部布局

    private View mHeaderView;
    private View mFooterView;

    public void setHeaderView(View view) {
        mHeaderView = view;
    }

    public void setFooterView(View view) {
        mFooterView = view;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setData(List<List<ItemBean>> data) {
        mItemBeanList = (ArrayList<List<ItemBean>>) data;
    }

    @Override
    public int getItemViewType(int position) {
        int dataPosition = position;
        if (mHeaderView != null) {
            dataPosition -- ;
        }
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        } else if (mFooterView != null && position == (getItemCount() -1 )) {
            return TYPE_FOOTER;
        } else  if (!TextUtils.isEmpty(mItemBeanList.get(dataPosition).get(0).getType_title())) {
            return TYPE_TITLE;
        } else if (mItemBeanList.get(dataPosition).size() == 1) {
            return TYPE_ONE;
        } else if (mItemBeanList.get(dataPosition).size() == 2) {
            return TYPE_TWO;
        } else if (mItemBeanList.get(dataPosition).size() == 3) {
            return TYPE_THREE;
        }

        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderHolder(getHeaderView());
            case TYPE_FOOTER:
                return new FooterHolder(getFooterView());
            case TYPE_TITLE:
                View titleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item_title, parent, false);
                return new TitleHolder(titleView);
            case TYPE_ONE:
                View oneView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item_one, parent, false);
                return new OneHolder(oneView);
            case TYPE_TWO:
                View twoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item_two, parent, false);
                return new TwoHolder(twoView);
            case TYPE_THREE:
                View threeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item_three, parent, false);
                return new ThreeHolder(threeView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        int dataPosition = position;
        //存在头布局的时候计算 item 位置需减一
        if (mHeaderView != null) {
            dataPosition --;
        }
        if (getItemViewType(position) == TYPE_TITLE) {
            String title = mItemBeanList.get(dataPosition).get(0).getType_title();
            ((TitleHolder) holder).mTitleText.setText(title);
            if ("Android".equals(title)) {
                ((TitleHolder) holder).mTitleImage.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_android));
                setOnClick(holder.itemView, 3);
            } else if ("福利".equals(title)) {
                ((TitleHolder) holder).mTitleImage.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_meizi));
                setOnClick(holder.itemView, 1);
            } else if ("IOS".equals(title)) {
                ((TitleHolder) holder).mTitleImage.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_ios));
                setOnClick(holder.itemView, 2);
            } else if ("休息视频".equals(title)) {
                ((TitleHolder) holder).mTitleImage.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_movie));
                setOnClick(holder.itemView, 2);
            } else if ("拓展资源".equals(title)) {
                ((TitleHolder) holder).mTitleImage.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_source));
                setOnClick(holder.itemView, 2);
            } else if ("瞎推荐".equals(title)) {
                ((TitleHolder) holder).mTitleImage.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_xia));
                setOnClick(holder.itemView, 2);
            } else if ("前端".equals(title)) {
                ((TitleHolder) holder).mTitleImage.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_qian));
                setOnClick(holder.itemView, 2);
            } else if ("App".equals(title)) {
                ((TitleHolder) holder).mTitleImage.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_app));
                setOnClick(holder.itemView, 2);
            }
        } else if (getItemViewType(position) == TYPE_ONE) {
            String type = mItemBeanList.get(dataPosition).get(0).getType();
            String url = mItemBeanList.get(dataPosition).get(0).getUrl();
            if ("福利".equals(type)) {
                ((OneHolder)holder).mTextView.setVisibility(View.GONE);
                ((OneHolder)holder).mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(((OneHolder)holder).mImageView.getContext())
                        .load(url)
                        .crossFade(1500)
                        .placeholder(R.drawable.img_two_bi_one)
                        .error(R.drawable.img_two_bi_one)
                        .into(((OneHolder)holder).mImageView);
            } else {
                String desc = mItemBeanList.get(dataPosition).get(0).getDesc();
                ((OneHolder)holder).mTextView.setText(desc);
                ImgLoadUtil.displayRandom(1, ((OneHolder)holder).mImageView);
            }
            setOnClick(((OneHolder)holder).mLayout, mItemBeanList.get(dataPosition).get(0));
        } else if (getItemViewType(position) == TYPE_TWO) {
            String desc1 = mItemBeanList.get(dataPosition).get(0).getDesc();
            String desc2 = mItemBeanList.get(dataPosition).get(1).getDesc();
            ((TwoHolder)holder).mTextView1.setText(desc1);
            ((TwoHolder)holder).mTextView2.setText(desc2);
            ImgLoadUtil.displayRandom(2, ((TwoHolder)holder).mImageView1);
            ImgLoadUtil.displayRandom(2, ((TwoHolder)holder).mImageView2);
            setOnClick(((TwoHolder)holder).mLayout1, mItemBeanList.get(dataPosition).get(0));
            setOnClick(((TwoHolder)holder).mLayout2, mItemBeanList.get(dataPosition).get(1));
        } else if (getItemViewType(position) == TYPE_THREE) {
            String desc1 = mItemBeanList.get(dataPosition).get(0).getDesc();
            String desc2 = mItemBeanList.get(dataPosition).get(1).getDesc();
            String desc3 = mItemBeanList.get(dataPosition).get(2).getDesc();
            ((ThreeHolder)holder).mTextView1.setText(desc1);
            ((ThreeHolder)holder).mTextView2.setText(desc2);
            ((ThreeHolder)holder).mTextView3.setText(desc3);
            ImgLoadUtil.displayRandom(3, ((ThreeHolder)holder).mImageView1);
            ImgLoadUtil.displayRandom(3, ((ThreeHolder)holder).mImageView2);
            ImgLoadUtil.displayRandom(3, ((ThreeHolder)holder).mImageView3);
            setOnClick(((ThreeHolder)holder).mLayout1, mItemBeanList.get(dataPosition).get(0));
            setOnClick(((ThreeHolder)holder).mLayout2, mItemBeanList.get(dataPosition).get(1));
            setOnClick(((ThreeHolder)holder).mLayout3, mItemBeanList.get(dataPosition).get(2));
        }

    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mItemBeanList.size();
        } else if (mHeaderView != null && mFooterView != null) {
            return mItemBeanList.size() + 2;
        } else {
            return mItemBeanList.size() + 1;
        }
    }

    /**
     * 用于打开相应的 webActivity
     */
    private void setOnClick(LinearLayout linearLayout, final ItemBean itemBean) {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), itemBean.getUrl(), "加载中...");
            }
        });
    }

    /**
     * 用于点击更多跳转到其他 Fragment
     */
    private void setOnClick(View titleView, final int position) {
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecommendFragment.jumpGankFragment(position);
            }
        });
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    private class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    private class TitleHolder extends RecyclerView.ViewHolder {
        ImageView mTitleImage;
        TextView mTitleText;

        public TitleHolder(View itemView) {
            super(itemView);
            mTitleImage = (ImageView) itemView.findViewById(R.id.image_title_type);
            mTitleText = (TextView) itemView.findViewById(R.id.text_title_type);
        }
    }

    private class OneHolder extends RecyclerView.ViewHolder {
        LinearLayout mLayout;
        ImageView mImageView;
        TextView mTextView;

        public OneHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView.findViewById(R.id.layout_item_one);
            mImageView = (ImageView) itemView.findViewById(R.id.image_one_photo);
            mTextView = (TextView) itemView.findViewById(R.id.text_one_photo_title);
        }
    }

    private class TwoHolder extends RecyclerView.ViewHolder {
        LinearLayout mLayout1, mLayout2;
        ImageView mImageView1, mImageView2;
        TextView mTextView1, mTextView2;

        public TwoHolder(View itemView) {
            super(itemView);
            mLayout1 = (LinearLayout) itemView.findViewById(R.id.layout_two_first);
            mLayout2 = (LinearLayout) itemView.findViewById(R.id.layout_two_second);
            mImageView1 = (ImageView) itemView.findViewById(R.id.image_two_first);
            mTextView1 = (TextView) itemView.findViewById(R.id.text_two_first_title);
            mImageView2 = (ImageView) itemView.findViewById(R.id.image_two_second);
            mTextView2 = (TextView) itemView.findViewById(R.id.text_two_second_title);
        }
    }

    private class ThreeHolder extends RecyclerView.ViewHolder {
        LinearLayout mLayout1, mLayout2, mLayout3;
        ImageView mImageView1, mImageView2, mImageView3;
        TextView mTextView1, mTextView2, mTextView3;

        public ThreeHolder(View itemView) {
            super(itemView);
            mLayout1 = (LinearLayout) itemView.findViewById(R.id.layout_three_first);
            mLayout2 = (LinearLayout) itemView.findViewById(R.id.layout_three_second);
            mLayout3 = (LinearLayout) itemView.findViewById(R.id.layout_three_third);
            mImageView1 = (ImageView) itemView.findViewById(R.id.image_three_first);
            mTextView1 = (TextView) itemView.findViewById(R.id.text_three_first_title);
            mImageView2 = (ImageView) itemView.findViewById(R.id.image_three_second);
            mTextView2 = (TextView) itemView.findViewById(R.id.text_three_second_title);
            mImageView3 = (ImageView) itemView.findViewById(R.id.image_three_third);
            mTextView3 = (TextView) itemView.findViewById(R.id.text_three_third_title);
        }
    }

}
