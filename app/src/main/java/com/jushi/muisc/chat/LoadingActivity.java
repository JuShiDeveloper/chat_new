package com.jushi.muisc.chat;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.jushi.muisc.chat.utils.DisplayUtils;
import com.jushi.muisc.chat.utils.LocalMusicUtils;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(); //不设置View,在manifest中设置Theme,让欢迎页面能快速启动
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Intent intent = new Intent(this,MainActivity.class);
        LocalMusicUtils.getSongs(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },1500);
    }
}
