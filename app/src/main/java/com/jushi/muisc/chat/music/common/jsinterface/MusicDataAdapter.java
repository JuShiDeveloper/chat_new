package com.jushi.muisc.chat.music.common.jsinterface;

import com.jushi.muisc.chat.music.home_page.artist.model.ArtistMusic;
import com.jushi.muisc.chat.music.home_page.artist.model.ArtistsModel;
import com.jushi.muisc.chat.music.home_page.banner.model.BannerModel;
import com.jushi.muisc.chat.music.chart.model.ChartDataModel;
import com.jushi.muisc.chat.music.common.public_model.LatestMusicModel;
import com.jushi.muisc.chat.music.search.model.SearchDataModel;
import com.jushi.muisc.chat.music.home_page.recommend.model.TodayRecommendModel;
import com.jushi.muisc.chat.music.common.public_model.SongDetail;

import java.util.List;

/**
 * 歌曲数据适配器
 */

public abstract class MusicDataAdapter implements OnMusicDataListener{
    @Override
    public void onBannerData(List<BannerModel.DataBean.SliderBean> sliderBeans) {

    }

    @Override
    public void onTodayRecommendData(List<TodayRecommendModel.ResultBean.ListBean> listBeans) {

    }

    @Override
    public void onArtistData(List<ArtistsModel.ArtistBean> artistBeans) {

    }

    @Override
    public void onSearchData(List<SearchDataModel.SongListBean> songListBeans) {

    }

    @Override
    public void onLatestMusicBillboardData(LatestMusicModel.BillboardBean billboardBean) {

    }

    @Override
    public void onLatestMusicListData(List<LatestMusicModel.SongListBean> songListBeans) {

    }

    @Override
    public void onChartData(List<ChartDataModel.ContentBeanX> contentBeans) {

    }

    @Override
    public void onSongDetail(SongDetail detail) {

    }

    @Override
    public void onArtistMusics(List<ArtistMusic.SonglistBean> songlistBeans) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess() {

    }
}
