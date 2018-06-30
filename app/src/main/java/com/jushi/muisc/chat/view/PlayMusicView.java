package com.jushi.muisc.chat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.transform.CornersTransform;

/**
 * 底部播放控制栏
 *
 */
public class PlayMusicView extends RelativeLayout implements View.OnClickListener {

    private ImageView authorImage;
    private RadioButton playBtn, playListBtn;
    private LinearLayout infoContainer;
    private JSTextView songName, songAuthor;
    private boolean isPlaying = false;

    public PlayMusicView(Context context) {
        this(context, null);
    }

    public PlayMusicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.play_music_controller_layout, this);
        findWidget();
        setClickListener();
    }

    private void findWidget() {
        authorImage = findViewById(R.id.play_music_controller_layout_author_imageView);
        playBtn = findViewById(R.id.play_music_controller_layout_play_btn);
        playListBtn = findViewById(R.id.play_music_controller_layout_playListMenu_btn);
        infoContainer = findViewById(R.id.play_music_controller_layout_song_info_container);
        songName = findViewById(R.id.play_music_controller_layout_songName);
        songAuthor = findViewById(R.id.play_music_controller_layout_songAuthor);
        songName.setSelected(true);
        songAuthor.setSelected(true);
    }

    private void setClickListener() {
        playBtn.setOnClickListener(this);
        playListBtn.setOnClickListener(this);
        authorImage.setOnClickListener(this);
        infoContainer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_music_controller_layout_play_btn: //play button
                if (isPlaying) {
                    playBtn.setButtonDrawable(R.drawable.pause_controller_icon);
                    isPlaying = false;
                } else {
                    playBtn.setButtonDrawable(R.drawable.play_controller_icon);
                    isPlaying = true;
                }
                if (listener != null) {
                    listener.onPlayBtnClickPlay(this.isPlaying);
                }
                break;
            case R.id.play_music_controller_layout_playListMenu_btn: //play list button
                break;
            case R.id.play_music_controller_layout_song_info_container: //song info container
                ActivityManager.startPlayMusicActivity(getContext());
                break;
        }
    }

    //检查是否在播放
    public void checkMusicIsPlaying(boolean isPlaying) {
        if (isPlaying) {
            playBtn.setButtonDrawable(R.drawable.play_controller_icon);
        } else {
            playBtn.setButtonDrawable(R.drawable.pause_controller_icon);
        }
        this.isPlaying = isPlaying;
    }

    //显示歌曲信息
    public void showSongInfo(String songName, String author) {
        if (!(songName == null))
            this.songName.setText(songName);
        if (!(author == null))
            songAuthor.setText(author);
    }

    //显示图片信息
    public void showAuthorImage(String imagePath) {
        try {
            if (imagePath == null) {
                Glide.with(getContext()).load(R.drawable.default_author_image)
                        .crossFade()
                        .transform(new CornersTransform(getContext()))
                        .into(authorImage);
            } else {
                Glide.with(getContext()).load(imagePath)
                        .crossFade()
                        .transform(new CornersTransform(getContext()))
                        .into(authorImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnPlayBtnIsPlayingListener listener;

    //播放按钮状态监听
    public void setOnPlayBtnIsPlayingListener(OnPlayBtnIsPlayingListener listener) {
        this.listener = listener;
    }

    public interface OnPlayBtnIsPlayingListener {
        void onPlayBtnClickPlay(boolean isPlayState);
    }

}
