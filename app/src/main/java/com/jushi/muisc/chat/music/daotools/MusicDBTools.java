package com.jushi.muisc.chat.music.daotools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jushi.muisc.chat.JSApplication;
import com.jushi.muisc.chat.dao.DaoMaster;
import com.jushi.muisc.chat.dao.DaoSession;
import com.jushi.muisc.chat.dao.SongDao;
import com.jushi.muisc.chat.music.localmusic.model.Song;
import com.jushi.muisc.chat.utils.LogUtils;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * 音乐数据库处理工具类
 */
public class MusicDBTools {
    private Context context;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private String dbName = "music";
    private DaoMaster daoMaster;
    private SongDao songDao;

    private static MusicDBTools musicDBTools;

    private MusicDBTools() {
        context = JSApplication.getContext();
        devOpenHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    public static MusicDBTools getInstance() {
        if (musicDBTools == null) {
            musicDBTools = new MusicDBTools();
        }
        return musicDBTools;
    }

    /**
     * 获取可读数据库
     *
     * @return
     */
    private SQLiteDatabase getReadableDatabase() {
        if (devOpenHelper == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        return devOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (devOpenHelper == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        return devOpenHelper.getWritableDatabase();
    }

    private DaoSession getDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

    /**
     * 保存播放的歌曲
     */
    public void savePlaySong(final Song song) {
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map(new Observable.Operator() {
                    @Override
                    public Object call(Object o) {
                        Song querySong = querySongInfoBySongEntity(song);
                        if (querySong == null) {
                            daoMaster = new DaoMaster(getWritableDatabase());
                            songDao = getDaoSession(daoMaster).getSongDao();
                            song.setPlayTimes(1);
                            long index = songDao.insert(song);
                            LogUtils.v("savePlaySong index = " + index);
                        } else {
                            updateLastPlayTime(querySong);
                        }
                        return null;
                    }
                }).subscribe();
    }

    /**
     * 更新最后一次播放时间
     * 同时播放次数加 1
     *
     * @param song
     */
    private void updateLastPlayTime(final Song song) {
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map(new Observable.Operator() {
                    @Override
                    public Object call(Object o) {
                        daoMaster = new DaoMaster(getWritableDatabase());
                        songDao = getDaoSession(daoMaster).getSongDao();
                        song.setPlayTimes(song.getPlayTimes() + 1);  //播放次数加 1
                        songDao.update(song);
                        return null;
                    }
                }).subscribe();
    }

    /**
     * 从表中获取所有添加进来的song
     *
     * @return
     */
    public List<Song> getAllSongByFromDB() {
        daoMaster = new DaoMaster(getWritableDatabase());
        songDao = getDaoSession(daoMaster).getSongDao();
        return songDao.queryBuilder().list();
    }

    /**
     * 查询单条数据
     *
     * @param song
     * @return
     */
    public Song querySongInfoBySongEntity(Song song) {
        Song songResult = null;
        daoMaster = new DaoMaster(getReadableDatabase());
        songDao = getDaoSession(daoMaster).getSongDao();
        Query query = songDao.queryBuilder().where(SongDao.Properties.SongName.eq(song.getSongName()),
                SongDao.Properties.SongAuthor.eq(song.getSongAuthor())).build();
        List<Song> songList = query.list();
        if (songList == null || songList.size() <= 0) return null;
        for (Song song2 : songList) {
            if (song.getSongName().equals(song2.getSongName()) && song.getSongAuthor().equals(song2.getSongAuthor())) {
                songResult = song2;
            }
        }
        return songResult;
    }
}
