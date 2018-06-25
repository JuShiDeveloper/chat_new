package com.jushi.muisc.chat.music.jsinterface;

import com.jushi.muisc.chat.music.artist.model.ArtistMusic;
import com.jushi.muisc.chat.music.artist.model.ArtistsModel;
import com.jushi.muisc.chat.music.banner.model.BannerModel;
import com.jushi.muisc.chat.music.chart.model.ChartDataModel;
import com.jushi.muisc.chat.music.public_model.LatestMusicModel;
import com.jushi.muisc.chat.music.search.model.SearchDataModel;
import com.jushi.muisc.chat.music.recommend.model.TodayRecommendModel;
import com.jushi.muisc.chat.music.public_model.SongDetail;

import java.util.List;

/**
 * 歌曲数据接口
 */

public interface OnMusicDataListener {
    //轮播图中数据
    void onBannerData(List<BannerModel.DataBean.SliderBean> sliderBeans);
    //今日推荐数据
    void onTodayRecommendData(List<TodayRecommendModel.ResultBean.ListBean> listBeans);
    //歌手数据
    void onArtistData(List<ArtistsModel.ArtistBean> artistBeans);

    //搜索出来的歌曲数据
    void onSearchData(List<SearchDataModel.SongListBean> songListBeans);

    //新歌榜歌曲集合数据
    void onLatestMusicListData(List<LatestMusicModel.SongListBean> songListBeans);
    //新歌榜广告牌数据
    void onLatestMusicBillboardData(LatestMusicModel.BillboardBean billboardBean);

    //榜单所有数据
    void onChartData(List<ChartDataModel.ContentBeanX> contentBeans);

    //将获取到的所有歌曲数据都转换成Song集合对象
    void onSongDetail(SongDetail detail);

    //歌手的歌
    void onArtistMusics(List<ArtistMusic.SonglistBean> songlistBeans);

    void onError();
    void onSuccess();
}
