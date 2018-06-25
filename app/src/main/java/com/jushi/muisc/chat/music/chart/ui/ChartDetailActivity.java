package com.jushi.muisc.chat.music.chart.ui;

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
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.chart.adapter.ChartDetailAdapter;
import com.jushi.muisc.chat.music.chart.adapter.OtherChartDetailAdapter;
import com.jushi.muisc.chat.music.play_navgation.PlayController;
import com.jushi.muisc.chat.music.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.music.chart.model.ChartDataModel;
import com.jushi.muisc.chat.music.public_model.LatestMusicModel;
import com.jushi.muisc.chat.music.localmusic.model.Song;
import com.jushi.muisc.chat.music.public_model.SongDetail;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.utils.Constant;
import com.jushi.muisc.chat.utils.DataUrlUtils;
import com.jushi.muisc.chat.utils.DisplayUtils;
import com.jushi.muisc.chat.utils.LocalMusicUtils;
import com.jushi.muisc.chat.utils.ShadowUtils;
import com.jushi.muisc.chat.utils.ToastUtils;
import com.jushi.muisc.chat.view.JSTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示榜单详细信息的页面
 */
public class ChartDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private String chartTitle, link, chartUrl;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private JSTextView tvLoading, tvMusicNum;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private NetWorkService workService;
    private RelativeLayout playAllLayout;
    private Handler handler;
    private ChartDetailAdapter detailAdapter;
    private OtherChartDetailAdapter otherDetailAdapter;
    private List<LatestMusicModel.SongListBean> listBeans = new ArrayList<>();
    private List<Song> songs = new ArrayList<>();
    private PlayController playController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recommend);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        chartTitle = getIntent().getStringExtra("chartTitle");
        link = getIntent().getStringExtra("chartLink");
        chartUrl = DataUrlUtils.getDetailChartUrl(chartTitle, 200);
        handler = new Handler();
        workService = NetWorkService.getInstance(this);
        initView();
    }

    private void initView() {
        findWidget();
        initToolBar();
        initCollapsingToolbarLayout();
        getDetailData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playController = PlayController.getInstance(this);
        playController.showPlayControllerInfo();
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
        tvLoading = findViewById(R.id.all_recommend_tv_loading);
        tvMusicNum = findViewById(R.id.play_all_music_number);

        recyclerSetAdapter();
    }

    //需要判断chartUrl是否为空，因为有些榜单没有相应的Url
    //没有Url的只显示榜单列表中的几条数据
    private void recyclerSetAdapter() {
        if (chartUrl != null) {
            detailAdapter = new ChartDetailAdapter(this, listBeans);
            recyclerView.setAdapter(detailAdapter);
            detailAdapter.setOnItemClickListener(new ChartDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(LatestMusicModel.SongListBean listBean, int position) {
                    detailAdapter.setPositionChanged(position);
                    playOneMusic(position);
                }
            });
        } else {
            new SongInfoTask().run();
            otherDetailAdapter = new OtherChartDetailAdapter(this);
            recyclerView.setAdapter(otherDetailAdapter);
            showRecyclerView();
            Glide.with(this).load(Constant.contentBeanX.getPic_s192()).crossFade().into(imageView);
            otherDetailAdapter.setOnItemClickListener(new OtherChartDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(ChartDataModel.ContentBeanX.ContentBean beanX, int position) {
                    otherDetailAdapter.setPositionChanged(position);
                    playOneMusic(position);
                }
            });
        }
    }

    private void playOneMusic(int position) {
        if (position < songs.size()) {
            playController.playOneMusic(songs.get(position), position);
        } else {
            ToastUtils.show(this, "正在努力加载中");
        }
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initCollapsingToolbarLayout() {
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color._333333));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color._333333));
        collapsingToolbarLayout.setTitle(chartTitle);
    }

    private void getDetailData() {
        if (chartUrl != null) {
            getChartDetailData();
        } else {
            tvMusicNum.setText(String.valueOf(Constant.contentBeanX.getContent().size()));
        }
    }
    //榜单数据Url不为空时获取新的数据
    private void getChartDetailData() {
        new ChartDetailTask().run();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_all_music_layout:
                if (songs.size() < listBeans.size()){
                    ToastUtils.show(this,"正在努力加载中");
                }else {
                    playController.setPlayList(songs);
                    playController.playAllMusic();
                    detailAdapter.setPositionChanged(0);
                    ActivityManager.startPlayMusicActivity(this);
                }
                break;
        }
    }

    class ChartDetailTask extends Thread {
        @Override
        public void run() {
            workService.getChartDetailMusicData(chartUrl, new MusicDataAdapter() {
                @Override
                public void onLatestMusicListData(List<LatestMusicModel.SongListBean> songListBeans) {
                    listBeans.addAll(songListBeans);
                    showChartDetailData();
                    new SongInfoTask().run();
                }

                @Override
                public void onLatestMusicBillboardData(final LatestMusicModel.BillboardBean billboardBean) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Glide.with(ChartDetailActivity.this).load(billboardBean.getPic_s192()).crossFade().into(imageView);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
    }

    private void showChartDetailData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                detailAdapter.notifyDataSetChanged();
                showRecyclerView();
                tvMusicNum.setText(String.valueOf(listBeans.size()));
            }
        });
    }

    private void showRecyclerView() {
        if (recyclerView.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
            tvLoading.setVisibility(View.INVISIBLE);
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

    //数据转换，将所有歌曲信息转为Song对象，便于播放
    class SongInfoTask extends Thread {
        @Override
        public void run() {
            songs.clear();
            if (chartUrl != null) {
                for (LatestMusicModel.SongListBean listBean : listBeans) {
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
            } else {
                for (ChartDataModel.ContentBeanX.ContentBean listBean : Constant.contentBeanX.getContent()) {
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
}
