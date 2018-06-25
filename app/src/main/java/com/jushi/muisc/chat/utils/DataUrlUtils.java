package com.jushi.muisc.chat.utils;

/**
 * Created by cpx on 2018/1/27.
 */

public class DataUrlUtils {
    //轮播图地址
    public static String getBannerUrl() {
        String url = "https://c.y.qq.com/musichall/fcgi-bin/fcg_yqqhomepagerecommend.fcg";
        return url;
    }

    public static String getTodayRecommendUrl(int count) {
        //今日推荐歌曲的地址链接
        String recommendMusicUrl = " http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=ppzs&operator=0&method=baidu.ting.song.userRecSongList&format=json&page_no=1&page_size=" + count + "&baiduid=b68565b592630952b805e80617898740&bduss=HpjZlJlTlFua2pwYk1JfkxkNk5QYVFQMWd5OGZ5YTJ4S3I3QmdCMFVjZ2xqemRZSVFBQUFBJCQAAAAAAAAAAAEAAADAe7RIsK7A1rXjtvlSSwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACUCEFglAhBYe";
        return recommendMusicUrl;
    }

    public static String getHotArtistsUrl(int count) {
        //热门歌手地址
        String hotArtistUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.9.1&channel=ppzs&operator=0&method=baidu.ting.artist.getList&format=json&offset=0&limit=" + count + "&order=1&area=0&sex=0";
        return hotArtistUrl;
    }

    public static String getChinaArtistUrl() {
        //华语男歌手
        String chinaUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.9.1&channel=ppzs&operator=0&method=baidu.ting.artist.getList&format=json&offset=0&limit=150&order=1&area=6";
        return chinaUrl;
    }

    public static String getOuMeiArtistUrl() {
        //欧美歌手
        String ouMeiUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.9.1&channel=ppzs&operator=0&method=baidu.ting.artist.getList&format=json&offset=0&limit=150&order=1&area=3";
        return ouMeiUrl;
    }

    public static String getHanGuoArtistUrl() {
        //韩国歌手
        String hanGuoUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.9.1&channel=ppzs&operator=0&method=baidu.ting.artist.getList&format=json&offset=0&limit=150&order=1&area=7";
        return hanGuoUrl;
    }

    public static String getJapanArtistUrl() {
        //日本歌手
        String japanUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.9.1&channel=ppzs&operator=0&method=baidu.ting.artist.getList&format=json&offset=0&limit=150&order=1&area=60";
        return japanUrl;
    }

    public static String getOtherArtistUrl() {
        //其他歌手
        String otherArtistUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.9.1&channel=ppzs&operator=0&method=baidu.ting.artist.getList&format=json&offset=0&limit=150&order=1&area=5";
        return otherArtistUrl;
    }

    //获得歌手的歌曲Url
    public static String getArtistMusics(String artist_ting_uid){
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.9.1&channel=ppzs&operator=0&method=baidu.ting.artist.getSongList&format=json&order=2&tinguid="+artist_ting_uid+"&artistid="+artist_ting_uid+"&offset=0&limits=100";
        return url;
    }

    //MV 链接
    public static String getMvUrl(int count) {
        String mvUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=ppzs&operator=0&provider=11%2C12&method=baidu.ting.mv.searchMV&format=json&order=1&page_num=1&page_size=" + count + "&query=%E5%85%A8%E9%83%A8";
        return mvUrl;
    }

    //直播链接
    public static String getZhiBoUrl(int count) {
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=ppzs&operator=0&method=baidu.ting.show.live&page_no=1&page_size=" + count + "";
        return url;
    }

    //播放直播的URL
    public static String getPlayZhiBoUrl(String rid) {
        String playUrl = "http://www.9xiu.com/mobile/" + rid + "";
        return playUrl;
    }

    //播放MV的URL
    public static String getPlayMVUrl(String mvId) {
        String playUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.9.1&channel=ppzs&operator=0&provider=11%2C12&method=baidu.ting.mv.playMV&format=json&mv_id=" + mvId + "&song_id=&definition=0";
        return playUrl;
    }

