package com.jushi.muisc.chat.music.home_page.banner.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cpx on 2018/1/27.
 */

public class BannerModel implements Serializable{

    /**
     * code : 0
     * data : {"slider":[{"linkUrl":"https://y.qq.com/m/digitalbum/gold/index.html?_video=true&id=3889148&g_f=shoujijiaodian","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000002DUw8O02w9ui.jpg","id":14028},{"linkUrl":"https://y.qq.com/msa/270/0_4799.html","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000003Usvcx1rI2jK.jpg","id":14041},{"linkUrl":"https://y.qq.com/m/digitalbum/gold/index.html?_video=true&id=3298803&g_f=shoujijiaodian","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000001zZjyc1zivBL.jpg","id":13983},{"linkUrl":"https://y.qq.com/m/act/sfhd/122.html","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000001H04LT0tmRFg.jpg","id":14022},{"linkUrl":"https://c.y.qq.com/node/m/client/music_headline/index.html?_hidehd=1&_button=2&zid=541629","picUrl":"http://y.gtimg.cn/music/photo_new/T003R720x288M000003KLbQB3U5iIs.jpg","id":13703}],"radioList":[{"picUrl":"http://y.gtimg.cn/music/photo/radio/track_radio_199_13_1.jpg","Ftitle":"热歌","radioid":199},{"picUrl":"http://y.gtimg.cn/music/photo/radio/track_radio_307_13_1.jpg","Ftitle":"一人一首招牌歌","radioid":307}],"songList":[{"songListDesc":"催泪大杀器！盘点演唱会经典万人大合唱","picUrl":"http://p.qpic.cn/music_cover/1Fr9IFMhWDPeUzWKVEjn3QTL2eX2QziaJmaL0ZAmsvtW71ic9IDUoYzg/300?n=1","id":"2646688496","accessnum":7858576,"songListAuthor":"Harry","pic_mid":"00333So02drvak","album_pic_mid":""},{"songListDesc":"纳尼？这些华语歌手竟然会唱日语歌！","picUrl":"http://p.qpic.cn/music_cover/z8wAFqicQ1qhImeiajkrgiaR4hYM3pzsUULFnicXshFXdw9uGkm261Ex9g/300?n=1","id":"1144416825","accessnum":614697,"songListAuthor":"风吹草地","pic_mid":"0013j8zs1jRnLQ","album_pic_mid":""},{"songListDesc":"精选内地十大民谣歌手代表作","picUrl":"http://p.qpic.cn/music_cover/hVUsfUFG2DV466URqw7PT7X66OknPIhic2mKDgicawN4qThIR7yhYY1w/300?n=1","id":"2043041547","accessnum":873838,"songListAuthor":"１'s ヽ","pic_mid":"004bFmjW2PXSqF","album_pic_mid":"0032YJyg2yF6Dd"},{"songListDesc":"$doopeBoi关注我网易yun歌手页","picUrl":"http://p.qpic.cn/music_cover/tkduvk4dwqBxwzZhsNe0nwkwyiaLHVkxtla7REsX0yNkhibOH3Bdb2og/300?n=1","id":"2040362185","accessnum":1155869,"songListAuthor":"SdopeBoi_7","pic_mid":"002s71FW1GD8go","album_pic_mid":"001iJq1y1Uq3zV"},{"songListDesc":"浮光掠影：ACG纯音乐赏析合辑","picUrl":"http://p.qpic.cn/music_cover/XMPAjfs5uwGZdWII3osvAvCRyNWx8Pqy5Yice41OCZlBhLtk0p0icNvg/300?n=1","id":"1723063372","accessnum":898094,"songListAuthor":"肥喵","pic_mid":"000xFtbN1l8ye8","album_pic_mid":"002egQPg3DWcCS"},{"songListDesc":"trip-hop单曲大推荐","picUrl":"http://y.gtimg.cn/music/photo_new/T005R600x600M000002CJKAY1LKpcz.jpg?n=1","id":"3482605622","accessnum":356777,"songListAuthor":"哑忍","pic_mid":"","album_pic_mid":"004aOQhn3PPOpK"}]}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private List<SliderBean> slider;
        private List<RadioListBean> radioList;
        private List<SongListBean> songList;

        public List<SliderBean> getSlider() {
            return slider;
        }

        public void setSlider(List<SliderBean> slider) {
            this.slider = slider;
        }

        public List<RadioListBean> getRadioList() {
            return radioList;
        }

        public void setRadioList(List<RadioListBean> radioList) {
            this.radioList = radioList;
        }

        public List<SongListBean> getSongList() {
            return songList;
        }

        public void setSongList(List<SongListBean> songList) {
            this.songList = songList;
        }

        public static class SliderBean implements Serializable{
            /**
             * linkUrl : https://y.qq.com/m/digitalbum/gold/index.html?_video=true&id=3889148&g_f=shoujijiaodian
             * picUrl : http://y.gtimg.cn/music/photo_new/T003R720x288M000002DUw8O02w9ui.jpg
             * id : 14028
             */

            private String linkUrl;
            private String picUrl;
            private int id;

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class RadioListBean implements Serializable{
            /**
             * picUrl : http://y.gtimg.cn/music/photo/radio/track_radio_199_13_1.jpg
             * Ftitle : 热歌
             * radioid : 199
             */

            private String picUrl;
            private String Ftitle;
            private int radioid;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getFtitle() {
                return Ftitle;
            }

            public void setFtitle(String Ftitle) {
                this.Ftitle = Ftitle;
            }

            public int getRadioid() {
                return radioid;
            }

            public void setRadioid(int radioid) {
                this.radioid = radioid;
            }
        }

        public static class SongListBean implements Serializable{
            /**
             * songListDesc : 催泪大杀器！盘点演唱会经典万人大合唱
             * picUrl : http://p.qpic.cn/music_cover/1Fr9IFMhWDPeUzWKVEjn3QTL2eX2QziaJmaL0ZAmsvtW71ic9IDUoYzg/300?n=1
             * id : 2646688496
             * accessnum : 7858576
             * songListAuthor : Harry
             * pic_mid : 00333So02drvak
             * album_pic_mid :
             */

            private String songListDesc;
            private String picUrl;
            private String id;
            private int accessnum;
            private String songListAuthor;
            private String pic_mid;
            private String album_pic_mid;

            public String getSongListDesc() {
                return songListDesc;
            }

            public void setSongListDesc(String songListDesc) {
                this.songListDesc = songListDesc;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getAccessnum() {
                return accessnum;
            }

            public void setAccessnum(int accessnum) {
                this.accessnum = accessnum;
            }

            public String getSongListAuthor() {
                return songListAuthor;
            }

            public void setSongListAuthor(String songListAuthor) {
                this.songListAuthor = songListAuthor;
            }

            public String getPic_mid() {
                return pic_mid;
            }

            public void setPic_mid(String pic_mid) {
                this.pic_mid = pic_mid;
            }

            public String getAlbum_pic_mid() {
                return album_pic_mid;
            }

            public void setAlbum_pic_mid(String album_pic_mid) {
                this.album_pic_mid = album_pic_mid;
            }
        }
    }
}
