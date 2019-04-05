package com.jushi.muisc.chat.music.zhibo_video.radio.entity;

import java.util.List;

public class RadioSongListEntity {

    /**
     * error_code : 22000
     * result : {"channel":"KTV金曲","channelid":null,"ch_name":"public_tuijian_ktv","artistid":null,"avatar":null,"count":null,"songlist":[{"songid":"286736","title":"寂寞的恋人啊","artist":"莫文蔚","thumb":"http://qukufile2.qianqian.com/data2/pic/be38960b9af81f6bab4ed6b356d234cd/70333/70333.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"162","all_artist_id":"162","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"2076038","title":"手放开","artist":"李圣杰","thumb":"http://qukufile2.qianqian.com/data2/pic/3e365e7535b811de49d93048e40a3c35/165460/165460.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"376","all_artist_id":"376","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"242602080","title":"Lemon Tree(酸甜版)","artist":"苏慧伦","thumb":"http://qukufile2.qianqian.com/data2/pic/040f8761103c3a719e93f96f12d3073e/177848/177848.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"322","all_artist_id":"322","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"2050429","title":"在我生命中的每一天","artist":"成龙,苏慧伦","thumb":"http://qukufile2.qianqian.com/data2/pic/32878d3f25bb2ad00e577bf138515a4e/72949/72949.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"146","all_artist_id":"146,322","resource_type":"0","havehigh":2,"charge":0,"all_rate":"flac,320,128,224,96"},{"songid":"70244190","title":"野百合也有春天","artist":"潘越云","thumb":"http://qukufile2.qianqian.com/data2/music/7ECC845B67EBDFA15419477BF8515DC4/254338620/254338620.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"297","all_artist_id":"297","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"451514","title":"秘密","artist":"张震岳","thumb":"http://qukufile2.qianqian.com/data2/pic/607d8bb507271db35b30f18879ca0b9e/71754/71754.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"224","all_artist_id":"224","resource_type":"0","havehigh":2,"charge":0,"all_rate":"128,flac,320,224,96"},{"songid":"10386683","title":"我终于失去了你","artist":"赵传","thumb":"http://qukufile2.qianqian.com/data2/pic/124802097/124802097.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"345","all_artist_id":"345","resource_type":"0","havehigh":2,"charge":0,"all_rate":"64,96,128,224,320,flac"},{"songid":"124810874","title":"我很丑,可是我很温柔","artist":"赵传","thumb":"http://qukufile2.qianqian.com/data2/pic/a53d6848089c0b394f0c7b4995bf6a8b/612150234/612150234.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"345","all_artist_id":"345","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320"},{"songid":"1093691","title":"候鸟","artist":"五月天","thumb":"http://qukufile2.qianqian.com/data2/pic/2eaeabcdfd654f618098d2a88d4583a1/579848698/579848698.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"127","all_artist_id":"127","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"926730","title":"水晶","artist":"任贤齐,徐怀钰","thumb":"http://qukufile2.qianqian.com/data2/pic/73a3804e1b971cbebc63d99260278136/173971/173971.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"119","all_artist_id":"119,275","resource_type":"0","havehigh":2,"charge":0,"all_rate":"128,flac,320,224,96"},{"songid":null,"title":null,"artist":null,"thumb":"","method":0,"flow":0,"artist_id":null,"all_artist_id":null,"resource_type":null,"havehigh":0,"charge":0,"all_rate":""}]}
     */

