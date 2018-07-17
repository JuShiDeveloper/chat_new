package com.jushi.muisc.chat.music.recommend.ui;

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
import com.jushi.muisc.chat.music.recommend.adapter.AllRecommendAdapter;
import com.jushi.muisc.chat.music.play_navgation.PlayController;
import com.jushi.muisc.chat.music.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.recommend.model.TodayRecommendModel;
import com.jushi.muisc.chat.music.public_model.SongDetail;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.music.utils.AnimationUtils;
import com.jushi.muisc.chat.utils.DisplayUtils;
import com.jushi.muisc.chat.music.utils.LocalMusicUtils;
import com.jushi.muisc.chat.utils.ShadowUtils;
import com.jushi.muisc.chat.utils.ToastUtils;
import com.jushi.muisc.chat.view.JSTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 所有的今日推荐类
 */
public class AllRecommendActivity extends AppCompatActivity implements View.OnClickListener {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private JSTextView tvAllMusicNum;
    private ProgressBar progressBar;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private NetWorkService workService;
    private RelativeLayout playAllLayout;
    private Handler handler;
    private RecommendTadk recommendTadk;
    private List<TodayRecommendModel.ResultBean.ListBean> listBeans;
    private List<Song> songs = new ArrayList<>();
    private AllRecommendAdapter recommendAdapter;
    private PlayController playController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recommend);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        workService = NetWorkService.getInstance(this);
        handler = new Handler();
        initView();
    }

    private void initView() {
        findWidget();
        initToolBar();
        initCollapsingToolbarLayout();
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

        getRecommendData();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initCollapsingToolbarLayout() {
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color._333333));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color._333333));
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.today_recommend));
    }

    @Override
    protected void onResume() {
        super.onResume();
        playController = PlayController.getInstance(this);
        playController.showPlayControllerInfo();
    }

    private void getRecommendData() {
        recommendTadk = new RecommendTadk();
        recommendTadk.run();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_all_music_layout:
                if (songs.size() < listBeans.size()) {
                    ToastUtils.show(this, "正在努力加载中");
                } else {
                    playController.setPlayList(songs);
                    playController.playAllMusic();
                    recommendAdapter.setDateChanged(0);
                    ActivityManager.startPlayMusicActivity(this);
                }
                break;
        }
    }

    class RecommendTadk extends Thread {
        @Override
        public void run() {
            workService.getTodayRecommendMusicData(100, new MusicDataAdapter() {
                @Override
                public void onTodayRecommendData(List<TodayRecommendModel.ResultBean.ListBean> beans) {
                    listBeans = beans;
                    showRecommendData();
                    new SongInfoTask().run();
                }
            });
        }
    }

    private void showRecommendData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int index = random.nextInt(listBeans.size() - 1);
                try {
                    Glide.with(AllRecommendActivity.this).load(listBeans.get(index).getPic_premium()).crossFade().into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recommendAdapter = new AllRecommendAdapter(AllRecommendActivity.this, listBeans);
                recyclerView.setAdapter(recommendAdapter);
                if (recyclerView.getVisibility() == View.INVISIBLE) {
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                tvAllMusicNum.setText(String.valueOf(listBeans.size()));
                setItemClickListener();
            }
        });
    }

    private void setItemClickListener() {
        recommendAdapter.setOnItemClickListener(new AllRecommendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TodayRecommendModel.ResultBean.ListBean listBean, int position) {
                recommendAdapter.setDateChanged(position);
                if (position < songs.size()) {
                    playController.playOneMusic(songs.get(position), position);
                } else {
                    ToastUtils.show(AllRecommendActivity.this, "正在努力加载中");
                }
            }
        });
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

    //数据转换
    class SongInfoTask extends Thread {
        @Override
        public void run() {
            songs.clear();
            for (TodayRecommendModel.ResultBean.ListBean listBean : listBeans) {
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
                        if (songs.size() == listBeans.size()) {
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
