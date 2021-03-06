package com.jushi.muisc.chat.sliding_menu.my_favorites;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jushi.muisc.chat.app.JSApplication;
import com.jushi.muisc.chat.music.common.daotools.MusicDBTools;
import com.jushi.muisc.chat.sliding_menu.localmusic.adapter.LocalMusicAdapter;
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

/**
 * 我的收藏控制类
 */
public class FavoritesController implements INearController {

    private INearPlayView iNearPlayView;
    private Context context;
    private PlayController playController;
    private LocalMusicAdapter musicAdapter;
    private List<Song> songs;

    public FavoritesController() {
        this.context = JSApplication.getContext();
        initialize();
    }

    private void initialize() {
        initData();
    }

    private void initData() {
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        Message msg = handler.obtainMessage();
                        msg.obj = MusicDBTools.getInstance().getAllFavoritesSong();
                        handler.sendMessage(msg);
                        return null;
                    }
                }).subscribe();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initMusicAdapter(msg);
            initMusicNum();
            setItemClickListener();
        }
    };

    private void initMusicNum() {
        iNearPlayView.onMusicNumber(songs.size());
    }

    private void initMusicAdapter(Message msg) {
        songs = (List<Song>) msg.obj;
        musicAdapter = new LocalMusicAdapter(context, songs);
        iNearPlayView.onAdapter(musicAdapter);
        if (PlayMusicService.isPlaying()){
            for (int i = 0; i < songs.size(); i++) {
                if (ComparisonUtils.isEquals(context,songs.get(i))) {
                    musicAdapter.setStateChange(i);
                }
            }
        }
    }

    private void setItemClickListener() {
        musicAdapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
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
            ToastUtils.show(context, "暂无收藏的歌曲");
        }
    }

    @Override
    public void onPlayController(PlayController playController) {
        this.playController = playController;
    }
}