    //搜索的URL
    public static String getSearchUrl(String keyWords, int page) {
        String searchUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.search.common&format=json&query=" + keyWords + "&page_no=" + page + "&page_size=25";
        return searchUrl;
    }

    //总榜单URL
    public static String getChartUrl() {
        String chartUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=ppzs&operator=0&method=baidu.ting.billboard.billCategory&format=json&kflag=2";
        return chartUrl;
    }

    //根据歌曲id 获得歌曲的详细信息
    public static String getSongInfoUrl(String songId){
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.song.playAAC&songid="+songId+"";
        return url;
    }

    //获得每一个榜单的详细数据Url
    public static String getDetailChartUrl(String chartTitle, int count) {
        if (chartTitle.equals(Constant.newMusicChart)) { //新歌榜
            return getNewMusicChartUrl(count);
        } else if (chartTitle.equals(Constant.hotMusicChart)) {//热歌榜
            return getHotChartUrl(count);
        } else if (chartTitle.equals(Constant.chinaMusicChart)) {//华语金曲榜
            return getChinaMusicUrl(count);
        } else if (chartTitle.equals(Constant.oldMusicChart)) {//经典老歌榜
            return getOldMusicUrl(count);
        } else if (chartTitle.equals(Constant.netWorkChart)) {//网络歌曲榜
            return getNetWorkMusicUrl(count);
        } else if (chartTitle.equals(Constant.yingShiMusicChart)) {//影视金曲榜
            return getYingShiMusicUrl(count);
        } else if (chartTitle.equals(Constant.qingGeMusicChart)) {//情歌对唱榜
            return getQingGeDuiChangUrl(count);
        } else if (chartTitle.equals(Constant.yaoGunChart)) {//摇滚榜
            return getYaoGunMusicUrl(count);
        }else if (chartTitle.equals(Constant.ouMeiChart)){ //欧美金曲榜
            return getOuMeiMusicUrl(count);
        } else {
            return null;
        }
    }

    //新歌榜URL
    public static String getNewMusicChartUrl(int count) {
        String newUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=ppzs&operator=0&method=baidu.ting.billboard.billList&format=json&type=1&offset=0&size=" + count + "&fields=song_id%2Ctitle%2Cauthor%2Calbum_title%2Cpic_big%2Cpic_small%2Chavehigh%2Call_rate%2Ccharge%2Chas_mv_mobile%2Clearn%2Csong_source%2Ckorean_bb_song";
        return newUrl;
    }

    //热歌榜URL
    public static String getHotChartUrl(int count) {
        String hotChartUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.9.0.0&channel=ppzs&operator=0&method=baidu.ting.billboard.billList&format=json&type=2&offset=0&size=" + count + "&fields=song_id%2Ctitle%2Cauthor%2Calbum_title%2Cpic_big%2Cpic_small%2Chavehigh%2Call_rate%2Ccharge%2Chas_mv_mobile%2Clearn%2Csong_source%2Ckorean_bb_song";
        return hotChartUrl;
    }

    //网络歌曲榜URL
    private static String getNetWorkMusicUrl(int count) {
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=25&offset=0&size=" + count + "";
        return url;
    }

    //情歌对唱榜URL
    private static String getQingGeDuiChangUrl(int count) {
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=23&offset=0&size=" + count + "";
        return url;
    }

    //经典老歌榜URL
    private static String getOldMusicUrl(int count) {
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=22&offset=0&size=" + count + "";
        return url;
    }

    //欧美金曲榜URL
    private static String getOuMeiMusicUrl(int count) {
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=21&offset=0&size=" + count + "";
        return url;
    }

    //华语金曲榜URL
    private static String getChinaMusicUrl(int count) {
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=20&offset=0&size=" + count + "";
        return url;
    }

    //影视金曲榜URL
    private static String getYingShiMusicUrl(int count) {
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=24&offset=0&size=" + count + "";
        return url;
    }

    //叱咤歌曲榜Url
    private String getChiZhaMusicUrl(int count) {
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=7&offset=0&size=" + count + "";
        return url;
    }

    //摇滚榜URL
    private static String getYaoGunMusicUrl(int count) {
        String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=11&offset=0&size=" + count + "";
        return url;
    }

}
