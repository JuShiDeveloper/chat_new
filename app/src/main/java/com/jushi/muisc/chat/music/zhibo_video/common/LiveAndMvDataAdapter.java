package com.jushi.muisc.chat.music.zhibo_video.common;

import com.jushi.muisc.chat.music.zhibo_video.mv.model.MVBean;
import com.jushi.muisc.chat.music.zhibo_video.mv.model.MVItemModel;
import com.jushi.muisc.chat.music.zhibo_video.zhibo.model.ZhiBoModel;

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
}
