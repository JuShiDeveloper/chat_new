package com.jushi.muisc.chat.music.home_page.common;

import com.jushi.muisc.chat.music.home_page.mv.model.MVBean;
import com.jushi.muisc.chat.music.home_page.mv.model.MVItemModel;
import com.jushi.muisc.chat.music.home_page.radio.entity.RadioListEntity;
import com.jushi.muisc.chat.music.home_page.radio.entity.RadioSongListEntity;
import com.jushi.muisc.chat.music.home_page.zhibo.model.ZhiBoModel;

import java.util.List;

/**
 * 直播、视频数据适配器
 */

public abstract class LiveAndMvDataAdapter implements OnLiveOrMvDataListener {
    @Override
    public void onMvData(List<MVBean.ResultBean.MvListBean> mvListBeans) {

    }

    @Override
    public void onLiveData(List<ZhiBoModel.DataBeanX.DataBean> dataBeans) {

    }

    @Override
    public void onMvDetailInfo(MVItemModel itemModel) {

    }

    @Override
    public void onRadioListData(RadioListEntity entity) {

    }

    @Override
    public void onRadioSongListData(RadioSongListEntity entity) {

    }

    @Override
    public void onError() {

    }
}
