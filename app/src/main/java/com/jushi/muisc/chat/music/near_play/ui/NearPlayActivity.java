package com.jushi.muisc.chat.music.near_play.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.near_play.controller.NearPlayController;
import com.jushi.muisc.chat.music.near_play.minterface.INearPlayView;


public class NearPlayActivity extends AppCompatActivity implements INearPlayView {

    private Toolbar toolbar;
    private TextView tvMusicNum;
    private RelativeLayout playAllLayout;
    private RecyclerView recyclerView;
    private NearPlayController playController;

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
        playController = new NearPlayController();
        playController.onNearPlayView(this);
        playController.initNearPlayData();
    }

    private void findWidget() {
        toolbar = findViewById(R.id.activity_near_play_toolbar);
        tvMusicNum = findViewById(R.id.play_all_music_number);
        playAllLayout = findViewById(R.id.play_all_music_layout);
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
                playController.onPlayAllBtnClick();
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
