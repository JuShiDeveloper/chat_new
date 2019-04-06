package com.jushi.muisc.chat.music.home_page.artist.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.common.jsinterface.OnRequestListener;
import com.jushi.muisc.chat.music.home_page.HomePageFragment;
import com.jushi.muisc.chat.music.home_page.artist.AllArtistActivity;
import com.jushi.muisc.chat.music.home_page.artist.adapter.HotArtistAdapter;
import com.jushi.muisc.chat.music.common.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.common.manager.ActivityManager;
import com.jushi.muisc.chat.common.manager.JSGridLayoutManager;
import com.jushi.muisc.chat.music.home_page.artist.model.ArtistsModel;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.music.common.utils.music.DataUrlUtils;
import com.jushi.muisc.chat.music.common.utils.music.MusicDataUtils;
import com.jushi.muisc.chat.common.utils.NetWorkUtils;
import com.jushi.muisc.chat.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页中的热门歌手
 */

public class HotArtistController implements View.OnClickListener {
    private final String SAVE_KEY = "artistBeans";
    private Context mContext;
    private RecyclerView recyclerView;
    private RadioButton moreButton;
    private Handler handler;
    private NetWorkService workService;
    private List<ArtistsModel.ArtistBean> artists;
    private HotArtistAdapter artistAdapter;
    private boolean isRefresh = false;
    private OnRequestListener requestListener;

    public HotArtistController(Context mContext, OnRequestListener requestListener) {
        this.mContext = mContext;
        this.requestListener = requestListener;
        handler = new Handler();
        workService = NetWorkService.getInstance(mContext);
    }

    public void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.hot_artist_recyclerView);
        JSGridLayoutManager manager = new JSGridLayoutManager(mContext, 3);
        manager.setScrollEnable(false);
        recyclerView.setLayoutManager(manager);
        Utils.setArtistRecyclerViewParams((Activity) mContext, recyclerView);
        moreButton = rootView.findViewById(R.id.hot_artist_moreButton);
        moreButton.setOnClickListener(this);

        getHotArtistData();
    }

    private void getHotArtistData() {
        loadHotArtistData();
    }

    private void loadHotArtistData() {
        if (NetWorkUtils.isNetworkAvailable(mContext)) {
            new HotArtistTask().run();
        } else {
            if (!isRefresh) {
                artists = (List<ArtistsModel.ArtistBean>) MusicDataUtils.getInstance(mContext).getSaveData(SAVE_KEY, ArtistsModel.ArtistBean.class);
                showHotArtistData();
            }
        }
    }

    public void refreshData() {
        isRefresh = true;
        if (artists == null) {
            artists = new ArrayList<>();
        }
        artists.clear();
        loadHotArtistData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hot_artist_moreButton:
                ActivityManager.startActivity(mContext, AllArtistActivity.class);
                break;
        }
    }

    class HotArtistTask extends Thread {
        @Override
        public void run() {
            workService.getArtistData(DataUrlUtils.getHotArtistsUrl(6), new MusicDataAdapter() {
                @Override
                public void onArtistData(List<ArtistsModel.ArtistBean> artistBeans) {
                    artists = artistBeans;
                    showHotArtistData();
                    MusicDataUtils.getInstance(mContext).saveData(SAVE_KEY, artists);
                }

                @Override
                public void onError() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestListener.onRequestFiled();
                        }
                    });
                }
            });
        }
    }

    private void showHotArtistData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                artistAdapter = new HotArtistAdapter(mContext, artists);
                recyclerView.setAdapter(artistAdapter);
                setItemClickListener();
                requestListener.onRequestSuccess();
            }
        });
    }

    private void setItemClickListener() {
        artistAdapter.setOnItemClickListener(new HotArtistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArtistsModel.ArtistBean artistBean, int position) {
                ActivityManager.startArtistMusicActivity(mContext, artistBean.getTing_uid(), artistBean.getName());
            }
        });
    }
}
