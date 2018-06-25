package com.jushi.muisc.chat.music.jsinterface;

import com.jushi.muisc.chat.music.mv.model.MVBean;
import com.jushi.muisc.chat.music.mv.model.MVItemModel;
import com.jushi.muisc.chat.music.zhibo.model.ZhiBoModel;

import java.util.List;

/**
 * 直播/视频数据接口
 */

public interface OnLiveOrMvDataListener {
    //MV数据
    void onMvData(List<MVBean.ResultBean.MvListBean> mvListBeans);

    //直播数据
    void onLiveData(List<ZhiBoModel.DataBeanX.DataBean> dataBeans);

    //MV详细信息
    void onMvDetailInfo(MVItemModel itemModel);
}
