package com.jushi.muisc.chat.music.service;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jushi.muisc.chat.music.jsinterface.DownloadListener;
import com.jushi.muisc.chat.music.zhibo_video.LiveAndMvDataAdapter;
import com.jushi.muisc.chat.music.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.music.home_page.artist.model.ArtistMusic;
import com.jushi.muisc.chat.music.home_page.artist.model.ArtistsModel;
import com.jushi.muisc.chat.music.home_page.banner.model.BannerModel;
import com.jushi.muisc.chat.music.chart.model.ChartDataModel;
import com.jushi.muisc.chat.music.public_model.LatestMusicModel;
import com.jushi.muisc.chat.sliding_menu.localmusic.model.Song;
import com.jushi.muisc.chat.music.zhibo_video.mv.model.MVBean;
import com.jushi.muisc.chat.music.zhibo_video.mv.model.MVItemModel;
import com.jushi.muisc.chat.music.search.model.SearchDataModel;
import com.jushi.muisc.chat.music.home_page.recommend.model.TodayRecommendModel;
import com.jushi.muisc.chat.music.public_model.SongDetail;
import com.jushi.muisc.chat.music.zhibo_video.zhibo.model.ZhiBoModel;
import com.jushi.muisc.chat.music.utils.http.OkHttpTool;
import com.jushi.muisc.chat.music.utils.music.DataUrlUtils;
import com.jushi.muisc.chat.utils.PATH;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 联网获取数据
 */

public class NetWorkService {
    private List<Song> songs = new ArrayList<>();
    private static NetWorkService workService;

    private NetWorkService() {
    }

    public static NetWorkService getInstance(Context context) {
        if (workService == null) {
            workService = new NetWorkService();
        }
        return workService;
    }

