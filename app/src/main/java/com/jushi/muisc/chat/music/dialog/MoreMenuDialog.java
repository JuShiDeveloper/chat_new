package com.jushi.muisc.chat.music.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.dialog.minterface.MenuDialogChangedListener;
import com.jushi.muisc.chat.music.localmusic.model.Song;
import com.jushi.muisc.chat.utils.DisplayUtils;
import com.jushi.muisc.chat.view.JSTextView;

public class MoreMenuDialog implements MenuDialogChangedListener, View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private View rootView;
    private Song song;
    private JSTextView tvSongName;
    private LinearLayout favoritesBtn, downloadBtn, shareBtn;

    public MoreMenuDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.BottomDialog);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_favorite_btn:
                hide();
                break;
            case R.id.menu_download_btn:
                hide();
                break;
            case R.id.menu_share_btn:
                break;
        }
    }
}
