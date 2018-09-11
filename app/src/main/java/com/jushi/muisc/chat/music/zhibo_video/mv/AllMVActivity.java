package com.jushi.muisc.chat.music.zhibo_video.mv;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.zhibo_video.mv.adapter.MvDataAdapter;
import com.jushi.muisc.chat.music.zhibo_video.common.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.music.zhibo_video.mv.model.MVBean;
import com.jushi.muisc.chat.music.common.service.MvItemInfoTaskService;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.common.utils.DisplayUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * 所有的MV页面
 */
public class AllMVActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Handler handler;
    private NetWorkService workService;
    private MvDataAdapter mvDataAdapter;
    private List<MVBean.ResultBean.MvListBean> mvListBeans;

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
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        setContentView(R.layout.activity_all_mv);
        handler = new Handler();
        workService = NetWorkService.getInstance(this);
        initView();
    }

    private void initView() {
        findWidget();
        getAllMvData();
    }

    private void findWidget() {
        toolbar = findViewById(R.id.all_mv_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerView = findViewById(R.id.all_mv_activity_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getAllMvData() {
        new AllMvDataTask().run();
    }

    class AllMvDataTask extends Thread {
        @Override
        public void run() {
            workService.getLatestMVData(100, new LiveAndMvDataAdapter() {
                @Override
                public void onMvData(List<MVBean.ResultBean.MvListBean> listBeans) {
                    mvListBeans = listBeans;
                    showMvData();
                }
            });
        }
    }

    private void showMvData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mvDataAdapter = new MvDataAdapter(AllMVActivity.this, mvListBeans);
                recyclerView.setAdapter(mvDataAdapter);
                setItemClick();
            }
        });
    }

    private void setItemClick() {
        mvDataAdapter.setOnItemClickListener(new MvDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MVBean.ResultBean.MvListBean mvListBean, int position) {
                String mvId = mvListBean.getMv_id();
                new MvItemInfoTaskService(AllMVActivity.this,mvId,workService).run();
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
