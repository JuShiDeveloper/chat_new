package com.jushi.muisc.chat.sliding_menu.download_manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.play.play_navgation.PlayController;
import com.jushi.muisc.chat.sliding_menu.common.minterface.INearPlayView;
import com.jushi.muisc.chat.common.utils.ShadowUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 下载管理页面
 */
public class DownloadActivity extends AppCompatActivity implements INearPlayView {

    private Toolbar toolbar;
    private TextView tvMusicNum;
    private RelativeLayout playAllLayout;
    private RecyclerView recyclerView;
    //播放控制栏
    private PlayController playController;
    private DownloadController downloadController;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_play);

        initialize();
    }

    private void initialize() {
        initController();
        findWidget();
        initToolbar();
        setClickListener();
    }

    private void initController() {
        downloadController = new DownloadController(this);
        downloadController.onNearPlayView(this);

        playController = PlayController.getInstance(this);
        playController.showPlayControllerInfo();
        downloadController.onPlayController(playController);
    }

    private void findWidget() {
        toolbar = findViewById(R.id.activity_near_play_toolbar);
        toolbar.setTitle(R.string.download_manager);
        tvMusicNum = findViewById(R.id.play_all_music_number);
        playAllLayout = findViewById(R.id.play_all_music_layout);
        ShadowUtils.setShadowDown_2(this, playAllLayout);
        recyclerView = findViewById(R.id.activity_near_play_RecyclerView);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setClickListener() {
        playAllLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadController.onPlayAllBtnClick();
            }
        });
    }

    @Override
    public void onAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMusicNumber(int num) {
        tvMusicNum.setText(String.valueOf(num));
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
}
