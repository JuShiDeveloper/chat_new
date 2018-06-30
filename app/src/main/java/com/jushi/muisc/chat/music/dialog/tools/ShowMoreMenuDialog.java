package com.jushi.muisc.chat.music.dialog.tools;

import android.content.Context;

import com.jushi.muisc.chat.music.dialog.MoreMenuDialog;
import com.jushi.muisc.chat.music.localmusic.model.Song;

public class ShowMoreMenuDialog {

    private static MoreMenuDialog menuDialog;

    public static void showMenuDialog(Context context, Song song) {
        menuDialog = new MoreMenuDialog(context);
        menuDialog.onSong(song);
        menuDialog.show();
    }
}
