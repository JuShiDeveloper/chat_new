package com.jushi.muisc.chat.music.jsinterface;

import com.jushi.muisc.chat.music.mv.model.MVBean;
import com.jushi.muisc.chat.music.mv.model.MVItemModel;
import com.jushi.muisc.chat.music.zhibo.model.ZhiBoModel;

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
