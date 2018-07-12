package com.jushi.muisc.chat.music.zhibo.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.utils.MusicDataUtils;
import com.jushi.muisc.chat.music.zhibo.ui.AllZhiBoActivity;
import com.jushi.muisc.chat.music.zhibo.adapter.LiveDataAdapter;
import com.jushi.muisc.chat.music.jsinterface.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.manager.JSGridLayoutManager;
import com.jushi.muisc.chat.music.zhibo.model.ZhiBoModel;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.music.utils.Constant;
import com.jushi.muisc.chat.music.utils.DataUrlUtils;
import com.jushi.muisc.chat.utils.NetWorkUtils;
import com.jushi.muisc.chat.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播/视频页面   直播
 */

public class LiveController implements View.OnClickListener {
    private final String SAVE_KEY = "liveBeans";
    private Context mContext;
    private RadioButton moreBtn;
    private RecyclerView recyclerView;
    private NetWorkService workService;
    private Handler handler;
    private List<ZhiBoModel.DataBeanX.DataBean> liveBeans;
    private LiveDataAdapter liveDataAdapter;
    private boolean isRefresh = false;


    public LiveController(Context mContext) {
        this.mContext = mContext;
        workService = NetWorkService.getInstance(mContext);
        handler = new Handler();
    }

    public void initView(View rootView) {
        moreBtn = rootView.findViewById(R.id.live_moreButton);
        moreBtn.setOnClickListener(this);
        recyclerView = rootView.findViewById(R.id.live_recyclerView);
        JSGridLayoutManager manager = new JSGridLayoutManager(mContext,2);
        manager.setScrollEnable(false);
        recyclerView.setLayoutManager(manager);
        Utils.setZhiBoRecyclerViewParams((Activity) mContext,recyclerView);

        loadLiveData();
    }

    private void loadLiveData() {
        if (NetWorkUtils.isNetworkAvailable(mContext)) {
            new LiveDataTask().run();
        }else {
            if (!isRefresh){
                liveBeans = (List<ZhiBoModel.DataBeanX.DataBean>) MusicDataUtils.getInstance(mContext).getSaveData(SAVE_KEY,ZhiBoModel.DataBeanX.DataBean.class);
                showLiveData();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.live_moreButton:
                ActivityManager.startActivity(mContext, AllZhiBoActivity.class);
                break;
        }
    }

    class LiveDataTask extends Thread{
        @Override
        public void run() {
            workService.getLiveData(4, new LiveAndMvDataAdapter() {
                @Override
                public void onLiveData(List<ZhiBoModel.DataBeanX.DataBean> dataBeans) {
                    liveBeans = dataBeans;
                    showLiveData();
                    MusicDataUtils.getInstance(mContext).saveData(SAVE_KEY,liveBeans);
                }
            });
        }
    }

    private void showLiveData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                liveDataAdapter = new LiveDataAdapter(mContext,liveBeans);
                recyclerView.setAdapter(liveDataAdapter);
                setItemClickListener();
            }
        });
    }

    private void setItemClickListener() {
        liveDataAdapter.setOnItemClickListener(new LiveDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ZhiBoModel.DataBeanX.DataBean dataBean, int position) {
                String playUrl = DataUrlUtils.getPlayZhiBoUrl(dataBean.getRid());
                ActivityManager.startPlayVideoActivity(mContext,playUrl,dataBean.getNickname(), Constant.TYPE_LIVE);
            }
        });
    }

    public void refreshData(){
        isRefresh = true;
        if (liveBeans == null){
            liveBeans = new ArrayList<>();
        }
        liveBeans.clear();
        loadLiveData();
    }
}
