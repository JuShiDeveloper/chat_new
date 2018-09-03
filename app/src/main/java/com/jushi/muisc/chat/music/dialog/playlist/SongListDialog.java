package com.jushi.muisc.chat.music.dialog.playlist;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.jushi.muisc.chat.JSApplication;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.sliding_menu.localmusic.adapter.LocalMusicAdapter;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.play.play_music.PlayMusicService;
import com.jushi.muisc.chat.music.utils.SaveUtils;
import com.jushi.muisc.chat.utils.ShadowUtils;
import com.jushi.muisc.chat.view.JSTextView;

import java.util.List;

/**
 * 显示歌曲列表的 dialog（点击播放控制栏最右边的按钮）
 */
public class SongListDialog {
    private static BottomSheetDialog dialog;
    private static SongListBuilder songListBuilder;
    private static JSTextView tvSongListTips;
    private static RecyclerView recyclerView;
    private static LinearLayout tipsLayout;
    private static List<Song> songs;

    public static SongListBuilder create(Context context) {
        dialog = new BottomSheetDialog(context);
        View view = View.inflate(context, R.layout.song_list_dialog_layout, null);
        dialog.setContentView(view);
        tvSongListTips = view.findViewById(R.id.tv_song_list_tips);
        tipsLayout = view.findViewById(R.id.song_list_tips_layout);
        ShadowUtils.setShadowDown_2(context, tipsLayout);
        recyclerView = view.findViewById(R.id.song_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        songListBuilder = new SongListBuilder();
        return songListBuilder;
    }


    public static class SongListBuilder {

        private LocalMusicAdapter musicAdapter;

        public SongListBuilder() {
            songs = PlayMusicService.getSongs();
            initialize();
        }

        private void initialize() {
            initAdapter();
            setItemClickListener();
            initStatus();
        }

        private void initStatus() {
            String num = new StringBuilder()
                    .append(JSApplication.getContext().getString(R.string.total_))
                    .append(songs.size())
                    .append(JSApplication.getContext().getString(R.string.shou_)).toString();
            tvSongListTips.setText(num);
            if (PlayMusicService.isPlaying()) {
                SaveUtils saveUtils = SaveUtils.getInstance(JSApplication.getContext());
                String name = saveUtils.getSavedSongName();
                String path = saveUtils.getSavedSongPath();
                for (int i = 0;i < songs.size();i++) {
                    if (name.equals(songs.get(i).getSongName()) && path.equals(songs.get(i).getSongPath())) {
                        musicAdapter.setStateChange(i);
                    }
                }
            }
        }

        private void initAdapter() {
            musicAdapter = new LocalMusicAdapter(JSApplication.getContext(), songs);
            recyclerView.setAdapter(musicAdapter);
        }

        private void setItemClickListener() {
            musicAdapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Song song, int position) {
                    PlayMusicService.playOneMusic(song, position);
                    musicAdapter.setStateChange(position);
                }
            });
        }

        public void show() {
            dialog.show();
        }
    }
}
