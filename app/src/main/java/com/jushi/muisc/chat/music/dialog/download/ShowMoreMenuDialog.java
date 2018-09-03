package com.jushi.muisc.chat.music.dialog.download;

import android.content.Context;

import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;

/**
 * 显示搜藏  下载   分享 三个选项的dialog
 */
public class ShowMoreMenuDialog {

    private static MoreMenuDialog menuDialog;

    public static void showMenuDialog(Context context, Song song) {
        menuDialog = new MoreMenuDialog(context);
        menuDialog.onSong(song);
        menuDialog.show();
    }
}
