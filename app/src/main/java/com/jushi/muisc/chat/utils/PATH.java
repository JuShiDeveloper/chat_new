package com.jushi.muisc.chat.utils;

import android.os.Environment;

import java.io.File;

public class PATH {
    //总文件夹
    private static final String TOTAL_PATH = "JuShiMusic";
    //歌曲的下载文件夹
    private static final String DOWNLOAD_PATH = "/music/";

    public static void initPath() {
        createDir(downloadMusicDir());
    }

    private static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private static String sdCardDir() {
        String sdCardDir = Environment.getExternalStorageDirectory().toString() + "/";
        return sdCardDir;
    }

    public static String downloadMusicDir() {
        return sdCardDir() + TOTAL_PATH + DOWNLOAD_PATH;
    }


}
