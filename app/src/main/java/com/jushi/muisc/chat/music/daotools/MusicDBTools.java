package com.jushi.muisc.chat.music.daotools;

import android.content.Context;

import com.jushi.muisc.chat.app.JSApplication;
import com.jushi.muisc.chat.dao.DaoMaster;
import com.jushi.muisc.chat.dao.DaoSession;
import com.jushi.muisc.chat.dao.SongDao;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.common.utils.PATH;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 音乐数据库处理工具类
 */
public class MusicDBTools {
    private Context context;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private String dbName = "Music";
    private DaoMaster daoMaster;
    private SongDao songDao;
    private final String FAVORITES = "y"; //收藏
    private final String UN_FAVORITES = "n";  //未收藏
    private final String DOWNLOAD = FAVORITES; //是否是新下载的

    private static MusicDBTools musicDBTools;

    private MusicDBTools() {
        context = JSApplication.getContext();
        devOpenHelper = new DaoMaster.DevOpenHelper(context, dbName);
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
    private Database getReadableDatabase() {
        if (devOpenHelper == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        return devOpenHelper.getReadableDb();
    }

    /**
     * 获取可写数据库
     */
    private Database getWritableDatabase() {
        if (devOpenHelper == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        return devOpenHelper.getWritableDb();
    }

    private DaoSession getDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

    /**
     * 保存播放的歌曲
     */
    public void savePlaySong(final Song song) {
        Song querySong = querySongInfoBySongEntity(song);
        if (querySong == null) {
            daoMaster = new DaoMaster(getWritableDatabase());
            songDao = getDaoSession(daoMaster).getSongDao();
            song.setPlayTimes(1);
            long index = songDao.insert(song);
        } else {
            updateLastPlayTime(querySong);
        }
    }

    /**
     * 添加收藏
     */
    public void addFavoritesToDB(Song song) {
        Song querySong = querySongInfoBySongEntity(song);
        if (querySong == null) {
            daoMaster = new DaoMaster(getWritableDatabase());
            songDao = getDaoSession(daoMaster).getSongDao();
            song.setFavorites(FAVORITES);
            songDao.insert(song);
        } else {
            updateFavoritesStatus(querySong);
        }
    }

    /**
     * 更新收藏状态
     *
     * @param song
     */
    private void updateFavoritesStatus(Song song) {
        daoMaster = new DaoMaster(getWritableDatabase());
        songDao = getDaoSession(daoMaster).getSongDao();
        song.setFavorites(FAVORITES);
        songDao.update(song);
    }

    /**
     * 保存下载数据到DB
     *
     * @param song
     */
    public void addDownloadToDB(Song song) {
        Song querySong = querySongInfoBySongEntity(song);
        if (querySong == null) {
            daoMaster = new DaoMaster(getWritableDatabase());
            songDao = getDaoSession(daoMaster).getSongDao();
            song.setDownload(DOWNLOAD);
            songDao.insert(song);
        } else {
            querySong.setLrcPath(song.getLrcPath());
            querySong.setSongPath(song.getSongPath());
            updataDownloadStatus(querySong);
        }
    }

    /**
     * 更新是否是下载的状态
     *
     * @param song
     */
    private void updataDownloadStatus(Song song) {
        daoMaster = new DaoMaster(getWritableDatabase());
        songDao = getDaoSession(daoMaster).getSongDao();
        song.setDownload(DOWNLOAD);
        songDao.update(song);
    }

    /**
     * 查询所有下载的数据
     *
     * @return
     */
    public List<Song> getAllDownloadMusic() {
        daoMaster = new DaoMaster(getReadableDatabase());
        songDao = getDaoSession(daoMaster).getSongDao();
        Query query = songDao.queryBuilder().where(SongDao.Properties.Download.eq(DOWNLOAD)).build();
        return query.list();
    }

    /**
     * 删除下载的歌曲（删除数据库中的记录和本地文件）
     */
    public void deleteDownloadSong(final Song song) {
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        daoMaster = new DaoMaster(getWritableDatabase());
                        songDao = getDaoSession(daoMaster).getSongDao();
                        songDao.delete(song);
                        PATH.deleteFile(song.getLrcPath());
                        PATH.deleteFile(song.getSongPath());
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
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        daoMaster = new DaoMaster(getWritableDatabase());
                        songDao = getDaoSession(daoMaster).getSongDao();
                        song.setPlayTimes(song.getPlayTimes() + 1);  //播放次数加 1
                        songDao.update(song);
                        return null;
                    }
                }).subscribe();
    }

    /**
     * 从表中获取所有添加进来并且播放过的song
     *
     * @return
     */
    public List<Song> getAllPlaySong() {
        daoMaster = new DaoMaster(getReadableDatabase());
        songDao = getDaoSession(daoMaster).getSongDao();
        Query query = songDao.queryBuilder()
                .where(SongDao.Properties.PlayTimes.notEq(0))
                .orderDesc(SongDao.Properties.PlayTimes)
                .build();
        return query.list();
    }

    /**
     * 从表中查询所有收藏的数据
     *
     * @return
     */
    public List<Song> getAllFavoritesSong() {
        daoMaster = new DaoMaster(getReadableDatabase());
        songDao = getDaoSession(daoMaster).getSongDao();
        Query query = songDao.queryBuilder().where(SongDao.Properties.Favorites.eq(FAVORITES)).build();
        return query.list();
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
