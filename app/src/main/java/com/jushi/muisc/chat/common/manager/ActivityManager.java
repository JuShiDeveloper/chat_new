package com.jushi.muisc.chat.common.manager;

import android.content.Context;
import android.content.Intent;

import com.jushi.muisc.chat.music.home_page.artist.music.ArtistMusicActivity;
import com.jushi.muisc.chat.music.chart.ui.ChartDetailActivity;
import com.jushi.muisc.chat.music.play.play_music.PlayMusicActivity;
import com.jushi.muisc.chat.music.play.play_video.PlayVideoActivity;
import com.jushi.muisc.chat.settings.SettingsActivity;

/**
 * Created by paocai on 2018/5/5.
 */

public class ActivityManager {

    public static void startActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> cls, String str) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("linkUrl", str);
        context.startActivity(intent);
    }

    public static void startPlayVideoActivity(Context context, String linkUrl, String nickName, String type) {
        Intent intent = new Intent(context, PlayVideoActivity.class);
        intent.putExtra("videoLink", linkUrl);
        intent.putExtra("nickName", nickName);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    public static void startChartDetailActivity(Context context, String chartTitle, String link) {
        Intent intent = new Intent(context, ChartDetailActivity.class);
        intent.putExtra("chartTitle", chartTitle);
        intent.putExtra("chartLink", link);
        context.startActivity(intent);
    }

    public static void startArtistMusicActivity(Context context, String artist_ting_uid, String artistName) {
        Intent intent = new Intent(context, ArtistMusicActivity.class);
        intent.putExtra("artistId", artist_ting_uid);
        intent.putExtra("artistName", artistName);
        context.startActivity(intent);
    }

    public static void startPlayMusicActivity(Context context) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
//        intent.putExtra("artistId",artist_ting_uid);
//        intent.putExtra("artistName",artistName);
        context.startActivity(intent);
    }

    public static void startSettingsActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
}
