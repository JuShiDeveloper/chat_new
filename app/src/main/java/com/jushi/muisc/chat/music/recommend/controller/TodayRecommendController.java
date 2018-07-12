package com.jushi.muisc.chat.music.recommend.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.recommend.ui.AllRecommendActivity;
import com.jushi.muisc.chat.music.recommend.adapter.TodayRecommendAdapter;
import com.jushi.muisc.chat.music.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.manager.JSGridLayoutManager;
import com.jushi.muisc.chat.music.localmusic.model.Song;
import com.jushi.muisc.chat.music.recommend.model.TodayRecommendModel;
import com.jushi.muisc.chat.music.public_model.SongDetail;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.music.service.PlayMusicService;
import com.jushi.muisc.chat.music.utils.LocalMusicUtils;
import com.jushi.muisc.chat.music.utils.MusicDataUtils;
import com.jushi.muisc.chat.utils.NetWorkUtils;
import com.jushi.muisc.chat.utils.ToastUtils;
import com.jushi.muisc.chat.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页今日推荐
 */

public class TodayRecommendController implements View.OnClickListener {
    private final String SAVE_KEY = "recommendBeans";
    private Context mContext;
    private RecyclerView recyclerView;
    private RadioButton moreBtn;
    private DataTask dataTask;
    private NetWorkService workService;
    private Handler handler;
    private List<TodayRecommendModel.ResultBean.ListBean> recommendBeans;
    private TodayRecommendAdapter recommendAdapter;
    private List<Song> songs = new ArrayList<>();
    private boolean isRefresh = false;

    public TodayRecommendController(Context mContext) {
        this.mContext = mContext;
        handler = new Handler();
        workService = NetWorkService.getInstance(mContext);
    }

    public void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.today_recommend_recyclerView);
        JSGridLayoutManager manager = new JSGridLayoutManager(mContext, 3);
        manager.setScrollEnable(false);
        recyclerView.setLayoutManager(manager);
        Utils.setRecyclerViewParams((Activity) mContext, recyclerView);
        moreBtn = rootView.findViewById(R.id.today_recommend_more);
        moreBtn.setOnClickListener(this);

        loadRecommendData();
    }

    private void loadRecommendData() {
        if (NetWorkUtils.isNetworkAvailable(mContext)) {
            dataTask = new DataTask();
            dataTask.run();
        }else {
            if (!isRefresh){
                recommendBeans = (List<TodayRecommendModel.ResultBean.ListBean>) MusicDataUtils.getInstance(mContext).getSaveData(SAVE_KEY,TodayRecommendModel.ResultBean.ListBean.class);
                showRecommendData();
            }
        }
    }

    public void refreshData() {
        isRefresh = true;
        if (recommendBeans == null) {
            recommendBeans = new ArrayList<>();
        }
        recommendBeans.clear();
        loadRecommendData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.today_recommend_more:
                ActivityManager.startActivity(mContext, AllRecommendActivity.class);
                break;
        }
    }

    class DataTask extends Thread {
        @Override
        public void run() {
            workService.getTodayRecommendMusicData(6, new MusicDataAdapter() {
                @Override
                public void onTodayRecommendData(List<TodayRecommendModel.ResultBean.ListBean> listBeans) {
                    recommendBeans = listBeans;
                    showRecommendData();
                    MusicDataUtils.getInstance(mContext).saveData(SAVE_KEY,recommendBeans);
                    new SongInfoTask().run();
                }
            });
        }
    }

    private void showRecommendData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                recommendAdapter = new TodayRecommendAdapter(mContext, recommendBeans);
                recyclerView.setAdapter(recommendAdapter);
                setAdapterClickListener();
            }
        });
    }

    private void setAdapterClickListener() {
        recommendAdapter.setOnItemClickListener(new TodayRecommendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TodayRecommendModel.ResultBean.ListBean listBean, int position) {
                if (position < songs.size()) {
                    PlayMusicService.playOneMusic(songs.get(position), position);
                    ActivityManager.startPlayMusicActivity(mContext);
                } else {
                    ToastUtils.show(mContext, "正在努力加载中");
                }
            }
        });
    }

    class SongInfoTask extends Thread {
        @Override
        public void run() {
            for (TodayRecommendModel.ResultBean.ListBean listBean : recommendBeans) {
                String songId = listBean.getSong_id();
                final Song song = new Song();
                workService.getSongInfo(songId, new MusicDataAdapter() {
                    @Override
                    public void onSongDetail(SongDetail detail) {
                        song.setSongId(detail.getSonginfo().getSong_id());
                        song.setSongName(detail.getSonginfo().getTitle());
                        song.setSongAlbum(detail.getSonginfo().getAlbum_title());
                        song.setSongAuthor(detail.getSonginfo().getAuthor());
                        song.setSongImagePath(detail.getSonginfo().getArtist_list().get(0).getAvatar_s300());
                        song.setSongPath(detail.getBitrate().getShow_link());
                        song.setSongSize(LocalMusicUtils.getSongSize(detail.getBitrate().getFile_size()));
                        song.setSongDuration(detail.getBitrate().getFile_duration());
                        song.setLrcPath(detail.getSonginfo().getLrclink());
                        songs.add(song);
                    }
                });
            }
        }
    }
}
