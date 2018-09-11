package com.jushi.muisc.chat.music.common.dialog.download;

import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;

public interface MenuDialogChangedListener {
    void show();
    void hide();
    void onSong(Song song);
}
