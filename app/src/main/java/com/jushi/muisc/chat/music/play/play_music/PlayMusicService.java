package com.jushi.muisc.chat.music.play.play_music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jushi.muisc.chat.app.JSApplication;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.common.utils.music.LocalMusicUtils;
import com.jushi.muisc.chat.common.utils.SaveUtils;

import java.util.List;

public class PlayMusicService extends Service {
    private static Context mContext;
    private static boolean isRunning = false;
    //播放歌曲的类
    private static PlayMusic playMusic;
    //播放列表
    private static List<Song> songs;
    //记录当前播放的歌曲在列表中的位置
    private static int index;
    //音乐播放回调
    private static OnMusicPlayListener listener;
    private static String songName, author, imagePath, lrcPath;
    private static SaveUtils saveUtils;
    //判断是不是暂停
    private static boolean isPaused = false;
    private static Song songInfo;

    public static void checkStartService(Context context) {
        mContext = context;
        if (!isRunning) {
            context.startService(new Intent(context, PlayMusicService.class));
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning = true;
        playMusic = new PlayMusic();
        saveUtils = SaveUtils.getInstance(getApplicationContext());
        index = saveUtils.getSaveIndex(); //在列表中的位置下标
    }

    //将播放列表传递进来
    public static void setPlayList(List<Song> songs1) {
        songs = songs1;
    }

    /**
     * 将当前播放列表传递到显示播放列表歌曲的dialog中
     *
     * @return
     */
    public static List<Song> getSongs() {
        if (songs == null || songs.size() == 0) {
            songs = LocalMusicUtils.getSongs(JSApplication.getContext());
        }
        return songs;
    }

    //播放全部歌曲
    public static void playAllMusic() {
        index = 0;
        if (songs.size() > 0) {
            songInfo = songs.get(index);
            setMusicData(songInfo.getSongPath(), index);
        }
    }

    //播放一首歌曲，点击单个item时调用
    public static void playOneMusic(Song song, int position) {
        songInfo = song;
        setMusicData(song.getSongPath(), position);
    }

    //设置歌曲路径
    private static void setMusicData(String songPath, int position) {
        index = position;
        playMusic.setData(songPath);
        play();
    }

    //调用播放类的方法真正的播放
    private static void play() {
        playMusic.play();
        getSongInfo();
        saveSongToDB();
    }

    //开始播放
    public static void startPlay() {
        if (songInfo == null) {  //如果songInfo为空，获取上一次播放时保持的信息
            songInfo = new Song();
            songInfo.setSongName(saveUtils.getSavedSongName()); //名称
            songInfo.setSongPath(saveUtils.getSavedSongPath());  //歌曲路径
            songInfo.setSongAuthor(saveUtils.getSaveAuthor()); //歌手
            songInfo.setSongImagePath(saveUtils.getSaveAuthorImage()); //图片路径
            songInfo.setLrcPath(saveUtils.getSaveLrcPath()); //歌词路径
            if (songInfo.getSongPath() == null) { //如果保存的歌曲信息为空
                songInfo.setSongName(songs.get(index).getSongName()); //名称
                songInfo.setSongPath(songs.get(index).getSongPath());  //歌曲路径
                songInfo.setSongAuthor(songs.get(index).getSongAuthor()); //歌手
                songInfo.setSongImagePath(songs.get(index).getSongImagePath()); //图片路径
                songInfo.setLrcPath(songs.get(index).getLrcPath()); //歌词路径
            }
            setMusicData(songInfo.getSongPath(), index);
        } else {
            play();
        }
//        if (isPaused){
//            play();
//        }else {
//            index = saveUtils.getSaveIndex();
//            playMusic.setData(songs.get(index).getSongPath());
//            play();
//        }
    }

    //保存当前播放的歌曲到数据库表中
    private static void saveSongToDB() {
        if (songInfo != null) {
            songInfo.setLastPlayTime(System.currentTimeMillis());
        }
        JSApplication.getMusicDBTools().savePlaySong(songInfo);
    }

    //获取当前播放的歌曲信息，用于保存和显示
    private static void getSongInfo() {
        songName = songInfo.getSongName();
        author = songInfo.getSongAuthor();
        imagePath = songInfo.getSongImagePath();
        lrcPath = songInfo.getLrcPath();
        saveSongInfo();
        if (listener != null) { //回调方法，将当前播放的歌曲信息传递出去，显示在播放控制栏和播放页面
            listener.onMusicPlay(songName, author, imagePath, lrcPath, index);
        }
    }

    //保存当前播放的歌曲信息
    private static void saveSongInfo() {
        saveUtils.saveAuthor(author); //歌手
        saveUtils.saveAuthorImage(imagePath);  //图片地址
        saveUtils.saveSongName(songName);  //歌名
        saveUtils.saveSongPath(songInfo.getSongPath());  //歌曲路径
        saveUtils.saveLrcPath(lrcPath);  //歌词路径
        saveUtils.saveIndex(index);
    }

    //暂停
    public static void pause() {
        playMusic.pause();
        isPaused = true;
    }

    //是否在播放
    public static boolean isPlaying() {
        if (playMusic == null) {
            return false;
        } else {
            return playMusic.isPlaying();
        }
    }

    //播放上一首歌曲
    public static void playPre() {
        if (songs.size() == 0) return;
        if (index > 0 && index < songs.size()) {
            index -= 1;
        } else {
            index = songs.size() - 1;
        }
        songInfo = songs.get(index);
        setMusicData(songInfo.getSongPath(), index);
    }

    //播放下一首歌曲
    public static void playNext() {
        if (songs.size() == 0) return;
        if (index >= 0 && index < songs.size() - 1) {
            index += 1;
        } else {
            index = 0;
        }
        songInfo = songs.get(index);
        setMusicData(songInfo.getSongPath(), index);
    }

    public static void setPlaySpeed(float speed) {
        playMusic.setPlaySpeed(speed);
    }

    //获得歌曲总时间
    public static int getDuration() {
        return playMusic.getDuration();
    }

    //获得歌曲当前播放的时间
    public static int getCurrentDuration() {
        return playMusic.getCurrentPosition();
    }

    //更新播放进度，拖动播放页面的进度条和歌词时调用
    public static void seekTo(int msec) {
        playMusic.seekTo(msec);
    }

    public static MediaPlayer getMediaPlayer() {
        return playMusic.getMediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        playMusic = null;
        System.gc();
    }

    //歌曲播放的事件监听
    public static void setOnMusicPlayListener(OnMusicPlayListener listener1) {
        listener = listener1;
    }

    public interface OnMusicPlayListener {
        void onMusicPlay(String songName, String author, String imagePath, String lrcPath, int index);
    }
}