    /**
     * 获得首页轮播图数据
     */
    public void getBannerImageData(final MusicDataAdapter dataAdapter) {
        OkHttpTool.httpClient(DataUrlUtils.getBannerUrl(), new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) {
                try {
                    String body = response.body().string();
                    BannerModel bannerModel = JSONObject.parseObject(body, BannerModel.class);
                    dataAdapter.onBannerData(bannerModel.getData().getSlider());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获得今日推荐的歌曲数据
     *
     * @param count
     * @param dataAdapter
     */
    public void getTodayRecommendMusicData(int count, final MusicDataAdapter dataAdapter) {
        songs.clear();
        OkHttpTool.httpClient(DataUrlUtils.getTodayRecommendUrl(count), new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) {
                try {
                    String body = response.body().string();
                    TodayRecommendModel recommendModel = JSONObject.parseObject(body, TodayRecommendModel.class);
                    List<TodayRecommendModel.ResultBean.ListBean> rModel = recommendModel.getResult().getList();
                    dataAdapter.onTodayRecommendData(rModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取歌手数据
     *
     * @param artistUrl
     * @param dataAdapter
     */
    public void getArtistData(String artistUrl, final MusicDataAdapter dataAdapter) {
        OkHttpTool.httpClient(artistUrl, new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) {
                try {
                    String body = response.body().string();
                    ArtistsModel artistsModel = JSONObject.parseObject(body, ArtistsModel.class);
                    dataAdapter.onArtistData(artistsModel.getArtist());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取MV数据
     */
    public void getLatestMVData(int count, final LiveAndMvDataAdapter dataAdapter) {
        OkHttpTool.httpClient(DataUrlUtils.getMvUrl(count), new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) {
                try {
                    String body = response.body().string();
                    MVBean mvBean = JSONObject.parseObject(body, MVBean.class);
                    dataAdapter.onMvData(mvBean.getResult().getMv_list());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 获取直播数据
     */
    public void getLiveData(int count, final LiveAndMvDataAdapter dataAdapter) {
        OkHttpTool.httpClient(DataUrlUtils.getZhiBoUrl(count), new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) {
                try {
                    String body = response.body().string();
                    ZhiBoModel zhiBoModel = JSONObject.parseObject(body, ZhiBoModel.class);
                    dataAdapter.onLiveData(zhiBoModel.getData().getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取MV视频的详细信息
     */
    public void getMvDetailInfo(String mv_id, final LiveAndMvDataAdapter dataAdapter) {
        OkHttpTool.httpClient(DataUrlUtils.getPlayMVUrl(mv_id), new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) {
                try {
                    String body = response.body().string();
                    MVItemModel itemModel = new Gson().fromJson(body, MVItemModel.class);
                    dataAdapter.onMvDetailInfo(itemModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获得搜索的歌曲数据
     *
     * @param keyWords    搜索关键词
     * @param page_no     分页数
     * @param dataAdapter
     */
    public void getSearchData(String keyWords, int page_no, final MusicDataAdapter dataAdapter) {
        OkHttpTool.httpClient(DataUrlUtils.getSearchUrl(keyWords, page_no), new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) {
                try {
                    String body = response.body().string();
                    SearchDataModel dataModel = JSONObject.parseObject(body, SearchDataModel.class);
                    dataAdapter.onSearchData(dataModel.getSong_list());
                    dataAdapter.onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    dataAdapter.onError();
                }
            }
        });
    }

    /**
     * 获得榜单详细的音乐数据（新歌榜，热歌榜，华语金曲榜......）
     *
     * @param chartUrl
     */
    public void getChartDetailMusicData(String chartUrl, final MusicDataAdapter dataAdapter) {
        OkHttpTool.httpClient(chartUrl, new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) {
                try {
                    String body = response.body().string();
                    LatestMusicModel musicModel = JSONObject.parseObject(body, LatestMusicModel.class);
                    dataAdapter.onLatestMusicListData(musicModel.getSong_list());
                    dataAdapter.onLatestMusicBillboardData(musicModel.getBillboard());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /***
     * 获得总榜单数据
     *
     * @param dataAdapter
     */
    public void getChartData(final MusicDataAdapter dataAdapter) {
        OkHttpTool.httpClient(DataUrlUtils.getChartUrl(), new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) {
                try {
                    String body = response.body().string();
                    ChartDataModel chartDataModel = JSONObject.parseObject(body, ChartDataModel.class);
                    dataAdapter.onChartData(chartDataModel.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 根据歌曲id 获得歌曲的详细信息
     *
     * @param songId
     * @return
     */
    public void getSongInfo(String songId, final MusicDataAdapter dataAdapter) {
        OkHttpTool.httpClient(DataUrlUtils.getSongInfoUrl(songId), new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {
                dataAdapter.onError();
            }

            @Override
            public void onResponse(Response response) {
                try {
                    String body = response.body().string();
                    SongDetail songDetail = JSONObject.parseObject(body, SongDetail.class);
                    dataAdapter.onSongDetail(songDetail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 根据歌手id 获得歌手的歌
     *
     * @param artist_ting_uid
     */
    public void getArtistMusics(String artist_ting_uid, final MusicDataAdapter dataAdapter) {
        OkHttpTool.httpClient(DataUrlUtils.getArtistMusics(artist_ting_uid), new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {
                dataAdapter.onError();
            }

            @Override
            public void onResponse(Response response) {
                try {
                    String body = response.body().string();
                    ArtistMusic artistMusic = JSONObject.parseObject(body, ArtistMusic.class);
                    dataAdapter.onArtistMusics(artistMusic.getSonglist());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * download
     */
    public void downloadMusic(final String fileLink, final String fileName, final DownloadListener downloadListener) {
        PATH.initPath();
        OkHttpTool.httpClient(fileLink, new OkHttpTool.OnClientListener() {
            @Override
            public void onError() {
                downloadListener.onDownloadFailed();
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int length = 0;
                FileOutputStream fos = null;
                String downloadPath = PATH.downloadMusicDir();
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(downloadPath, fileName);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((length = is.read(buf)) != -1) {
                        fos.write(buf, 0, length);
                        sum += length;
                        int progress = (int) (sum * 1.0f / total * 100);
                        downloadListener.onDownloading(progress);
                    }
                    fos.flush();
                    downloadListener.onDownloadSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    downloadListener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
