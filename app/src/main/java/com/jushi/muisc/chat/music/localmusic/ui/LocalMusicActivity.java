package com.jushi.muisc.chat.music.localmusic.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.localmusic.adapter.LocalMusicAdapter;
import com.jushi.muisc.chat.music.play_navgation.PlayController;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.music.localmusic.model.Song;
import com.jushi.muisc.chat.utils.DisplayUtils;
import com.jushi.muisc.chat.utils.LocalMusicUtils;
import com.jushi.muisc.chat.utils.ShadowUtils;
import com.jushi.muisc.chat.utils.ToastUtils;
import com.jushi.muisc.chat.view.JSTextView;

import java.util.ArrayList;
import java.util.List;

public class LocalMusicActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private Toolbar toolbar;
    private RadioButton searchBtn;
    private EditText editText;
    private RelativeLayout titleContainer, playAllLayout;
    //显示歌曲数量
    private JSTextView tvMusicNum;
    private RecyclerView recyclerView;
    private LocalMusicAdapter musicAdapter;
    private List<Song> songs;
    private PlayController playController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        setContentView(R.layout.activity_local_music);
        initView();
    }

    private void initView() {
        findWidget();
    }

    private void findWidget() {
        toolbar = findViewById(R.id.local_music_activity_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        ShadowUtils.setShadowDown_2(this,toolbar);
        searchBtn = findViewById(R.id.local_music_activity_search_btn);
        searchBtn.setOnClickListener(this);
        editText = findViewById(R.id.local_music_activity_editText);
        titleContainer = findViewById(R.id.local_music_activity_title_layout);
        playAllLayout = findViewById(R.id.play_all_music_layout);
        ShadowUtils.setShadowDown_2(this, playAllLayout);
        playAllLayout.setOnClickListener(this);
        tvMusicNum = findViewById(R.id.play_all_music_number);

        recyclerView = findViewById(R.id.local_music_activity_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //初始化底部的播放控制栏
    private void initPlayController() {
        playController = PlayController.getInstance(this);
        playController.showPlayControllerInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
        initPlayController();
        setEditTextListener();
    }

    //请求SD卡读写权限
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            getLocalMusicData();
        }
    }

    //获取本地歌曲
    private void getLocalMusicData() {
        if (songs == null) {
            songs = LocalMusicUtils.getSongs(this);
        }
        musicAdapter = new LocalMusicAdapter(this, songs);
        recyclerView.setAdapter(musicAdapter);
        tvMusicNum.setText(String.valueOf(songs.size()));
        setItemClickListener();
//        if (playController.isPlaying()){
//            musicAdapter.setStateChange(playController.getIndex());
//        }
    }

    private void setEditTextListener() {
        editText.setOnEditorActionListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                update(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //设置Item点击事件
    private void setItemClickListener() {
        musicAdapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Song song, int position) {
                musicAdapter.setStateChange(position);
                //设置播放列表到播放控制栏
                playController.setPlayList(songs);
                //播放当前被点击的歌曲
                playController.playOneMusic(song, position);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (editText.getVisibility() == View.GONE) {
                    finish();
                } else {
                    setTitleBarStateChange();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (editText.getVisibility() == View.GONE) {
            finish();
        } else {
            setTitleBarStateChange();
        }
    }

    //显示和隐藏标题栏以及搜索框（搜索本地歌曲）
    private void setTitleBarStateChange() {
        if (editText.getVisibility() == View.GONE) {
            editText.setVisibility(View.VISIBLE);
            titleContainer.setVisibility(View.GONE);
        } else {
            editText.setText("");
            editText.setVisibility(View.GONE);
            titleContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.local_music_activity_search_btn: //点击搜索按钮
                setTitleBarStateChange();
                break;
            case R.id.play_all_music_layout:  //点击播放播放
                if (songs.size() > 0){
                    playController.setPlayList(songs);
                    playController.playAllMusic();
                    musicAdapter.setStateChange(0);
                    ActivityManager.startPlayMusicActivity(this);
                }else {
                    ToastUtils.show(this,"暂无本地歌曲");
                }
                break;
        }
    }

    //搜索本地歌曲时动态改变数据
    private List<Song> songList = new ArrayList<>();
    public void update(String keyWord) {
        songList.clear();
        songList.addAll(songs);
        try {
            if (keyWord == null || keyWord.length() == 0) {
                songs.clear();
                songs = LocalMusicUtils.getSongs(this);
                musicAdapter.notifyDataSetChanged();
                tvMusicNum.setText(String.valueOf(songs.size()));
                return;
            }

            boolean b = true;
            for (char c : keyWord.toCharArray()) {
                if (c != ' ') {
                    b = false;
                    break;
                }
            }

            if (b) {
                songs.clear();
                musicAdapter.notifyDataSetChanged();
                return;
            }

//            List<Song> songList = LocalMusicUtils.getSongs(this);

            songs.clear();
            for (Song i : songList) {
                String name = i.getSongName();
                String arts = i.getSongAuthor();
                String album = i.getSongAlbum();

                if (name.contains(keyWord) || arts.contains(keyWord) || album.contains(keyWord)) {
                    songs.add(i);
                }
            }
            musicAdapter.notifyDataSetChanged();
            tvMusicNum.setText(String.valueOf(songs.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //回车键按下处理
        //判断event不为空，并且是回车键按下
        if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                actionId == event.getAction()) {
            //获得输入的内容
            String inputKeyWords = editText.getText().toString();
            if (!inputKeyWords.equals("")) {
            }
        }
        return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }
}
