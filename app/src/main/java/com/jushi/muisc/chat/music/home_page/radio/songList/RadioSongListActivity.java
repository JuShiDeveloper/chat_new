package com.jushi.muisc.chat.music.home_page.radio.songList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.utils.RefreshViewUtils;
import com.jushi.muisc.chat.music.common.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.music.home_page.artist.adapter.ArtistMusicAdapter;
import com.jushi.muisc.chat.music.home_page.artist.model.ArtistMusic;
import com.jushi.muisc.chat.music.play.play_navgation.PlayController;
import com.jushi.muisc.chat.music.home_page.common.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.music.home_page.radio.entity.RadioSongListEntity;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * 某一电台下的歌曲列表
 * 包括 公共频道/音乐人频道
 */
public class RadioSongListActivity extends AppCompatActivity implements View.OnClickListener, RadioPublicChannelSongListAdapter.OnItemClickListener {
    public static final String URL_KEY = "url_key";
    public static final String TYPE_KET = "type";
    public static final String TITLE_KET = "title";
    private static final String PUBLIC_CHANNEL = "公共频道";
    private static final String MUSIC_CHANNEL = "音乐人频道";
    private ImageView backBtn;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private NetWorkService netWorkService;
    private PlayController playController;
    private String url;
    private String type;
    private List<RadioSongListEntity.ResultBean.SonglistBean> songlistBeans = new ArrayList<>();
    private RadioPublicChannelSongListAdapter publicChannelSongListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_song_list);
        netWorkService = NetWorkService.getInstance(this);
        url = getIntent().getStringExtra(URL_KEY);
        type = getIntent().getStringExtra(TYPE_KET);
        RefreshViewUtils.showRefreshDialog(this);
        initialize();
    }

    private void initialize() {
        initView();
        loadData();
    }

    private void initView() {
        backBtn = findViewById(R.id.radio_song_list_page_back_btn);
        tvTitle = findViewById(R.id.radio_song_list_page_title);
        recyclerView = findViewById(R.id.rv_activity_radio_song_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backBtn.setOnClickListener(this);
    }

    private void loadData() {
        new LoadDataTask().run();
        tvTitle.setText(String.format("%s：%s", PUBLIC_CHANNEL, getIntent().getStringExtra(TITLE_KET)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        playController = PlayController.getInstance(this);
        playController.showPlayControllerInfo();
    }

    /**
     * 公共频道下某一电台数据
     */
    private class LoadDataTask extends Thread {
        @Override
        public void run() {
            netWorkService.getSongListFromOnceRadio(url, new LiveAndMvDataAdapter() {
                @Override
                public void onRadioSongListData(final RadioSongListEntity entity) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showPublicChannelSongList(entity);
                        }
                    });
                }
            });
        }
    }

    private void showPublicChannelSongList(RadioSongListEntity entity) {
        List<RadioSongListEntity.ResultBean.SonglistBean> beans = entity.getResult().getSonglist();
        for (int i = 0; i < beans.size() - 1; i++) {
            if (TextUtils.isEmpty(beans.get(i).getTitle()) || TextUtils.isEmpty(beans.get(i).getArtist())) {
                continue;
            }
            songlistBeans.add(beans.get(i));
        }
        publicChannelSongListAdapter = new RadioPublicChannelSongListAdapter(this, songlistBeans);
        recyclerView.setAdapter(publicChannelSongListAdapter);
        publicChannelSongListAdapter.setOnItemClickListener(this);
        RefreshViewUtils.dismissRefreshDialog();
    }

    @Override
    public void onItemClick(RadioSongListEntity.ResultBean.SonglistBean songlistBean, int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_song_list_page_back_btn:
                finish();
                break;
        }
    }
}
