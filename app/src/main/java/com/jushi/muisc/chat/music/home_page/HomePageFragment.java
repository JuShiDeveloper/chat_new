package com.jushi.muisc.chat.music.home_page;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jushi.muisc.chat.R;
import com.jushi.base.fragment.ViewPagerFragment;
import com.jushi.muisc.chat.music.home_page.banner.controller.BannerImageController;
import com.jushi.muisc.chat.music.home_page.artist.controller.HotArtistController;
import com.jushi.muisc.chat.music.chart.ChartFragment;
import com.jushi.muisc.chat.music.zhibo_video.VideoAndLiveFragment;
import com.jushi.muisc.chat.music.home_page.recommend.controller.TodayRecommendController;
import com.jushi.muisc.chat.common.view.RefreshHeadView;

/**
 * 首页
 */
public class HomePageFragment extends ViewPagerFragment {

//    private View rootView;
    private PullToRefreshLayout refreshLayout;
    private Handler handler;
    //轮播图
    private BannerImageController bannerController;
    //今日推荐
    private TodayRecommendController recommendController;
    //热门歌手
    private HotArtistController hotArtistController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_music_home_page, container, false);
        handler = new Handler();
        initView();
        return rootView;
    }

    private void initView() {
        findWidget();
        setRefreshListener();
        initMusicData();
        setBannerListener();
    }


    private void findWidget() {
        refreshLayout = rootView.findViewById(R.id.homepage_refreshLayout);
        refreshLayout.setCustomRefreshView(new RefreshHeadView(getContext()));
    }

    private void initMusicData() {
        //初始化轮播图数据
        bannerController = new BannerImageController(getContext());
        bannerController.initBannerView(rootView);
        //初始化今日推荐数据
        recommendController = new TodayRecommendController(getContext());
        recommendController.initView(rootView);
        //初始化热门歌手
        hotArtistController = new HotArtistController(getContext());
        hotArtistController.initView(rootView);
    }

    //手动滑动banner图时不可下拉刷新
    private void setBannerListener(){
        bannerController.setOnViewPagerScrollListener(new BannerImageController.OnViewPagerScrollListener() {
            @Override
            public void onScroll(boolean isScroll) {
                if (isScroll){
                    refreshLayout.setPullDownEnable(false);
                }else {
                    refreshLayout.setPullDownEnable(true);
                }
            }
        });
    }

    private void setRefreshListener() {
        refreshLayout.setPullUpEnable(false);
        refreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                toRefresh();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }, 500);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });
    }

    //刷新数据
    private void toRefresh() {
        //刷新首页中banner数据
        bannerController.refreshData();
        //刷新首页今日推荐数据
        recommendController.refreshData();
        //刷新首页歌手数据
        hotArtistController.refreshData();
        //刷新MV和直播数据
        VideoAndLiveFragment.refreshData();
        //刷新榜单数据
        ChartFragment.refreshData();
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerController.startBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        bannerController.stopBanner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
