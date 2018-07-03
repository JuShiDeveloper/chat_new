package com.jushi.muisc.chat.music.near_play;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.daotools.MusicDBTools;
import com.jushi.muisc.chat.music.localmusic.model.Song;
import com.jushi.muisc.chat.utils.LogUtils;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

public class NearPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_play);
        LogUtils.v("thread :"+Thread.currentThread().getName());
        Observable.just(null)
                .subscribeOn(Schedulers.newThread())
                .map(new Observable.Operator() {
                    @Override
                    public Object call(Object o) {
                        LogUtils.v("thread :"+Thread.currentThread().getName());
                        List<Song> songs = MusicDBTools.getInstance().getAllSongByFromDB();
                        LogUtils.v("all songs size = " + songs.size());
                        for (Song song : songs) {
                            LogUtils.v("song to string: " + song.toString());
                        }
                        return null;
                    }
                }).subscribe();
    }
}
