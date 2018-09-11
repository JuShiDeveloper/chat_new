package com.jushi.muisc.chat.music.service;

import android.content.Context;

import com.jushi.muisc.chat.music.zhibo_video.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.common.manager.ActivityManager;
import com.jushi.muisc.chat.music.zhibo_video.mv.model.MVItemModel;

/**
 * 获取MV详细信息
 */

public class MvItemInfoTaskService extends Thread{
    private Context context;
    private String mvId;
    private NetWorkService workService;

    public MvItemInfoTaskService(Context context,String mvId,NetWorkService workService){
        this.context = context;
        this.mvId = mvId;
        this.workService = workService;
    }

    @Override
    public void run() {
        workService.getMvDetailInfo(mvId, new LiveAndMvDataAdapter() {
            @Override
            public void onMvDetailInfo(MVItemModel itemModel) {
                String playUrl = itemModel.getResult().getFiles().get_$31().getFile_link();
                String title = itemModel.getResult().getMv_info().getTitle();
                ActivityManager.startPlayVideoActivity(context,playUrl,title,null);
            }
        });
    }
}
