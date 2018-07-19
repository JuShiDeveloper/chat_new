package com.jushi.muisc.chat.sliding_menu.near_play;

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
import com.jushi.muisc.chat.sliding_menu.near_play.NearPlayController;
import com.jushi.muisc.chat.sliding_menu.minterface.INearPlayView;
import com.jushi.muisc.chat.music.play_navgation.PlayController;
import com.jushi.muisc.chat.utils.ShadowUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 最近播放页面
 */
public class NearPlayActivity extends AppCompatActivity implements INearPlayView {

    private Toolbar toolbar;
    private TextView tvMusicNum;
    private RelativeLayout playAllLayout;
    private RecyclerView recyclerView;
    private NearPlayController nearPlayController;
    //播放控制栏
    private PlayController playController;

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
        nearPlayController = new NearPlayController();
        nearPlayController.onNearPlayView(this);

        playController = PlayController.getInstance(this);
        playController.showPlayControllerInfo();
        nearPlayController.onPlayController(playController);
    }

    private void findWidget() {
        toolbar = findViewById(R.id.activity_near_play_toolbar);
        toolbar.setTitle(R.string.near_play);
        tvMusicNum = findViewById(R.id.play_all_music_number);
        playAllLayout = findViewById(R.id.play_all_music_layout);
        ShadowUtils.setShadowDown_2(this,playAllLayout);
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
                nearPlayController.onPlayAllBtnClick();
            }
        });
    }

    @Override
    public void onMusicNumber(int num) {
        tvMusicNum.setText(String.valueOf(num));
    }

    @Override
    public void onAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
