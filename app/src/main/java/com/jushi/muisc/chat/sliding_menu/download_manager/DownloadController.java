package com.jushi.muisc.chat.sliding_menu.download_manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jushi.muisc.chat.music.common.daotools.MusicDBTools;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.play.play_navgation.PlayController;
import com.jushi.muisc.chat.music.play.play_music.PlayMusicService;
import com.jushi.muisc.chat.sliding_menu.common.ComparisonUtils;
import com.jushi.muisc.chat.sliding_menu.common.minterface.INearController;
import com.jushi.muisc.chat.sliding_menu.common.minterface.INearPlayView;
import com.jushi.muisc.chat.common.utils.ToastUtils;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DownloadController implements INearController {
    private Context context;
    private INearPlayView iNearPlayView;
    private DownloadAdapter musicAdapter;
    private List<Song> songs;
    private PlayController playController;

    public DownloadController(Context context) {
        this.context = context;
        initDownloadData();
    }

    private void initDownloadData() {
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        Message msg = handler.obtainMessage();
                        msg.obj = MusicDBTools.getInstance().getAllDownloadMusic();
                        handler.sendMessage(msg);
                        return null;
                    }
                }).subscribe();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initMusicAdapter(msg);
            initMusicNumber();
            setItemClickListener();
        }
    };

    private void initMusicAdapter(Message msg) {
        songs = (List<Song>) msg.obj;
        musicAdapter = new DownloadAdapter(context, songs);
        iNearPlayView.onAdapter(musicAdapter);
        if (PlayMusicService.isPlaying()){
            for (int i = 0; i < songs.size(); i++) {
                if (ComparisonUtils.isEquals(context,songs.get(i))) {
                    musicAdapter.setStateChange(i);
                }
            }
        }
    }

    private void initMusicNumber() {
        iNearPlayView.onMusicNumber(songs.size());
    }

    private void setItemClickListener() {
        musicAdapter.setOnItemClickListener(new DownloadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Song song, int position) {
                musicAdapter.setStateChange(position);
                playController.setPlayList(songs);
                playController.playOneMusic(song, position);
            }
        });
    }

    @Override
    public void onNearPlayView(INearPlayView iNearPlayView) {
        this.iNearPlayView = iNearPlayView;
    }

    @Override
    public void onPlayAllBtnClick() {
        if (songs != null && songs.size() > 0) {
            playController.setPlayList(songs);
            playController.playAllMusic();
            musicAdapter.setStateChange(0);
        } else {
            ToastUtils.show(context, "暂无下载的歌曲");
        }
    }

    @Override
    public void onPlayController(PlayController playController) {
        this.playController = playController;
    }
}
