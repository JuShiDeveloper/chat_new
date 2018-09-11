package com.jushi.muisc.chat.common.utils;

import com.jushi.muisc.chat.music.chart.model.ChartDataModel;

/**
 * 常用字段
 */
public class Constant {

    public static final String newMusicChart = "新歌榜";
    public static final String hotMusicChart = "热歌榜";
    public static final String ouMeiChart = "欧美金曲榜";
    public static final String chinaMusicChart = "华语金曲榜";
    public static final String oldMusicChart = "经典老歌榜";
    public static final String netWorkChart = "网络歌曲榜";
    public static final String yingShiMusicChart = "影视金曲榜";
    public static final String qingGeMusicChart = "情歌对唱榜";
    public static final String yaoGunChart = "摇滚榜";

    public static ChartDataModel.ContentBeanX contentBeanX;
    public static final String TYPE_LIVE = "zhibo";
    public static final String TYPE_MV = "mv";

    /*----------保存歌曲信息-------------*/
    public static final String SAVE_SONG_INFO = "SongInfo";
    public static final String SAVE_SONG_NAME_KEY = "songName";
    public static final String SAVE_SONG_AUTHOR_KEY = "author";
    public static final String SAVE_AUTHOR_IMAGE_KEY = "authorImage";
    public static final String SAVE_SONG_INDEX_KEY = "index";
    public static final String SAVE_SONG_PATH_KEY = "songPath";
    public static final String SAVE_LRC_PATH_KEY = "lrcPath";

    /*-----------保存用户信息--------------------*/
    public static final String SAVE_USER_INFO = "user_info";
    public static final String SAVE_USER_IMAGE_KEY = "user_image";
    public static final String SAVE_AUTO_LOGIN = "auto_login"; //设置是否自动登陆

}
