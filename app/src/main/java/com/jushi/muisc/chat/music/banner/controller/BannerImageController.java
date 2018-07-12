package com.jushi.muisc.chat.music.banner.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.banner.ui.BannerViewActivity;
import com.jushi.muisc.chat.music.banner.adapter.BannerImageAdapter;
import com.jushi.muisc.chat.music.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.music.banner.model.BannerModel;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.music.utils.MusicDataUtils;
import com.jushi.muisc.chat.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 首页轮播图
 */

public class BannerImageController {
    private final String SAVE_KEY = "sliderBeans";
    private Context mContext;
    private Handler handler;
    private ViewPager viewPager;
    private List<View> views = new ArrayList<>();
    private List<View> dotsList = new ArrayList<>();
    private LinearLayout dotsContainer;
    private NetWorkService workService;
    private BannerImageTask bannerImageTask;
    private BannerImageAdapter bannerImageAdapter;
    private int oldPosition = 0;
    private int currentItem;
    private List<BannerModel.DataBean.SliderBean> sliders;
    private Timer timer;
    private boolean isRefresh = false;

    public BannerImageController(Context mContext) {
        this.mContext = mContext;
        handler = new Handler();
        workService = NetWorkService.getInstance(mContext);
    }

    public void initBannerView(View rootView) {
        viewPager = rootView.findViewById(R.id.ViewPager_music_home_page_fragment);
        dotsContainer = rootView.findViewById(R.id.dots_container);

        loadBannerData();
        setViewPagerListener();
        startTimer();
        setViewPagerTouch();
    }

    private void loadBannerData() {
        if (NetWorkUtils.isNetworkAvailable(mContext)) {
            bannerImageTask = new BannerImageTask();
            bannerImageTask.run();
        }else {
            if (!isRefresh){
                sliders = (List<BannerModel.DataBean.SliderBean>) MusicDataUtils.getInstance(mContext).getSaveData(SAVE_KEY,BannerModel.DataBean.SliderBean.class);
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
                    MusicDataUtils.getInstance(mContext).saveData(SAVE_KEY,sliders);
                }
            });
        }
    }

    private void showBannerData() {
        views.clear();
        dotsList.clear();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dotsContainer.getChildCount() > 0)
                    dotsContainer.removeAllViews();
            }
        });
        for (int i = 0; i < sliders.size(); i++) {
            final ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            views.add(imageView);
            final int finalI = i;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Glide.with(mContext).load(sliders.get(finalI).getPicUrl()).crossFade().into(imageView);
                    createDots();
                }
            });
        }
        viewPagerSetAdapter();
    }

    private void createDots() {
        View dot = new View(mContext);
        dotsContainer.addView(dot);
        dot.setBackgroundResource(R.drawable.banner_unselect_drawable);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(22, 22);
        params.leftMargin = 10;
        params.rightMargin = 10;
        dot.setLayoutParams(params);
        dotsList.add(dot);
        dotsList.get(0).setBackgroundResource(R.drawable.banner_select_drawable);
    }

    private void viewPagerSetAdapter() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                bannerImageAdapter = new BannerImageAdapter(views);
                viewPager.setAdapter(bannerImageAdapter);
                viewPager.setOffscreenPageLimit(1);
                setViewClickListener();
            }
        });
    }

    private void setViewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotsList.get(oldPosition).setBackgroundResource(R.drawable.banner_unselect_drawable);
                currentItem = position % views.size();
                dotsList.get(currentItem).setBackgroundResource(R.drawable.banner_select_drawable);
                oldPosition = position % views.size();
                setViewClickListener();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setViewClickListener() {
        View view = views.get(oldPosition);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String infoLink = sliders.get(oldPosition).getLinkUrl();
                ActivityManager.startActivity(mContext, BannerViewActivity.class, infoLink);
            }
        });
        setViewTouchListener(view);
    }

    private void setViewTouchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timer.cancel();
                        viewPagerScroll(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        timer.cancel();
                        viewPagerScroll(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        startTimer();
                        viewPagerScroll(false);
                        break;
                }
                return false;
            }
        });
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int current = viewPager.getCurrentItem();
                        viewPager.setCurrentItem(current + 1);
                    }
                });
            }
        }, 4000, 4000);
    }

    private void setViewPagerTouch() {
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timer.cancel();
                        viewPagerScroll(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        timer.cancel();
                        viewPagerScroll(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        startTimer();
                        viewPagerScroll(false);
                        break;
                }
                return false;
            }
        });
    }

    public void refreshData() {
        isRefresh = true;
        loadBannerData();
    }

    private void viewPagerScroll(boolean isScroll) {
        if (scrollListener != null) {
            scrollListener.onScroll(isScroll);
        }
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
