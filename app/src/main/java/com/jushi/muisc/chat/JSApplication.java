package com.jushi.muisc.chat;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;
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
        initHuanXin();
        initUmeng();
    }

    /**
     * 初始化环信即时通讯
     */
    private void initHuanXin() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
//        options.setAutoTransferMessageAttachments(true);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
//        options.setAutoDownloadThumbnail(true);
//        自动登陆
        options.setAutoLogin(true);
//初始化
        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        try {
            EMClient.getInstance().createAccount("musics", "123456");
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化友盟统计
     */
    private void initUmeng() {
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
