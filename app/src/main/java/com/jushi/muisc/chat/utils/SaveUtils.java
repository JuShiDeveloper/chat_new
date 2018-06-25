package com.jushi.muisc.chat.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveUtils {
    private static Context mContext;
    private SharedPreferences sp;


    private static SaveUtils saveUtils;
    private SaveUtils(){}
    public static SaveUtils getInstance(Context context){
        mContext = context;
        if (saveUtils == null) {
            saveUtils = new SaveUtils();
        }
        return saveUtils;
    }

    public void saveSongName(String songName){
        getSP();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_SONG_NAME_KEY,songName);
        editor.commit();
    }

    public void saveAuthor(String author){
        getSP();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_SONG_AUTHOR_KEY,author);
        editor.commit();
    }

    public String getSavedSongName(){
        getSP();
        return sp.getString(Constant.SAVE_SONG_NAME_KEY,null);
    }

    public String getSaveAuthor(){
        getSP();
        return sp.getString(Constant.SAVE_SONG_AUTHOR_KEY,null);
    }

    public void saveAuthorImage(String imagePath){
        getSP();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_AUTHOR_IMAGE_KEY,imagePath);
        editor.commit();
    }

    public String getSaveAuthorImage(){
        getSP();
        return sp.getString(Constant.SAVE_AUTHOR_IMAGE_KEY,null);
    }

    public void saveIndex(int index){
        getSP();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constant.SAVE_SONG_INDEX_KEY,index);
        editor.commit();
    }

    public int getSaveIndex(){
        getSP();
        return sp.getInt(Constant.SAVE_SONG_INDEX_KEY,0);
    }

    public void saveSongPath(String songPath){
        getSP();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_SONG_PATH_KEY,songPath);
        editor.commit();
    }

    public String getSavedSongPath(){
        getSP();
        return sp.getString(Constant.SAVE_SONG_PATH_KEY,null);
    }

    public void saveLrcPath(String lrcPath){
        getSP();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.SAVE_LRC_PATH_KEY,lrcPath);
        editor.commit();
    }

    public String getSaveLrcPath(){
        getSP();
        return sp.getString(Constant.SAVE_LRC_PATH_KEY,null);
    }

    private void getSP() {
        if (sp == null){
            sp = mContext.getSharedPreferences(Constant.SAVE_SONG_INFO,Context.MODE_PRIVATE);
        }
    }
}
