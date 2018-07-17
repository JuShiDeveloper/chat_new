package com.jushi.muisc.chat.sliding_menu.localmusic.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Song {
    @Id
    private Long id; //作为主键
    private String songId;
    private String songName;
    private String songAuthor;
    private String songAlbum;
    private String songPath;
    private String songImagePath;
    private String songSize;
    private int songDuration;
    private String lrcPath;
    //最后一次播放时间
    private long lastPlayTime;
    //播放次数
    private int playTimes;
    //是否收藏（y收藏 n未收藏）
    private String favorites;
    //是否是新下载的(y是，n否)
    private String download;

    @Generated(hash = 1559814666)
    public Song(Long id, String songId, String songName, String songAuthor,
            String songAlbum, String songPath, String songImagePath,
            String songSize, int songDuration, String lrcPath, long lastPlayTime,
            int playTimes, String favorites, String download) {
        this.id = id;
        this.songId = songId;
        this.songName = songName;
        this.songAuthor = songAuthor;
        this.songAlbum = songAlbum;
        this.songPath = songPath;
        this.songImagePath = songImagePath;
        this.songSize = songSize;
        this.songDuration = songDuration;
        this.lrcPath = lrcPath;
        this.lastPlayTime = lastPlayTime;
        this.playTimes = playTimes;
        this.favorites = favorites;
        this.download = download;
    }

    @Generated(hash = 87031450)
    public Song() {
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongAuthor() {
        return songAuthor;
    }

    public void setSongAuthor(String songAuthor) {
        this.songAuthor = songAuthor;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getSongImagePath() {
        return songImagePath;
    }

    public void setSongImagePath(String songImagePath) {
        this.songImagePath = songImagePath;
    }

    public String getSongSize() {
        return songSize;
    }

    public void setSongSize(String songSize) {
        this.songSize = songSize;
    }

    public int getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }

    public String getLrcPath() {
        return lrcPath;
    }

    public void setLrcPath(String lrcPath) {
        this.lrcPath = lrcPath;
    }


    public long getLastPlayTime() {
        return this.lastPlayTime;
    }

    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public int getPlayTimes() {
        return this.playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public String getFavorites() {
        return this.favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", songId='" + songId + '\'' +
                ", songName='" + songName + '\'' +
                ", songAuthor='" + songAuthor + '\'' +
                ", songAlbum='" + songAlbum + '\'' +
                ", songPath='" + songPath + '\'' +
                ", songImagePath='" + songImagePath + '\'' +
                ", songSize='" + songSize + '\'' +
                ", songDuration=" + songDuration +
                ", lrcPath='" + lrcPath + '\'' +
                ", lastPlayTime=" + lastPlayTime +
                ", playTimes=" + playTimes +
                ", favorites='" + favorites + '\'' +
                ", download='" + download + '\'' +
                '}';
    }
}
