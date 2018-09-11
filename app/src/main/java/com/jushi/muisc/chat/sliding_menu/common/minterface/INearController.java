package com.jushi.muisc.chat.sliding_menu.common.minterface;

import com.jushi.muisc.chat.music.play.play_navgation.PlayController;

public interface INearController {
    void onNearPlayView(INearPlayView iNearPlayView);
    //播放全部按钮被点击
    void onPlayAllBtnClick();
    //播放控制栏
    void onPlayController(PlayController playController);
}
