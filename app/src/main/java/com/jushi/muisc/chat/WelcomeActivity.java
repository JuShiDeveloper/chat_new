package com.jushi.muisc.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jushi.muisc.chat.main.MainActivity;
import com.jushi.muisc.chat.music.common.utils.music.LocalMusicUtils;
import com.jushi.muisc.chat.common.utils.PATH;
import com.umeng.analytics.MobclickAgent;

/**
 * 应用启动时的欢迎界面
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(); //不设置View,在manifest中设置Theme,让欢迎页面能快速启动
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && Intent.ACTION_VIEW.equals(action)) {
                Uri uri = intent.getData();
                String audioPath = Uri.decode(uri.getEncodedPath());
                Log.v("==yufei==", "audioPath = " + audioPath);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Intent intent = new Intent(this, MainActivity.class);
        LocalMusicUtils.getSongs(this);
        requestPermission();
        PATH.initPath();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1500);
        MobclickAgent.onResume(this);
    }

    //SD卡读写权限请求
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
