package com.jushi.muisc.chat.music.zhibo_video.common;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.zhibo_video.mv.controller.LatestMVController;
import com.jushi.muisc.chat.music.zhibo_video.zhibo.controller.LiveController;

/**
 * 直播/视频
 */
public class VideoAndLiveFragment extends Fragment {

    private View rootView;
    //最新MV
    private static LatestMVController mvController;
    //直播
    private static LiveController liveController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_music_video_and_live, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        //初始化最新MV
        mvController = new LatestMVController(getContext());
        mvController.initView(rootView);

        //初始化直播数据
        liveController = new LiveController(getContext());
        liveController.initView(rootView);
    }

    public static void refreshData(){
        mvController.refreshData();
        liveController.refreshData();
    }

}
