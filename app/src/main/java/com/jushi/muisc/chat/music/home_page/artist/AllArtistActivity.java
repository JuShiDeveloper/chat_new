package com.jushi.muisc.chat.music.home_page.artist;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.home_page.artist.adapter.AllArtistAdapter;
import com.jushi.muisc.chat.music.common.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.common.manager.ActivityManager;
import com.jushi.muisc.chat.music.home_page.artist.model.ArtistsModel;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.music.common.utils.music.DataUrlUtils;
import com.jushi.muisc.chat.common.utils.DisplayUtils;
import com.jushi.muisc.chat.common.utils.ShadowUtils;
import com.jushi.muisc.chat.common.view.JSTextView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有歌手页面
 */
public class AllArtistActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout itemLayout;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    //顶部的选择按钮(热门、华语、韩国、日本、其他)
    private JSTextView tvHot, tvChina, tvOuMei, tvHanGuo, tvJapan, tvOther;
    private ArtistItem itemClick = ArtistItem.HOT;
    private String artistUrl;
    private NetWorkService workService;
    private Handler handler;
    private List<ArtistsModel.ArtistBean> artistBeans = new ArrayList<>();
    private AllArtistAdapter allArtistAdapter;

    public enum ArtistItem {
        HOT, CHINA, OUMEI, HANGUO, JAPAN, OTHER
    }

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
        setContentView(R.layout.activity_all_artist);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        artistUrl = DataUrlUtils.getHotArtistsUrl(60);
        handler = new Handler();
        workService = NetWorkService.getInstance(this);
        initView();
    }

    private void initView() {
        findWidget();
        setClickListener();
        recyclerViewSetAdapter();
        loadArtistData();
    }

    private void findWidget() {
        itemLayout = findViewById(R.id.all_artist_item_layout);
        ShadowUtils.setShadowDown_2(this, itemLayout);
        toolbar = findViewById(R.id.all_artist_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.all_artist_activity_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvHot = findViewById(R.id.hot_singer);
        tvChina = findViewById(R.id.china_singer);
        tvOuMei = findViewById(R.id.ouMei_singer);
        tvHanGuo = findViewById(R.id.hanGuo_singer);
        tvJapan = findViewById(R.id.riBen_singer);
        tvOther = findViewById(R.id.other_singer);

        progressBar = findViewById(R.id.all_artist_activity_load_image);
    }


    private void setClickListener() {
        tvHot.setOnClickListener(this);
        tvChina.setOnClickListener(this);
        tvOuMei.setOnClickListener(this);
        tvHanGuo.setOnClickListener(this);
        tvJapan.setOnClickListener(this);
        tvOther.setOnClickListener(this);
    }

    private void recyclerViewSetAdapter() {
        allArtistAdapter = new AllArtistAdapter(this, artistBeans);
        recyclerView.setAdapter(allArtistAdapter);
        setItemClickListener();
    }

    private void setItemClickListener() {
        allArtistAdapter.setOnItemClickListener(new AllArtistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArtistsModel.ArtistBean artistBean, int position) {
                ActivityManager.startArtistMusicActivity(AllArtistActivity.this,
                        artistBean.getTing_uid(), artistBean.getName());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hot_singer:
                itemClick = ArtistItem.HOT;
                artistUrl = DataUrlUtils.getHotArtistsUrl(60);
                break;
            case R.id.china_singer:
                itemClick = ArtistItem.CHINA;
                artistUrl = DataUrlUtils.getChinaArtistUrl();
                break;
            case R.id.ouMei_singer:
                itemClick = ArtistItem.OUMEI;
                artistUrl = DataUrlUtils.getOuMeiArtistUrl();
                break;
            case R.id.hanGuo_singer:
                itemClick = ArtistItem.HANGUO;
                artistUrl = DataUrlUtils.getHanGuoArtistUrl();
                break;
            case R.id.riBen_singer:
                itemClick = ArtistItem.JAPAN;
                artistUrl = DataUrlUtils.getJapanArtistUrl();
                break;
            case R.id.other_singer:
                itemClick = ArtistItem.OTHER;
                artistUrl = DataUrlUtils.getOtherArtistUrl();
                break;
        }
        setTextColorChanged(itemClick);
        loadArtistData();
    }

    private void loadArtistData() {
        artistBeans.clear();
        notifyDataChanged();
        switch (itemClick) {
            case HOT:
                if (DataCache.hotList.size() > 0){
                    artistBeans.addAll(DataCache.hotList);
                    return;
                }
                break;
            case CHINA:
                if (DataCache.huayuList.size() > 0){
                    artistBeans.addAll(DataCache.huayuList);
                    return;
                }
                break;
            case OUMEI:
                if (DataCache.oumeiList.size() > 0){
                    artistBeans.addAll(DataCache.oumeiList);
                    return;
                }
                break;
            case HANGUO:
                if (DataCache.hanguoList.size() > 0){
                    artistBeans.addAll(DataCache.hanguoList);
                    return;
                }
                break;
            case JAPAN:
                if (DataCache.japanList.size() > 0){
                    artistBeans.addAll(DataCache.japanList);
                    return;
                }
                break;
            case OTHER:
                if (DataCache.otherList.size() > 0){
                    artistBeans.addAll(DataCache.otherList);
                    return;
                }
                break;
        }
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        new ArtistDataTask().run();
    }

    class ArtistDataTask extends Thread {
        @Override
        public void run() {
            workService.getArtistData(artistUrl, new MusicDataAdapter() {
                @Override
                public void onArtistData(List<ArtistsModel.ArtistBean> beans) {
                    artistBeans.addAll(beans);
                    notifyDataChanged();
                    cacheData();
                }
            });
        }
    }

    private void cacheData() {
        switch (itemClick) {
            case HOT:
                DataCache.hotList.addAll(artistBeans);
                break;
            case CHINA:
                DataCache.huayuList.addAll(artistBeans);
                break;
            case OUMEI:
                DataCache.oumeiList.addAll(artistBeans);
                break;
            case HANGUO:
                DataCache.hanguoList.addAll(artistBeans);
                break;
            case JAPAN:
                DataCache.japanList.addAll(artistBeans);
                break;
            case OTHER:
                DataCache.otherList.addAll(artistBeans);
                break;
        }
    }

    private void notifyDataChanged() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                allArtistAdapter.notifyDataSetChanged();
                if (recyclerView.getVisibility() == View.INVISIBLE) {
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setTextColorChanged(ArtistItem itemClick) {
        switch (itemClick) {
            case HOT:
                tvHot.setTextColor(getResources().getColor(R.color.e80b0b));
                tvChina.setTextColor(getResources().getColor(R.color._333333));
                tvOuMei.setTextColor(getResources().getColor(R.color._333333));
                tvHanGuo.setTextColor(getResources().getColor(R.color._333333));
                tvJapan.setTextColor(getResources().getColor(R.color._333333));
                tvOther.setTextColor(getResources().getColor(R.color._333333));
                break;
            case CHINA:
                tvHot.setTextColor(getResources().getColor(R.color._333333));
                tvChina.setTextColor(getResources().getColor(R.color.e80b0b));
                tvOuMei.setTextColor(getResources().getColor(R.color._333333));
                tvHanGuo.setTextColor(getResources().getColor(R.color._333333));
                tvJapan.setTextColor(getResources().getColor(R.color._333333));
                tvOther.setTextColor(getResources().getColor(R.color._333333));
                break;
            case OUMEI:
                tvHot.setTextColor(getResources().getColor(R.color._333333));
                tvChina.setTextColor(getResources().getColor(R.color._333333));
                tvOuMei.setTextColor(getResources().getColor(R.color.e80b0b));
                tvHanGuo.setTextColor(getResources().getColor(R.color._333333));
                tvJapan.setTextColor(getResources().getColor(R.color._333333));
                tvOther.setTextColor(getResources().getColor(R.color._333333));
                break;
            case HANGUO:
                tvHot.setTextColor(getResources().getColor(R.color._333333));
                tvChina.setTextColor(getResources().getColor(R.color._333333));
                tvOuMei.setTextColor(getResources().getColor(R.color._333333));
                tvHanGuo.setTextColor(getResources().getColor(R.color.e80b0b));
                tvJapan.setTextColor(getResources().getColor(R.color._333333));
                tvOther.setTextColor(getResources().getColor(R.color._333333));
                break;
            case JAPAN:
                tvHot.setTextColor(getResources().getColor(R.color._333333));
                tvChina.setTextColor(getResources().getColor(R.color._333333));
                tvOuMei.setTextColor(getResources().getColor(R.color._333333));
                tvHanGuo.setTextColor(getResources().getColor(R.color._333333));
                tvJapan.setTextColor(getResources().getColor(R.color.e80b0b));
                tvOther.setTextColor(getResources().getColor(R.color._333333));
                break;
            case OTHER:
                tvHot.setTextColor(getResources().getColor(R.color._333333));
                tvChina.setTextColor(getResources().getColor(R.color._333333));
                tvOuMei.setTextColor(getResources().getColor(R.color._333333));
                tvHanGuo.setTextColor(getResources().getColor(R.color._333333));
                tvJapan.setTextColor(getResources().getColor(R.color._333333));
                tvOther.setTextColor(getResources().getColor(R.color.e80b0b));
                break;
        }
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

    static class DataCache {
        static List<ArtistsModel.ArtistBean> hotList = new ArrayList<>();
        static List<ArtistsModel.ArtistBean> huayuList = new ArrayList<>();
        static List<ArtistsModel.ArtistBean> oumeiList = new ArrayList<>();
        static List<ArtistsModel.ArtistBean> hanguoList = new ArrayList<>();
        static List<ArtistsModel.ArtistBean> japanList = new ArrayList<>();
        static List<ArtistsModel.ArtistBean> otherList = new ArrayList<>();
    }
}
