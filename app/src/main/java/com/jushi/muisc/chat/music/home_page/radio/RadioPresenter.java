package com.jushi.muisc.chat.music.home_page.radio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.utils.ToastUtils;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.music.common.utils.music.DataUrlUtils;
import com.jushi.muisc.chat.music.home_page.common.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.music.home_page.radio.common.CustomGridLayoutManager;
import com.jushi.muisc.chat.music.home_page.radio.common.RadioAdapter;
import com.jushi.muisc.chat.music.home_page.radio.entity.RadioListEntity;
import com.jushi.muisc.chat.music.home_page.radio.more.RadioMoreActivity;
import com.jushi.muisc.chat.music.home_page.radio.songList.RadioSongListActivity;

import java.util.List;

public class RadioPresenter extends LiveAndMvDataAdapter implements RadioAdapter.OnItemClickListener {
    private Context context;
    private NetWorkService netWorkService;
    private View rootView;
    private RelativeLayout titleLayout;
    private LinearLayout linearLayout;
    private TextView tvPtitle, tvMtitle;
    private TextView tvPmoreBtn;
    private RecyclerView rvPublicChannel, rvMusicChannel;
    private List<RadioListEntity.ResultBean> results;
    private RadioAdapter pRadioAdapter;
    private RadioAdapter mRadioAdapter;

    public RadioPresenter(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
        netWorkService = NetWorkService.getInstance(context);
        initView();
        initRecyclerView(rvPublicChannel);
        initRecyclerView(rvMusicChannel);
        setPublicChannelMoreBtnClick();
    }

    private void initView() {
        linearLayout = rootView.findViewById(R.id.radio_total_layout);
        titleLayout = rootView.findViewById(R.id.radio_title_layout);
        tvPtitle = rootView.findViewById(R.id.radio_item_pTitle);
        rvPublicChannel = rootView.findViewById(R.id.radio_item_rv_public_channel);
        tvMtitle = rootView.findViewById(R.id.radio_item_mTitle);
        rvMusicChannel = rootView.findViewById(R.id.radio_item_rv_music_channel);
        tvPmoreBtn = rootView.findViewById(R.id.radio_public_channel_moreButton);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                outRect.set(15, 10, 15, 10);
            }
        });
        recyclerView.setLayoutManager(new CustomGridLayoutManager(context, 3));
    }

    private void setPublicChannelMoreBtnClick() {
        tvPmoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (results == null) return;
                String json = JSONObject.toJSONString(results);
                Intent intent = new Intent(context, RadioMoreActivity.class);
                intent.putExtra(RadioMoreActivity.INTENT_KEY, json);
                context.startActivity(intent);
            }
        });
    }

    /**
     * 加载电台列表数据
     */
    public void loadRadioListData() {
        linearLayout.setVisibility(View.GONE);
        new DataTask().run();
    }

    class DataTask implements Runnable {
        @Override
        public void run() {
            netWorkService.getRadioListData(RadioPresenter.this);
        }
    }

    @Override
    public void onRadioListData(RadioListEntity entity) {
        results = entity.getResult();
        final String title = results.get(0).getTitle();
        final String mTitle = results.get(1).getTitle();
        rootView.post(new Runnable() {
            @Override
            public void run() {
                tvPtitle.setText(title);
                pRadioAdapter = new RadioAdapter(context, results.get(0).getChannellist(), title, 6);
                rvPublicChannel.setAdapter(pRadioAdapter);
                pRadioAdapter.setOnItemClickListener(RadioPresenter.this);
                tvMtitle.setText(mTitle);
                mRadioAdapter = new RadioAdapter(context, results.get(1).getChannellist(), mTitle, 6);
                rvMusicChannel.setAdapter(mRadioAdapter);
                mRadioAdapter.setOnItemClickListener(RadioPresenter.this);
            }
        });
    }

    @Override
    public void onError() {
        linearLayout.post(new Runnable() {
            @Override
            public void run() {
                linearLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClick(RadioListEntity.ResultBean.ChannellistBean channellistBean, int position, String type) {
        Intent intent = new Intent(context, RadioSongListActivity.class);
        String url = "";
        if (type.equals(RadioAdapter.PUBLIC_CHANNEL)) { //公共频道
            url = DataUrlUtils.getSongListFormOncePublicChannelRadio(channellistBean.getCh_name());
        }
        if (type.equals(RadioAdapter.MUSIC_CHANNEL)) { //音乐人频道
            ToastUtils.show(context, "正在开发中");
            return;
        }
        intent.putExtra(RadioSongListActivity.URL_KEY, url);
        intent.putExtra(RadioSongListActivity.TYPE_KET, type);
        intent.putExtra(RadioSongListActivity.TITLE_KET, channellistBean.getName());
        context.startActivity(intent);
    }


}
