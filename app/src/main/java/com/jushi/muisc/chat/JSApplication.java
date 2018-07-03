package com.jushi.muisc.chat;

import android.app.Application;
import android.content.Context;

import com.jushi.muisc.chat.music.daotools.MusicDBTools;

public class JSApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }

    public static MusicDBTools getMusicDBTools(){
        return MusicDBTools.getInstance();
    }
}
