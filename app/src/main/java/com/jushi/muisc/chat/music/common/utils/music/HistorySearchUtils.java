package com.jushi.muisc.chat.music.common.utils.music;

import android.content.Context;
import android.content.SharedPreferences;


import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存历史搜索
 */

public class HistorySearchUtils {
    private static SharedPreferences sp;
    private static Context mContext;
    private static HistorySearchUtils searchUtils;

    private HistorySearchUtils() {
    }

    public static HistorySearchUtils getInstance(Context context) {
        mContext = context;
        if (searchUtils == null) {
            searchUtils = new HistorySearchUtils();
        }
        sp = context.getSharedPreferences("KeyWords", Context.MODE_PRIVATE);
        return searchUtils;
    }

    /**
     * 保存搜索过的关键词
     *
     * @param list
     */
    public void saveKeyWords(final List<String> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (list == null || list.size() < 0) {
                        return;
                    }
                    if (list.size() > 5) {
                        for (int i = 5; i < list.size(); i++) {
                            list.remove(i);
                        }
                    }
                    String keyWords = JSONObject.toJSONString(list);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("keyWords", keyWords);
                    edit.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取搜索过的关键词
     *
     * @return
     */
    public List<String> getKeyWords() {
        List<String> keyWords = new ArrayList<>();
        try {
            String keyJson = sp.getString("keyWords", null);
            if (keyJson == null) {
                return keyWords;
            }
            keyWords = JSONObject.parseArray(keyJson, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyWords;
    }
}
