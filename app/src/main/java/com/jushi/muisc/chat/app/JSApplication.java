package com.jushi.muisc.chat.app;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.common.daotools.MusicDBTools;
import com.jushi.muisc.chat.common.utils.PATH;
import com.jushi.rxPermissions.RxPermissions;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;

import rx.functions.Action1;

public class JSApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        PATH.initPath();
        initHuanXin();
        initUmeng();
        initLog();
        CrashReport.initCrashReport(this, "b43e9da2e0", true);
    }

    private void initLog(){
        com.blankj.utilcode.util.Utils.init(this);
        RxPermissions.getInstance(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean it) {
                if (it){
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        CrashUtils.init(new CrashUtils.OnCrashListener() {
                            @Override
                            public void onCrash(String crashInfo, Throwable e) {
                                LogUtils.d("onCrash() called with: crashInfo = [$crashInfo], e = [$e]");
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 初始化环信即时通讯
     */
    private void initHuanXin() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
//        options.setAutoTransferMessageAttachments(true);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
//        options.setAutoDownloadThumbnail(true);
//        自动登陆
        options.setAutoLogin(true);
//初始化
        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
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
