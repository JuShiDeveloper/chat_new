package com.jushi.muisc.chat.music.localmusic.model;

public class Song {
    private String songId;
    private String songName;
    private String songAuthor;
    private String songAlbum;
    private String songPath;
    private String songImagePath;
    private String songSize;
    private int songDuration;
    private String lrcPath;

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

    @Override
    public String toString() {
        return "Song{" +
                "songId='" + songId + '\'' +
                ", songName='" + songName + '\'' +
                ", songAuthor='" + songAuthor + '\'' +
                ", songAlbum='" + songAlbum + '\'' +
                ", songPath='" + songPath + '\'' +
                ", songImagePath='" + songImagePath + '\'' +
                ", songSize='" + songSize + '\'' +
                ", songDuration=" + songDuration +
                ", lrcPath='" + lrcPath + '\'' +
                '}';
    }
}
