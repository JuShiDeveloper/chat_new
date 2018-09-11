package com.jushi.muisc.chat.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 持久化数据存储类
 */
public class SaveUtils {
    private static Context mContext;
    private SharedPreferences sp;


    private static SaveUtils saveUtils;

    private SaveUtils() {
    }

    public static SaveUtils getInstance(Context context) {
        mContext = context;
        if (saveUtils == null) {
            saveUtils = new SaveUtils();
        }
        return saveUtils;
    }

    private void getSP(String spName) {
        try {
            if (sp == null) {
                sp = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*------------------------保存与歌曲相关的内容开始---------------------------------*/

    public void saveSongName(String songName) {
        getSP(Constant.SAVE_SONG_INFO);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_SONG_NAME_KEY, songName);
        editor.commit();
    }

    public void saveAuthor(String author) {
        getSP(Constant.SAVE_SONG_INFO);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_SONG_AUTHOR_KEY, author);
        editor.commit();
    }

    public String getSavedSongName() {
        getSP(Constant.SAVE_SONG_INFO);
        return sp.getString(Constant.SAVE_SONG_NAME_KEY, null);
    }

    public String getSaveAuthor() {
        getSP(Constant.SAVE_SONG_INFO);
        return sp.getString(Constant.SAVE_SONG_AUTHOR_KEY, null);
    }

    public void saveAuthorImage(String imagePath) {
        getSP(Constant.SAVE_SONG_INFO);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_AUTHOR_IMAGE_KEY, imagePath);
        editor.commit();
    }

    public String getSaveAuthorImage() {
        getSP(Constant.SAVE_SONG_INFO);
        return sp.getString(Constant.SAVE_AUTHOR_IMAGE_KEY, null);
    }

    public void saveIndex(int index) {
        getSP(Constant.SAVE_SONG_INFO);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constant.SAVE_SONG_INDEX_KEY, index);
        editor.commit();
    }

    public int getSaveIndex() {
        getSP(Constant.SAVE_SONG_INFO);
        return sp.getInt(Constant.SAVE_SONG_INDEX_KEY, 0);
    }

    public void saveSongPath(String songPath) {
        getSP(Constant.SAVE_SONG_INFO);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_SONG_PATH_KEY, songPath);
        editor.commit();
    }

    public String getSavedSongPath() {
        getSP(Constant.SAVE_SONG_INFO);
        return sp.getString(Constant.SAVE_SONG_PATH_KEY, null);
    }

    public void saveLrcPath(String lrcPath) {
        getSP(Constant.SAVE_SONG_INFO);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_LRC_PATH_KEY, lrcPath);
        editor.commit();
    }

    public String getSaveLrcPath() {
        getSP(Constant.SAVE_SONG_INFO);
        return sp.getString(Constant.SAVE_LRC_PATH_KEY, null);
    }

    /*----------------------保存与歌曲相关的内容结束-------------------------------------*/

    /*-----------------保存与用户相关的内容开始---------------------------*/

    public void saveUserImage(String imagePath) {
        getSP(Constant.SAVE_USER_INFO);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(Constant.SAVE_USER_IMAGE_KEY, imagePath);
        edit.commit();
    }

    public String getSaveUserImage() {
        getSP(Constant.SAVE_USER_INFO);
        return sp.getString(Constant.SAVE_USER_IMAGE_KEY, null);
    }
}
