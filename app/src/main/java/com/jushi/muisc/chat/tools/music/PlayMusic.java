package com.jushi.muisc.chat.tools.music;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.jushi.muisc.chat.music.service.PlayMusicService;


/**
 * 播放音乐
 */
public class PlayMusic implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener
        , MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer;

    public PlayMusic() {
        //调用本类中初始化MediaPlayer的方法
        initMediaPlayer();
    }

    /**
     * 初始化MediaPlayer
     */
    private void initMediaPlayer() {

        //获得MediaPlayer对象
        mediaPlayer = new MediaPlayer();
        //设置播放流
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //设置一首歌准备好的监听
        mediaPlayer.setOnPreparedListener(this);
        //设置播放出错监听
        mediaPlayer.setOnErrorListener(this);
        //设置一首歌播放完成监听
        mediaPlayer.setOnCompletionListener(this);
    }

    /**
     * 设置音乐的播放路径
     *
     * @param path 要播放的音乐的路径
     */
    public void setData(String path) {
        try {
            //重新设置
            mediaPlayer.reset();
            //设置要播放的音乐路径
            mediaPlayer.setDataSource(path);
            //准备
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放音乐
     */
    public void play() {
        //如果mediaPlayer不为null就开始播放音乐
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        //设置一首歌播放完成监听
        mediaPlayer.setOnCompletionListener(this);
    }

    /**
     * 暂停播放
     */
    public void pause() {
        //如果是播放状态，就暂停，否则就播放
        if (isPlaying()) {
            mediaPlayer.pause();
        } else {
            play();
        }
    }

    /**
     * 用于后台服务被启动时判断当前是否在播放歌曲
     *
     * @return
     */
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            boolean isPlaying = mediaPlayer.isPlaying();
            return isPlaying;
        }
        return false;
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public void seekTo(int msec){
        mediaPlayer.seekTo(msec);
    }

    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
    /**
     * 监听一首歌是否播放完
     *
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //一首歌播放完之后，自动播放下一首
        PlayMusicService.playNext();
    }

    /**
     * 监听一首歌是否准备完成
     *
     * @param mediaPlayer
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        play();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mediaPlayer.reset();
        return false;
    }

}
