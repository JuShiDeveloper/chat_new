package com.jushi.muisc.chat.music.dialog.minterface;

import com.jushi.muisc.chat.music.localmusic.model.Song;

public interface MenuDialogChangedListener {
    void show();
    void hide();
    void onSong(Song song);
}