    private int error_code;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * channel : KTV金曲
         * channelid : null
         * ch_name : public_tuijian_ktv
         * artistid : null
         * avatar : null
         * count : null
         * songlist : [{"songid":"286736","title":"寂寞的恋人啊","artist":"莫文蔚","thumb":"http://qukufile2.qianqian.com/data2/pic/be38960b9af81f6bab4ed6b356d234cd/70333/70333.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"162","all_artist_id":"162","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"2076038","title":"手放开","artist":"李圣杰","thumb":"http://qukufile2.qianqian.com/data2/pic/3e365e7535b811de49d93048e40a3c35/165460/165460.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"376","all_artist_id":"376","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"242602080","title":"Lemon Tree(酸甜版)","artist":"苏慧伦","thumb":"http://qukufile2.qianqian.com/data2/pic/040f8761103c3a719e93f96f12d3073e/177848/177848.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"322","all_artist_id":"322","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"2050429","title":"在我生命中的每一天","artist":"成龙,苏慧伦","thumb":"http://qukufile2.qianqian.com/data2/pic/32878d3f25bb2ad00e577bf138515a4e/72949/72949.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"146","all_artist_id":"146,322","resource_type":"0","havehigh":2,"charge":0,"all_rate":"flac,320,128,224,96"},{"songid":"70244190","title":"野百合也有春天","artist":"潘越云","thumb":"http://qukufile2.qianqian.com/data2/music/7ECC845B67EBDFA15419477BF8515DC4/254338620/254338620.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"297","all_artist_id":"297","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"451514","title":"秘密","artist":"张震岳","thumb":"http://qukufile2.qianqian.com/data2/pic/607d8bb507271db35b30f18879ca0b9e/71754/71754.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"224","all_artist_id":"224","resource_type":"0","havehigh":2,"charge":0,"all_rate":"128,flac,320,224,96"},{"songid":"10386683","title":"我终于失去了你","artist":"赵传","thumb":"http://qukufile2.qianqian.com/data2/pic/124802097/124802097.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"345","all_artist_id":"345","resource_type":"0","havehigh":2,"charge":0,"all_rate":"64,96,128,224,320,flac"},{"songid":"124810874","title":"我很丑,可是我很温柔","artist":"赵传","thumb":"http://qukufile2.qianqian.com/data2/pic/a53d6848089c0b394f0c7b4995bf6a8b/612150234/612150234.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"345","all_artist_id":"345","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320"},{"songid":"1093691","title":"候鸟","artist":"五月天","thumb":"http://qukufile2.qianqian.com/data2/pic/2eaeabcdfd654f618098d2a88d4583a1/579848698/579848698.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"127","all_artist_id":"127","resource_type":"0","havehigh":2,"charge":0,"all_rate":"96,128,224,320,flac"},{"songid":"926730","title":"水晶","artist":"任贤齐,徐怀钰","thumb":"http://qukufile2.qianqian.com/data2/pic/73a3804e1b971cbebc63d99260278136/173971/173971.jpg@s_2,w_90,h_90","method":0,"flow":0,"artist_id":"119","all_artist_id":"119,275","resource_type":"0","havehigh":2,"charge":0,"all_rate":"128,flac,320,224,96"},{"songid":null,"title":null,"artist":null,"thumb":"","method":0,"flow":0,"artist_id":null,"all_artist_id":null,"resource_type":null,"havehigh":0,"charge":0,"all_rate":""}]
         */

        private String channel;
        private Object channelid;
        private String ch_name;
        private Object artistid;
        private Object avatar;
        private Object count;
        private List<SonglistBean> songlist;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public Object getChannelid() {
            return channelid;
        }

        public void setChannelid(Object channelid) {
            this.channelid = channelid;
        }

        public String getCh_name() {
            return ch_name;
        }

        public void setCh_name(String ch_name) {
            this.ch_name = ch_name;
        }

        public Object getArtistid() {
            return artistid;
        }

        public void setArtistid(Object artistid) {
            this.artistid = artistid;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getCount() {
            return count;
        }

        public void setCount(Object count) {
            this.count = count;
        }

        public List<SonglistBean> getSonglist() {
            return songlist;
        }

        public void setSonglist(List<SonglistBean> songlist) {
            this.songlist = songlist;
        }

        public static class SonglistBean {
            /**
             * songid : 286736
             * title : 寂寞的恋人啊
             * artist : 莫文蔚
             * thumb : http://qukufile2.qianqian.com/data2/pic/be38960b9af81f6bab4ed6b356d234cd/70333/70333.jpg@s_2,w_90,h_90
             * method : 0
             * flow : 0
             * artist_id : 162
             * all_artist_id : 162
             * resource_type : 0
             * havehigh : 2
             * charge : 0
             * all_rate : 96,128,224,320,flac
             */

            private String songid;
            private String title;
            private String artist;
            private String thumb;
            private int method;
            private int flow;
            private String artist_id;
            private String all_artist_id;
            private String resource_type;
            private int havehigh;
            private int charge;
            private String all_rate;

            public String getSongid() {
                return songid;
            }

            public void setSongid(String songid) {
                this.songid = songid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getArtist() {
                return artist;
            }

            public void setArtist(String artist) {
                this.artist = artist;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public int getMethod() {
                return method;
            }

            public void setMethod(int method) {
                this.method = method;
            }

            public int getFlow() {
                return flow;
            }

            public void setFlow(int flow) {
                this.flow = flow;
            }

            public String getArtist_id() {
                return artist_id;
            }

            public void setArtist_id(String artist_id) {
                this.artist_id = artist_id;
            }

            public String getAll_artist_id() {
                return all_artist_id;
            }

            public void setAll_artist_id(String all_artist_id) {
                this.all_artist_id = all_artist_id;
            }

            public String getResource_type() {
                return resource_type;
            }

            public void setResource_type(String resource_type) {
                this.resource_type = resource_type;
            }

            public int getHavehigh() {
                return havehigh;
            }

            public void setHavehigh(int havehigh) {
                this.havehigh = havehigh;
            }

            public int getCharge() {
                return charge;
            }

            public void setCharge(int charge) {
                this.charge = charge;
            }

            public String getAll_rate() {
                return all_rate;
            }

            public void setAll_rate(String all_rate) {
                this.all_rate = all_rate;
            }
        }
    }
}
