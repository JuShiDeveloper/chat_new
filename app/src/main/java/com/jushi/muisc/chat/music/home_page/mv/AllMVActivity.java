package com.jushi.muisc.chat.music.home_page.mv;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.utils.RefreshViewUtils;
import com.jushi.muisc.chat.common.utils.SystemBarUtil;
import com.jushi.muisc.chat.common.utils.ToastUtils;
import com.jushi.muisc.chat.music.home_page.mv.adapter.MvDataAdapter;
import com.jushi.muisc.chat.music.home_page.common.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.music.home_page.mv.model.MVBean;
import com.jushi.muisc.chat.music.home_page.mv.common.MvItemInfoTaskService;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.common.utils.DisplayUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * 所有的MV页面
 */
public class AllMVActivity extends AppCompatActivity implements MvItemInfoTaskService.MVDetailRequestListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private View statusBar;
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
//        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        SystemBarUtil.setRootViewFitsSystemWindows(this, false);
        SystemBarUtil.setTranslucentStatus(this);
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
        statusBar = findViewById(R.id.activity_all_mv_status_bar);
        statusBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtils.getStatusBarHeight(this)));
        toolbar = findViewById(R.id.all_mv_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        recyclerView = findViewById(R.id.all_mv_activity_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                outRect.set(15, 10, 15, 10);
            }
        });
    }

    private void getAllMvData() {
        RefreshViewUtils.showRefreshDialog(this);
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
                RefreshViewUtils.dismissRefreshDialog();
            }
        });
    }

    private void setItemClick() {
        mvDataAdapter.setOnItemClickListener(new MvDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MVBean.ResultBean.MvListBean mvListBean, int position) {
                String mvId = mvListBean.getMv_id();
                new MvItemInfoTaskService(AllMVActivity.this, mvId, workService, AllMVActivity.this).run();
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

    @Override
    public void onRequestDetailFiled() {
        ToastUtils.show(this, "当前MV视频暂时无法播放！");
    }
}
