package com.jushi.muisc.chat.sliding_menu;

import android.content.Context;

import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.utils.SaveUtils;

/**
 * 比较当前播放的是否在列表中
 */
public class ComparisonUtils {

    public static boolean isEquals(Context context, Song song) {
        String name = SaveUtils.getInstance(context).getSavedSongName();
        String path = SaveUtils.getInstance(context).getSavedSongPath();
        if (name.equals(song.getSongName()) && path.equals(song.getSongPath())) {
            return true;
        } else {
            return false;
        }
    }
}
