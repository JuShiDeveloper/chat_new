package com.jushi.muisc.chat.music.home_page.mv.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.utils.ToastUtils;
import com.jushi.muisc.chat.music.home_page.mv.AllMVActivity;
import com.jushi.muisc.chat.music.home_page.mv.adapter.MvDataAdapter;
import com.jushi.muisc.chat.music.home_page.common.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.common.manager.ActivityManager;
import com.jushi.muisc.chat.common.manager.JSGridLayoutManager;
import com.jushi.muisc.chat.music.home_page.mv.model.MVBean;
import com.jushi.muisc.chat.music.home_page.mv.common.MvItemInfoTaskService;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.music.common.utils.music.MusicDataUtils;
import com.jushi.muisc.chat.common.utils.NetWorkUtils;
import com.jushi.muisc.chat.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播/视频页面  最新MV
 */

public class LatestMVController implements View.OnClickListener {
    private final String SAVE_KEY = "mvListBeans";
    private Context mContext;
    private RadioButton moreBtn;
    private RecyclerView recyclerView;
    private Handler handler;
    private NetWorkService workService;
    private List<MVBean.ResultBean.MvListBean> mvListBeans;
    private MvDataAdapter mvDataAdapter;
    private boolean isRefresh = false;

    public LatestMVController(Context mContext) {
        this.mContext = mContext;
        handler = new Handler();
        workService = NetWorkService.getInstance(mContext);
    }

    public void initView(View rootView) {
        moreBtn = rootView.findViewById(R.id.latest_mv_moreButton);
        moreBtn.setOnClickListener(this);
        recyclerView = rootView.findViewById(R.id.latest_mv_recyclerView);
        JSGridLayoutManager manager = new JSGridLayoutManager(mContext, 2);
        manager.setScrollEnable(false);
        recyclerView.setLayoutManager(manager);
        Utils.setLatestMvRecyclerViewParams((Activity) mContext, recyclerView);

        getMvData();
    }

    private void getMvData() {
        if (NetWorkUtils.isNetworkAvailable(mContext)) {
            new MVDataTask().run();
        } else {
            if (!isRefresh) {
                mvListBeans = (List<MVBean.ResultBean.MvListBean>) MusicDataUtils.getInstance(mContext).getSaveData(SAVE_KEY, MVBean.ResultBean.MvListBean.class);
                showMvData();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.latest_mv_moreButton:
                ActivityManager.startActivity(mContext, AllMVActivity.class);
                break;
        }
    }

    class MVDataTask extends Thread {
        @Override
        public void run() {
            workService.getLatestMVData(4, new LiveAndMvDataAdapter() {
                @Override
                public void onMvData(List<MVBean.ResultBean.MvListBean> listBeans) {
                    mvListBeans = listBeans;
                    showMvData();
                    MusicDataUtils.getInstance(mContext).saveData(SAVE_KEY, mvListBeans);
                }
            });
        }
    }

    private void showMvData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mvDataAdapter = new MvDataAdapter(mContext, mvListBeans);
                recyclerView.setAdapter(mvDataAdapter);
                setItemClickListener();
            }
        });
    }

    private void setItemClickListener() {
        mvDataAdapter.setOnItemClickListener(new MvDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MVBean.ResultBean.MvListBean mvListBean, int position) {
                String mvId = mvListBean.getMv_id();
                new MvItemInfoTaskService(mContext, mvId, workService, requestListener).run();
            }
        });
    }

    public void refreshData() {
        isRefresh = true;
        if (mvListBeans == null) {
            mvListBeans = new ArrayList<>();
        }
        mvListBeans.clear();
        getMvData();
    }

    private MvItemInfoTaskService.MVDetailRequestListener requestListener = new MvItemInfoTaskService.MVDetailRequestListener() {
        @Override
        public void onRequestDetailFiled() {
            ToastUtils.show(mContext, "当前MV视频暂时无法播放！");
        }
    };
}
