package com.jushi.muisc.chat.music.zhibo_video.zhibo.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.zhibo_video.zhibo.adapter.LiveDataAdapter;
import com.jushi.muisc.chat.music.zhibo_video.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.manager.ActivityManager;
import com.jushi.muisc.chat.music.zhibo_video.zhibo.model.ZhiBoModel;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.music.utils.Constant;
import com.jushi.muisc.chat.music.utils.music.DataUrlUtils;
import com.jushi.muisc.chat.utils.DisplayUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * 显示所有的直播数据页面
 */
public class AllZhiBoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Handler handler;
    private NetWorkService workService;
    private List<ZhiBoModel.DataBeanX.DataBean> liveBeans;
    private LiveDataAdapter liveDataAdapter;


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_zhi_bo);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        handler = new Handler();
        workService = NetWorkService.getInstance(this);

        initView();
    }

    private void initView() {
        findWidget();
        getZhiBoData();
    }

    private void findWidget() {
        toolbar = findViewById(R.id.all_zhibo_activity_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerView = findViewById(R.id.all_zhibo_activity_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getZhiBoData() {
        new ZhiBoDataTask().run();
    }

    class ZhiBoDataTask extends Thread {
        @Override
        public void run() {
            workService.getLiveData(130, new LiveAndMvDataAdapter() {
                @Override
                public void onLiveData(List<ZhiBoModel.DataBeanX.DataBean> dataBeans) {
                    liveBeans = dataBeans;
                    showZhiBoData();
                }
            });
        }
    }

    private void showZhiBoData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                liveDataAdapter = new LiveDataAdapter(AllZhiBoActivity.this, liveBeans);
                recyclerView.setAdapter(liveDataAdapter);
                setItemClick();
            }
        });
    }

    private void setItemClick() {
        liveDataAdapter.setOnItemClickListener(new LiveDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ZhiBoModel.DataBeanX.DataBean dataBean, int position) {
                String playUrl = DataUrlUtils.getPlayZhiBoUrl(dataBean.getRid());
                ActivityManager.startPlayVideoActivity(AllZhiBoActivity.this, playUrl, dataBean.getNickname(), Constant.TYPE_LIVE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
