package com.jushi.muisc.chat.music.zhibo_video.radio;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.music.zhibo_video.common.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.music.zhibo_video.radio.common.CustomGridLayoutManager;
import com.jushi.muisc.chat.music.zhibo_video.radio.common.RadioAdapter;
import com.jushi.muisc.chat.music.zhibo_video.radio.model.RadioListEntity;

import java.util.List;

public class RadioPresenter extends LiveAndMvDataAdapter {
    private Context context;
    private NetWorkService netWorkService;
    private View rootView;
    private RelativeLayout titleLayout;
    private TextView tvPtitle, tvMtitle;
    private RecyclerView rvPublicChannel, rvMusicChannel;

    public RadioPresenter(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
        netWorkService = NetWorkService.getInstance(context);
        initView();
        initRecyclerView(rvPublicChannel);
        initRecyclerView(rvMusicChannel);
    }

    private void initView() {
        titleLayout = rootView.findViewById(R.id.radio_title_layout);
        tvPtitle = rootView.findViewById(R.id.radio_item_pTitle);
        rvPublicChannel = rootView.findViewById(R.id.radio_item_rv_public_channel);
        tvMtitle = rootView.findViewById(R.id.radio_item_mTitle);
        rvMusicChannel = rootView.findViewById(R.id.radio_item_rv_music_channel);
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

    /**
     * 加载电台列表数据
     */
    public void loadRadioListData() {
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
        final List<RadioListEntity.ResultBean> results = entity.getResult();
        final String title = results.get(0).getTitle();
        final String mTitle = results.get(1).getTitle();
        rootView.post(new Runnable() {
            @Override
            public void run() {
                tvPtitle.setText(title);
                rvPublicChannel.setAdapter(new RadioAdapter(context, results.get(0).getChannellist(), title));
                tvMtitle.setText(mTitle);
                rvMusicChannel.setAdapter(new RadioAdapter(context, results.get(1).getChannellist(), mTitle));
            }
        });
    }

    @Override
    public void onError() {

    }
}
