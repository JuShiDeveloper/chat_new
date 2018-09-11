package com.jushi.muisc.chat.music.home_page.artist.ui;

import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.home_page.artist.adapter.ArtistMusicAdapter;
import com.jushi.muisc.chat.music.play.play_navgation.PlayController;
import com.jushi.muisc.chat.music.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.common.manager.ActivityManager;
import com.jushi.muisc.chat.music.home_page.artist.model.ArtistMusic;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.public_model.SongDetail;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.music.utils.animation.AnimationUtils;
import com.jushi.muisc.chat.common.utils.DisplayUtils;
import com.jushi.muisc.chat.music.utils.music.LocalMusicUtils;
import com.jushi.muisc.chat.common.utils.ShadowUtils;
import com.jushi.muisc.chat.common.utils.ToastUtils;
import com.jushi.muisc.chat.common.view.JSTextView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示歌手的歌的页面
 */
public class ArtistMusicActivity extends AppCompatActivity implements View.OnClickListener {

    private String artistId, artistName;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private JSTextView tvAllMusicNum;
    private ProgressBar progressBar;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private NetWorkService workService;
    private RelativeLayout playAllLayout;
    private Handler handler;
    private PlayController playController;
    private List<ArtistMusic.SonglistBean> songBeans;
    private ArtistMusicAdapter musicAdapter;
    private List<Song> songs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recommend);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        artistId = getIntent().getStringExtra("artistId");
        artistName = getIntent().getStringExtra("artistName");
        workService = NetWorkService.getInstance(this);
        handler = new Handler();
        initView();
    }

    private void initView() {
        findWidget();
        initToolBar();
        initCollapsingToolbarLayout();
        getArtistMusicData();
    }

    private void findWidget() {
        collapsingToolbarLayout = findViewById(R.id.all_recommend_CollapsingToolbarLayout);
        toolbar = findViewById(R.id.all_recommend_toolbar);
        imageView = findViewById(R.id.iv_all_recommend_title_bg);
        recyclerView = findViewById(R.id.all_recommend_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        playAllLayout = findViewById(R.id.play_all_music_layout);
        ShadowUtils.setShadowDown_2(this, playAllLayout);
        playAllLayout.setOnClickListener(this);
        progressBar = findViewById(R.id.all_recommend_tv_loading);
        tvAllMusicNum = findViewById(R.id.play_all_music_number);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initCollapsingToolbarLayout() {
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color._333333));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color._333333));
        collapsingToolbarLayout.setTitle(artistName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        playController = PlayController.getInstance(this);
        playController.showPlayControllerInfo();
    }

    private void getArtistMusicData() {
        new ArtistMusicTask().run();
    }

    class ArtistMusicTask extends Thread {
        @Override
        public void run() {
            workService.getArtistMusics(artistId, new MusicDataAdapter() {
                @Override
                public void onArtistMusics(List<ArtistMusic.SonglistBean> songlistBeans) {
                    songBeans = songlistBeans;
                    showArtistMusicData();
                    new SongInfoTask().run();
                }
            });
        }
    }

    private void showArtistMusicData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.with(ArtistMusicActivity.this).load(songBeans.get(0).getPic_s500()).crossFade().into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                musicAdapter = new ArtistMusicAdapter(ArtistMusicActivity.this, songBeans);
                recyclerView.setAdapter(musicAdapter);
                if (recyclerView.getVisibility() == View.INVISIBLE) {
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                tvAllMusicNum.setText(String.valueOf(songBeans.size()));
                setItemClickListener();
            }
        });
    }

    private void setItemClickListener() {
        musicAdapter.setOnItemClickListener(new ArtistMusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArtistMusic.SonglistBean songBean, int position) {
                musicAdapter.setSelectChanged(position);
                if (position < songs.size()) {
                    playController.playOneMusic(songs.get(position), position);
                } else {
                    ToastUtils.show(ArtistMusicActivity.this, "正在努力加载中");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_all_music_layout:
                if (songs.size() < songBeans.size()) {
                    ToastUtils.show(this, "正在努力加载中");
                } else {
                    playController.setPlayList(songs);
                    playController.playAllMusic();
                    musicAdapter.setSelectChanged(0);
                    ActivityManager.startPlayMusicActivity(this);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //准备数据，将歌曲信息转为Song类，好统一播放
    class SongInfoTask extends Thread {
        @Override
        public void run() {
            songs.clear();
            for (ArtistMusic.SonglistBean listBean : songBeans) {
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
                        if (songs.size() == songBeans.size()){
                            setAllLayoutVisible();
                        }
                    }
                });
            }
        }
    }

    private void setAllLayoutVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.translateAnimation(playAllLayout);
                playAllLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
