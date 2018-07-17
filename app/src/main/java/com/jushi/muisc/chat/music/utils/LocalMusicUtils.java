package com.jushi.muisc.chat.music.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LocalMusicUtils {
    private static final int SONG_ID = 0;
    private static final int DISPLAY_NAME = 1;
    private static final int SONG_NAME = 2;
    private static final int SONG_DURATION = 3;
    private static final int SONG_ARTIST = 4;
    private static final int SONG_ALBUM = 5;
    private static final int SONG_PATH = 6;
    private static final int SONG_SIZE = 7;
    private static final int SONG_ALBUM_ID = 8;
    private static final int SONG_NORMAL_SIZE = 1000 * 800;
    private static List<Song> songList = new ArrayList<>();

    public static List<Song> getSongs(Context context) {
        if (songList.size() > 0) {
            return songList;
        } else {
            return getLocalMusic(context);
        }
    }

    private static List<Song> getLocalMusic(Context context) {
        songList.clear();
        try {
            Cursor cursor = context.getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            new String[]{MediaStore.Audio.Media._ID,
                                    MediaStore.Audio.Media.DISPLAY_NAME,
                                    MediaStore.Audio.Media.TITLE,
                                    MediaStore.Audio.Media.DURATION,
                                    MediaStore.Audio.Media.ARTIST,
                                    MediaStore.Audio.Media.ALBUM,
                                    MediaStore.Audio.Media.DATA,
                                    MediaStore.Audio.Media.SIZE,
                                    MediaStore.Audio.Media.ALBUM_ID},
                            MediaStore.Audio.Media.MIME_TYPE + "= ? or "
                                    + MediaStore.Audio.Media.MIME_TYPE + "= ?",
                            new String[]{"audio/mpeg", "audio/x-ms-wma"}, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int songSize = cursor.getInt(SONG_SIZE);
                    if (songSize >= SONG_NORMAL_SIZE) {
                        String songPath = cursor.getString(SONG_PATH);
                        if (isFileExist(songPath)) {
                            String songName = cursor.getString(SONG_NAME);
                            String songAuthor = cursor.getString(SONG_ARTIST);
                            String songId = cursor.getString(SONG_ID);
                            String songAlbum = cursor.getString(SONG_ALBUM);
                            String songAlbumId = cursor.getString(SONG_ALBUM_ID);
                            int songDuration = cursor.getInt(SONG_DURATION);
                            Song song = new Song();
                            song.setSongId(songId);
                            song.setSongName(songName);
                            song.setSongPath(songPath);
                            song.setSongAlbum(songAlbum);
                            song.setSongAuthor(songAuthor);
                            song.setSongSize(getSongSize(songSize));
                            song.setSongDuration(songDuration);
                            song.setSongImagePath(getSongImagePath(context, songAlbumId));
                            song.setLrcPath(getLrcPath(songName, songPath));
                            songList.add(song);
                        }
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songList;
    }

    private static String getLrcPath(String songName, String songPath) {
        ///storage/emulated/0/netease/cloudmusic/Music/银临 Aki阿杰 - 牵丝戏.mp3
        String fileDir = songPath.substring(0, songPath.lastIndexOf('/') + 1);
        String lrcPath = fileDir + songName + ".lrc";
        if (isFileExist(lrcPath)){
            return lrcPath;
        }else {
            return null;
        }
    }

    private static String getSongImagePath(Context context, String songAlbumId) {
        String[] albumArt = {MediaStore.Audio.Albums.ALBUM_ART};
        Uri uri = Uri.parse("content://media" + MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI.getPath() + "/" + songAlbumId);
        String imagePath = null;
        Cursor cursor = context.getContentResolver().query(uri, albumArt, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
            cursor.moveToNext();
            imagePath = cursor.getString(0);
        }
        cursor.close();
        return imagePath;
    }

    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return (file.exists()) ? true : false;
    }

    public static String getSongSize(int songSize) {
        float s = songSize / 1024f / 1024f;
        String size = s + "";
        return size.substring(0, 4) + "M";
    }
}
