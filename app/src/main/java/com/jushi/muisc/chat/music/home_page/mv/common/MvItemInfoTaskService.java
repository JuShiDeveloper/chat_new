package com.jushi.muisc.chat.music.home_page.mv.common;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.music.home_page.common.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.common.manager.ActivityManager;
import com.jushi.muisc.chat.music.home_page.mv.model.MVItemModel;

/**
 * 获取MV详细信息
 */

public class MvItemInfoTaskService extends Thread {
    private Context context;
    private String mvId;
    private NetWorkService workService;
    private Handler handler;
    private MVDetailRequestListener requestListener;

    public MvItemInfoTaskService(Context context, String mvId, NetWorkService workService, MVDetailRequestListener requestListener) {
        this.context = context;
        this.mvId = mvId;
        this.workService = workService;
        this.requestListener = requestListener;
        handler = new Handler();
    }

    @Override
    public void run() {
        workService.getMvDetailInfo(mvId, new LiveAndMvDataAdapter() {
            @Override
            public void onMvDetailInfo(MVItemModel itemModel) {
                String playUrl = itemModel.getResult().getFiles().get_$31().getFile_link();
                String title = itemModel.getResult().getMv_info().getTitle();
                if (TextUtils.isEmpty(playUrl)) {
                    requestFiled();
                    return;
                }
                ActivityManager.startPlayVideoActivity(context, playUrl, title, null);
            }

            @Override
            public void onError() {
                requestFiled();
            }
        });
    }

    private void requestFiled() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                requestListener.onRequestDetailFiled();
            }
        });
    }

    public interface MVDetailRequestListener{
        void onRequestDetailFiled();
    }
}
