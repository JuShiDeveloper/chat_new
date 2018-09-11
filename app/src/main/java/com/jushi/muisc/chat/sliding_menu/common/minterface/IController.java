package com.jushi.muisc.chat.sliding_menu.common.minterface;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public interface IController {
    //侧滑菜单中圆形头像和点击登陆文字
    void headerView(ImageView headerImage, TextView loadingTv, RelativeLayout imageLayout);
    //本地音乐
    void localMusic();
    //最近播放
    void nearPlay();
    //我的收藏
    void myFavorites();
    //下载管理
    void downloadManager();
}
