package com.jushi.muisc.chat.music.home_page.banner.controller;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.home_page.banner.BannerViewActivity;
import com.jushi.muisc.chat.music.common.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.common.manager.ActivityManager;
import com.jushi.muisc.chat.music.home_page.banner.model.BannerModel;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.music.common.utils.music.MusicDataUtils;
import com.jushi.muisc.chat.common.utils.NetWorkUtils;
import com.jushi.mzbanner.MZBannerView;
import com.jushi.mzbanner.holder.MZHolderCreator;
import com.jushi.mzbanner.holder.MZViewHolder;

import java.util.List;

/**
 * 首页轮播图
 */

public class BannerImageController {
    private final String SAVE_KEY = "sliderBeans";
    private Context mContext;
    private MZBannerView bannerView;
    private NetWorkService workService;
    private Handler handler;
    private BannerImageTask bannerImageTask;
    private List<BannerModel.DataBean.SliderBean> sliders;
    private boolean isRefresh = false;

    public BannerImageController(Context mContext) {
        this.mContext = mContext;
        handler = new Handler();
        workService = NetWorkService.getInstance(mContext);
    }

    public void initBannerView(View rootView) {
        bannerView = rootView.findViewById(R.id.mzBanner_view);
        bannerView.setDuration(1500);

        loadBannerData();
        setBannerListener();
    }

    private void loadBannerData() {
        if (NetWorkUtils.isNetworkAvailable(mContext)) {
            bannerImageTask = new BannerImageTask();
            bannerImageTask.run();
        } else {
            if (!isRefresh) {
                sliders = (List<BannerModel.DataBean.SliderBean>) MusicDataUtils.getInstance(mContext).getSaveData(SAVE_KEY, BannerModel.DataBean.SliderBean.class);
                showBannerData();
            }
        }
    }

    class BannerImageTask extends Thread {
        @Override
        public void run() {
            workService.getBannerImageData(new MusicDataAdapter() {
                @Override
                public void onBannerData(List<BannerModel.DataBean.SliderBean> sliderBeans) {
                    sliders = sliderBeans;
                    showBannerData();
                    MusicDataUtils.getInstance(mContext).saveData(SAVE_KEY, sliders);
                }
            });
        }
    }

    private void showBannerData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                bannerView.setPages(sliders, new MZHolderCreator() {
                    @Override
                    public MZViewHolder createViewHolder() {
                        return new BannerHolder();
                    }
                });
                startBanner();
            }
        });
    }

    public void startBanner() {
        bannerView.start();
    }

    public void stopBanner() {
        bannerView.pause();
    }

    class BannerHolder implements MZViewHolder<BannerModel.DataBean.SliderBean> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = View.inflate(context, R.layout.banner_item_view, null);
            imageView = view.findViewById(R.id.banner_item_view);
            return view;
        }

        @Override
        public void onBind(Context context, int position, BannerModel.DataBean.SliderBean data) {
            Glide.with(context).load(data.getPicUrl()).crossFade().into(imageView);
        }
    }

    private void setBannerListener() {
        bannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                String infoLink = sliders.get(position).getLinkUrl();
                ActivityManager.startActivity(mContext, BannerViewActivity.class, infoLink);
            }
        });
        bannerView.setOnScrollChangeListener(new MZBannerView.BannerScrollChangeListener() {
            @Override
            public void onScroll(boolean isScroll) {
                scrollListener.onScroll(isScroll);
            }
        });
    }


    public void refreshData() {
        isRefresh = true;
        loadBannerData();
    }

    private OnViewPagerScrollListener scrollListener;

    public void setOnViewPagerScrollListener(OnViewPagerScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public interface OnViewPagerScrollListener {
        //用于解决viewpager与下拉刷新华东冲突问题
        void onScroll(boolean isScroll);
    }
}
