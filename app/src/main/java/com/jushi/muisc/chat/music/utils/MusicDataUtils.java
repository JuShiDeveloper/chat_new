package com.jushi.muisc.chat.music.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 数据缓存工具类，在没网络情况下获取上一次缓存的数据
 */
public class MusicDataUtils {
    private static SharedPreferences sp;
    private static Context mContext;
    private static MusicDataUtils musicDataUtils;

    private MusicDataUtils() {
    }

    public static MusicDataUtils getInstance(Context context) {
        mContext = context;
        if (musicDataUtils == null) {
            musicDataUtils = new MusicDataUtils();
        }
        return musicDataUtils;
    }

    public void saveData(final String key, final List<?> objects) {
        sp = mContext.getSharedPreferences(key, Context.MODE_PRIVATE);
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        String jsonData = JSONObject.toJSONString(objects);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(key, jsonData);
                        editor.commit();
                        return null;
                    }
                }).subscribe();
    }

    public List<?> getSaveData(String key,Class<?> cls){
        sp = mContext.getSharedPreferences(key, Context.MODE_PRIVATE);
        List<?> dataList = new ArrayList<>();
        String jsonData = sp.getString(key,null);
        if (jsonData == null){
            return dataList;
        }
        dataList = JSONObject.parseArray(jsonData,cls);
        return dataList;
    }
}
