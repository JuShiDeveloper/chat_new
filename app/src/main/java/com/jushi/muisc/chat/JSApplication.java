package com.jushi.muisc.chat;

import android.app.Application;
import android.content.Context;

import com.jushi.muisc.chat.music.daotools.MusicDBTools;
import com.jushi.muisc.chat.utils.PATH;
import com.umeng.commonsdk.UMConfigure;

public class JSApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        PATH.initPath();
        //未在配置文件里设置app key
        UMConfigure.init(this, getString(R.string.Umen_app_key),
                getString(R.string.Umeng_Channel),
                UMConfigure.DEVICE_TYPE_PHONE, null);
        //已经在配置文件中设置app key
//        UMConfigure.init(Context context, int deviceType, String pushSecret);
    }

    public static Context getContext() {
        return context;
    }

    public static MusicDBTools getMusicDBTools() {
        return MusicDBTools.getInstance();
    }
}
