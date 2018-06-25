package com.jushi.muisc.chat.music.artist.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.artist.ui.AllArtistActivity;
import com.jushi.muisc.chat.music.artist.adapter.HotArtistAdapter;
import com.jushi.muisc.chat.music.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.manager.JSGridLayoutManager;
import com.jushi.muisc.chat.music.artist.model.ArtistsModel;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.utils.DataUrlUtils;
import com.jushi.muisc.chat.utils.Utils;

import java.util.List;

/**
 * 首页中的热门歌手
 */

public class HotArtistController implements View.OnClickListener {
    private Context mContext;
    private RecyclerView recyclerView;
    private RadioButton moreButton;
    private Handler handler;
    private NetWorkService workService;
    private HotArtistTask artistTask;
    private List<ArtistsModel.ArtistBean> artists;
    private HotArtistAdapter artistAdapter;

    public HotArtistController(Context mContext) {
        this.mContext = mContext;
        handler = new Handler();
        workService = NetWorkService.getInstance(mContext);
    }

    public void initView(View rootView){
        recyclerView = rootView.findViewById(R.id.hot_artist_recyclerView);
        JSGridLayoutManager manager = new JSGridLayoutManager(mContext,3);
        manager.setScrollEnable(false);
        recyclerView.setLayoutManager(manager);
        Utils.setArtistRecyclerViewParams((Activity) mContext,recyclerView);
        moreButton = rootView.findViewById(R.id.hot_artist_moreButton);
        moreButton.setOnClickListener(this);

        getHotArtistData();
    }

    private void getHotArtistData() {
        loadHotArtistData();
    }

    private void loadHotArtistData() {
        artistTask = new HotArtistTask();
        artistTask.run();
    }

    public void refreshData(){
        if (artists == null)
            return;
        artists.clear();
        loadHotArtistData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hot_artist_moreButton:
                ActivityManager.startActivity(mContext, AllArtistActivity.class);
                break;
        }
    }

    class HotArtistTask extends Thread{
        @Override
        public void run() {
            workService.getArtistData(DataUrlUtils.getHotArtistsUrl(6), new MusicDataAdapter() {
                @Override
                public void onArtistData(List<ArtistsModel.ArtistBean> artistBeans) {
                    artists = artistBeans;
                    showHotArtistData();
                }
            });
        }
    }

    private void showHotArtistData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                artistAdapter = new HotArtistAdapter(mContext,artists);
                recyclerView.setAdapter(artistAdapter);
                setItemClickListener();
            }
        });
    }

    private void setItemClickListener() {
        artistAdapter.setOnItemClickListener(new HotArtistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArtistsModel.ArtistBean artistBean, int position) {
                ActivityManager.startArtistMusicActivity(mContext,artistBean.getTing_uid(),artistBean.getName());
            }
        });
    }
}
