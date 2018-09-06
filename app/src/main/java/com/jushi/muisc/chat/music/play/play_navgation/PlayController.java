package com.jushi.muisc.chat.music.play.play_navgation;

import android.app.Activity;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.play.play_music.PlayMusicService;
import com.jushi.muisc.chat.music.utils.SaveUtils;
import com.jushi.muisc.chat.view.PlayMusicView;

import java.util.List;

/**
 * 播放控制栏，控制类
 */
public class PlayController implements PlayMusicService.OnMusicPlayListener, PlayMusicView.OnPlayBtnIsPlayingListener {
    private static Activity activity;
    private PlayMusicView controllerView;
    private SaveUtils saveUtils;
    private String songName, songAuthor, authorImage;
    private int index;
    private List<Song> songs;


    private static PlayController playController;

    private PlayController() {
    }

    public static PlayController getInstance(Activity mActivity) {
        activity = mActivity;
        if (playController == null) {
            playController = new PlayController();
        }
        PlayMusicService.checkStartService(mActivity);
        return playController;
    }

    //显示播放控制栏信息
    public void showPlayControllerInfo() {
        controllerView = activity.findViewById(R.id.PlayMusicView);
        //播放按钮状态监听
        controllerView.setOnPlayBtnIsPlayingListener(this);
        //检查是否在播放
        controllerView.checkMusicIsPlaying(isPlaying());
        //获取保存的数据
        getSaveUtils();
        //设置歌曲播放监听
        PlayMusicService.setOnMusicPlayListener(this);
    }

    //获取保存的歌曲信息
    private void getSaveUtils() {
        if (saveUtils == null) {
            saveUtils = SaveUtils.getInstance(activity);
        }
        songName = saveUtils.getSavedSongName();
        songAuthor = saveUtils.getSaveAuthor();
        authorImage = saveUtils.getSaveAuthorImage(); //图片地址
        if (songName == null && songs.size() > 0) {
            songName = songs.get(0).getSongName();
            songAuthor = songs.get(0).getSongAuthor();
            authorImage = songs.get(0).getSongImagePath();
        }
        showSongInfo();
    }

    //显示歌曲信息
    private void showSongInfo() {
        controllerView.showSongInfo(songName, songAuthor);
        controllerView.showAuthorImage(authorImage);
    }

    //传递播放列表
    public void setPlayList(List<Song> songs) {
        this.songs = songs;
        PlayMusicService.setPlayList(songs);
    }

    //播放全部
    public void playAllMusic() {
        PlayMusicService.playAllMusic();
    }

    //点击某一首歌曲时播放
    public void playOneMusic(Song song, int position) {
        PlayMusicService.playOneMusic(song, position);
    }

    //是否在播放
    public boolean isPlaying() {
        return PlayMusicService.isPlaying();
    }

    public void destory() {
        activity = null;
        System.gc();
    }

    public int getIndex() {
        return index;
    }

    //播放回调，传递当前播放的歌曲信息过来
    @Override
    public void onMusicPlay(String songName, String author, String imagePath, String lrcPath, int index) {
        this.songName = songName;
        this.songAuthor = author;
        this.authorImage = imagePath;
        this.index = index;
        showSongInfo();
        controllerView.checkMusicIsPlaying(true);
    }

    //播放按钮状态监听
    @Override
    public void onPlayBtnClickPlay(boolean isPlayState) {
        if (isPlayState) {
            PlayMusicService.startPlay();
        } else {
            PlayMusicService.pause();
        }
    }

    public void setControllerViewVisible(int visiblity){
        controllerView.setVisible(visiblity);
    }
}
