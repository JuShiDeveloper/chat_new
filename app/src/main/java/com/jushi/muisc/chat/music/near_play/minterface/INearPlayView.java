package com.jushi.muisc.chat.music.near_play.minterface;

import android.support.v7.widget.RecyclerView;

public interface INearPlayView {
    void onAdapter(RecyclerView.Adapter adapter);
    void onMusicNumber(int num);
}
