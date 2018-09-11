package com.jushi.muisc.chat.music.dialog.download;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.daotools.MusicDBTools;
import com.jushi.muisc.chat.music.jsinterface.DownloadListener;
import com.jushi.muisc.chat.music.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.public_model.SongDetail;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.music.utils.music.LocalMusicUtils;
import com.jushi.muisc.chat.common.utils.LogUtils;
import com.jushi.muisc.chat.common.utils.PATH;
import com.jushi.muisc.chat.common.utils.ToastUtils;
import com.jushi.muisc.chat.common.view.JSTextView;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 点击更多按钮（三个点的按钮）弹出的dialog
 */
public class MoreMenuDialog implements MenuDialogChangedListener, View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private View rootView;
    private Song song;
    private JSTextView tvSongName;
    private LinearLayout favoritesBtn, downloadBtn, shareBtn;
    private NetWorkService netWorkService;

    public MoreMenuDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.BottomDialog);
        netWorkService = NetWorkService.getInstance(context);

        initialize();
    }

    private void initialize() {
        initView();
        initDialog();
        findWidget();
    }

    private void initView() {
        try {
            rootView = View.inflate(context, R.layout.layout_menu_click_more_btn, null);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                rootView = LayoutInflater.from(context).inflate(R.layout.layout_menu_click_more_btn, null);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void initDialog() {
//        dialog.getWindow().setWindowAnimations(R.style.Menu_Dialog_Style);
        dialog.setContentView(rootView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);

    }

    private void findWidget() {
        tvSongName = rootView.findViewById(R.id.menu_show_songName);
        tvSongName.setSelected(true);
        favoritesBtn = rootView.findViewById(R.id.menu_favorite_btn);
        downloadBtn = rootView.findViewById(R.id.menu_download_btn);
        shareBtn = rootView.findViewById(R.id.menu_share_btn);

        favoritesBtn.setOnClickListener(this);
        downloadBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
    }

    @Override
    public void onSong(Song song) {
        this.song = song;
    }

    @Override
    public void show() {
        showSongName();
        dialog.show();
    }

    private void showSongName() {
        tvSongName.setText(song.getSongName());
    }

    @Override
    public void hide() {
        dialog.dismiss();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String text = (String) msg.obj;
            ToastUtils.show(context, text);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_favorite_btn:
                addFavoritesToDB();
                hide();
                break;
            case R.id.menu_download_btn:
                downloadMusic();
                hide();
                break;
            case R.id.menu_share_btn:
                break;
        }
    }

    /**
     * download
     */
    private void downloadMusic() {
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        netWorkService.getSongInfo(song.getSongId(), new MusicDataAdapter() {
                            @Override
                            public void onSongDetail(SongDetail detail) {
                                song.setSongImagePath(detail.getSonginfo().getArtist_list().get(0).getAvatar_s300());
                                String fileLink = detail.getBitrate().getFile_link();
                                if (TextUtils.isEmpty(fileLink))
                                    fileLink = detail.getBitrate().getShow_link();
                                if (TextUtils.isEmpty(fileLink)) return;
                                startDownloadMusic(fileLink);
                                String lrcLink = detail.getSonginfo().getLrclink();
                                startDownloadLrc(lrcLink);
                            }
                        });
                        return null;
                    }
                }).subscribe();
    }

    /**
     * 下载歌词
     *
     * @param lrcLink
     */
    private void startDownloadLrc(String lrcLink) {
        netWorkService.downloadMusic(lrcLink, song.getSongName() + ".lrc", new DownloadListener() {
            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadSuccess() {

            }

            @Override
            public void onDownloadFailed() {

            }
        });
    }

    /**
     * 开始下载歌曲
     *
     * @param fileLink
     */
    private void startDownloadMusic(String fileLink) {
        netWorkService.downloadMusic(fileLink, song.getSongName() + ".mp3", new DownloadListener() {
            @Override
            public void onDownloading(int progress) {
                LogUtils.v("progress = " + progress);
            }

            @Override
            public void onDownloadSuccess() {
                sendMessage("下载成功！");

                song.setSongPath(PATH.downloadMusicDir() + song.getSongName() + ".mp3");
                song.setLrcPath(PATH.downloadMusicDir() + song.getSongName() + ".lrc");
                MusicDBTools.getInstance().addDownloadToDB(song);
            }

            @Override
            public void onDownloadFailed() {
                sendMessage("下载失败！");
            }
        });
    }

    /**
     * 添加收藏到数据库
     */
    private void addFavoritesToDB() {
        Observable.just("")
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(final String s) {
                        netWorkService.getSongInfo(song.getSongId(), new MusicDataAdapter() {
                            @Override
                            public void onSongDetail(SongDetail detail) {
                                song.setSongPath(detail.getBitrate().getShow_link());
                                song.setSongDuration(detail.getBitrate().getFile_duration());
                                song.setLrcPath(detail.getSonginfo().getLrclink());
                                song.setSongAlbum(detail.getSonginfo().getAlbum_title());
                                song.setSongImagePath(detail.getSonginfo().getArtist_list().get(0).getAvatar_s300());
                                song.setSongSize(LocalMusicUtils.getSongSize(detail.getBitrate().getFile_size()));

                                MusicDBTools.getInstance().addFavoritesToDB(song);

                                sendMessage("收藏成功，请到我的收藏页查看！");
                            }
                        });
                        return null;
                    }
                }).subscribe();
    }

    private void sendMessage(String msgs){
        Message msg = handler.obtainMessage();
        msg.obj = msgs;
        handler.sendMessage(msg);
    }

}
