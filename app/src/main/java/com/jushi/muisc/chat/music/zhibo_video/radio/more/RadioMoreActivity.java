package com.jushi.muisc.chat.music.zhibo_video.radio.more;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.common.utils.music.DataUrlUtils;
import com.jushi.muisc.chat.music.zhibo_video.radio.common.RadioAdapter;
import com.jushi.muisc.chat.music.zhibo_video.radio.entity.RadioListEntity;
import com.jushi.muisc.chat.music.zhibo_video.radio.songList.RadioSongListActivity;

import java.util.List;

/**
 * 电台公共频道所有数据页面
 */
public class RadioMoreActivity extends AppCompatActivity implements View.OnClickListener, RadioAdapter.OnItemClickListener {
    public static final String INTENT_KEY = "punlic_channel";
    private List<RadioListEntity.ResultBean> results;
    private ImageView backBtn;
    private RecyclerView recyclerView;
    private RadioAdapter radioAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_more);
        initialize();
    }

    private void initialize() {
        getData();
        initView();
        showData();
    }

    private void getData() {
        results = JSONObject.parseArray(getIntent().getStringExtra(INTENT_KEY), RadioListEntity.ResultBean.class);
    }

    private void initView() {
        backBtn = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.rv_radio_more);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                outRect.set(15, 15, 15, 10);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        backBtn.setOnClickListener(this);
    }

    private void showData() {
        radioAdapter = new RadioAdapter(this, results.get(0).getChannellist(), results.get(0).getTitle(), results.get(0).getChannellist().size());
        recyclerView.setAdapter(radioAdapter);
        radioAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(RadioListEntity.ResultBean.ChannellistBean channellistBean, int position, String type) {
        Intent intent = new Intent(this, RadioSongListActivity.class);
        String url = "";
        if (type.equals(RadioAdapter.PUBLIC_CHANNEL)) { //公共频道
            url = DataUrlUtils.getSongListFormOncePublicChannelRadio(channellistBean.getCh_name());
        }
        if (type.equals(RadioAdapter.MUSIC_CHANNEL)) { //音乐人频道
            url = channellistBean.getArtistid();
        }
        intent.putExtra(RadioSongListActivity.URL_KEY, url);
        intent.putExtra(RadioSongListActivity.TYPE_KET, type);
        intent.putExtra(RadioSongListActivity.TITLE_KET, channellistBean.getName());
        startActivity(intent);
    }
}
