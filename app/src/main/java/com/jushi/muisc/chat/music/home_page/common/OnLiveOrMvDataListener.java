package com.jushi.muisc.chat.music.home_page.common;

import com.jushi.muisc.chat.music.home_page.mv.model.MVBean;
import com.jushi.muisc.chat.music.home_page.mv.model.MVItemModel;
import com.jushi.muisc.chat.music.home_page.radio.entity.RadioListEntity;
import com.jushi.muisc.chat.music.home_page.radio.entity.RadioSongListEntity;
import com.jushi.muisc.chat.music.home_page.zhibo.model.ZhiBoModel;

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

    //电台列表数据
    void onRadioListData(RadioListEntity entity);

    //某个电台的歌曲列表数据
    void onRadioSongListData(RadioSongListEntity entity);

    void onError();
}
