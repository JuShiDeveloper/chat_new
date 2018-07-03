package com.jushi.muisc.chat.music.near_play.controller;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jushi.muisc.chat.JSApplication;
import com.jushi.muisc.chat.music.daotools.MusicDBTools;
import com.jushi.muisc.chat.music.localmusic.adapter.LocalMusicAdapter;
import com.jushi.muisc.chat.music.localmusic.model.Song;
import com.jushi.muisc.chat.music.near_play.minterface.INearController;
import com.jushi.muisc.chat.music.near_play.minterface.INearPlayView;
import com.jushi.muisc.chat.utils.LogUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class NearPlayController implements INearController {

    private Context context;
    private INearPlayView iNearPlayView;
    private LocalMusicAdapter musicAdapter;
    private List<Song> songs;

    public NearPlayController() {
        context = JSApplication.getContext();
    }

    public void initNearPlayData() {
        Observable.just(MusicDBTools.getInstance().getAllSongByFromDB())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<Song>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Song> songs) {
                        iNearPlayView.onMusicNumber(songs.size());
                        NearPlayController.this.songs = songs;
                        
                    }
                });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };


    @Override
    public void onNearPlayView(INearPlayView iNearPlayView) {
        this.iNearPlayView = iNearPlayView;
    }

    @Override
    public void onPlayAllBtnClick() {

    }
}
