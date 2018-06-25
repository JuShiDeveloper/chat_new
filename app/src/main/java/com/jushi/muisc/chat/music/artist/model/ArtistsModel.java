package com.jushi.muisc.chat.music.artist.model;

import java.io.Serializable;
import java.util.List;

/**
 * 歌手
 */

public class ArtistsModel implements Serializable{

    /**
     * artist : [{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246669444/246669444.jpg@s_0,w_20","firstchar":"X","ting_uid":"2517","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246669444/246669444.jpg@s_0,w_120","name":"薛之谦","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"88","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246669444/246669444.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246669444/246669444.jpg@s_0,w_240","albums_total":"14","songs_total":"85"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246668350/246668350.jpg@s_0,w_20","firstchar":"Q","ting_uid":"245815","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246668350/246668350.jpg@s_0,w_120","name":"祁隆","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"57297","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246668350/246668350.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246668350/246668350.jpg@s_0,w_240","albums_total":"44","songs_total":"175"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/4e2f99725085e29afbe3004b34e3b3ea/566870515/566870515.jpg@s_0,w_20","firstchar":"L","ting_uid":"1376","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/4e2f99725085e29afbe3004b34e3b3ea/566870515/566870515.jpg@s_0,w_120","name":"龙梅子","islocate":0,"gender":"1","country":"中国","piao_id":"0","artist_id":"762","avatar_small":"http://qukufile2.qianqian.com/data2/pic/4e2f99725085e29afbe3004b34e3b3ea/566870515/566870515.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/4e2f99725085e29afbe3004b34e3b3ea/566870515/566870515.jpg@s_0,w_240","albums_total":"19","songs_total":"121"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/121900710/121900710.jpg@s_0,w_20","firstchar":"L","ting_uid":"132632388","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/121900710/121900710.jpg@s_0,w_120","name":"刘珂矣","islocate":1,"gender":"1","country":"","piao_id":"0","artist_id":"119680493","avatar_small":"http://qukufile2.qianqian.com/data2/pic/121900710/121900710.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/121900710/121900710.jpg@s_0,w_240","albums_total":"8","songs_total":"26"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/ef48ad0b18aafcff40ed9877a603ff22/267376192/267376192.jpg@s_0,w_20","firstchar":"Q","ting_uid":"1117","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/ef48ad0b18aafcff40ed9877a603ff22/267376192/267376192.jpg@s_0,w_120","name":"齐秦","islocate":0,"gender":"0","country":"台湾","piao_id":"0","artist_id":"163","avatar_small":"http://qukufile2.qianqian.com/data2/pic/ef48ad0b18aafcff40ed9877a603ff22/267376192/267376192.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/ef48ad0b18aafcff40ed9877a603ff22/267376192/267376192.jpg@s_0,w_240","albums_total":"36","songs_total":"217"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246669218/246669218.jpg@s_0,w_20","firstchar":"X","ting_uid":"707709","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246669218/246669218.jpg@s_0,w_120","name":"小蓓蕾组合","islocate":0,"gender":"2","country":"中国","piao_id":"0","artist_id":"10367212","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246669218/246669218.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246669218/246669218.jpg@s_0,w_240","albums_total":"163","songs_total":"3022"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246710232/246710232.jpg@s_0,w_20","firstchar":"Z","ting_uid":"1097","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246710232/246710232.jpg@s_0,w_120","name":"周华健","islocate":0,"gender":"0","country":"香港","piao_id":"0","artist_id":"123","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246710232/246710232.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246710232/246710232.jpg@s_0,w_240","albums_total":"39","songs_total":"595"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246709489/246709489.jpg@s_0,w_20","firstchar":"R","ting_uid":"1094","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246709489/246709489.jpg@s_0,w_120","name":"任贤齐","islocate":0,"gender":"0","country":"台湾","piao_id":"0","artist_id":"119","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246709489/246709489.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246709489/246709489.jpg@s_0,w_240","albums_total":"18","songs_total":"307"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/86426181/86426181.jpg@s_0,w_20","firstchar":"L","ting_uid":"1629","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/86426181/86426181.jpg@s_0,w_120","name":"冷漠","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"1842","avatar_small":"http://qukufile2.qianqian.com/data2/pic/86426181/86426181.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/86426181/86426181.jpg@s_0,w_240","albums_total":"37","songs_total":"101"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246708144/246708144.jpg@s_0,w_20","firstchar":"L","ting_uid":"1067","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246708144/246708144.jpg@s_0,w_120","name":"刘若英","islocate":0,"gender":"1","country":"台湾","piao_id":"0","artist_id":"74","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246708144/246708144.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246708144/246708144.jpg@s_0,w_240","albums_total":"12","songs_total":"195"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/99c89b46db96782245fa64d48ea8a08a/540354163/540354163.jpg@s_0,w_20","firstchar":"L","ting_uid":"1383","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/99c89b46db96782245fa64d48ea8a08a/540354163/540354163.jpg@s_0,w_120","name":"李健","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"776","avatar_small":"http://qukufile2.qianqian.com/data2/pic/99c89b46db96782245fa64d48ea8a08a/540354163/540354163.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/99c89b46db96782245fa64d48ea8a08a/540354163/540354163.jpg@s_0,w_240","albums_total":"13","songs_total":"67"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/b78856798d5137cf530926e60f8fb166/260256392/260256392.jpg@s_0,w_20","firstchar":"Z","ting_uid":"1209","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/b78856798d5137cf530926e60f8fb166/260256392/260256392.jpg@s_0,w_120","name":"赵传","islocate":0,"gender":"0","country":"台湾","piao_id":"0","artist_id":"345","avatar_small":"http://qukufile2.qianqian.com/data2/pic/b78856798d5137cf530926e60f8fb166/260256392/260256392.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/b78856798d5137cf530926e60f8fb166/260256392/260256392.jpg@s_0,w_240","albums_total":"16","songs_total":"214"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246585973/246585973.jpg@s_0,w_20","firstchar":"W","ting_uid":"821050","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246585973/246585973.jpg@s_0,w_120","name":"乌兰图雅","islocate":0,"gender":"1","country":"中国","piao_id":"0","artist_id":"5734662","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246585973/246585973.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246585973/246585973.jpg@s_0,w_240","albums_total":"25","songs_total":"86"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/23372f2991e404fbb9d25b78d41c2af7/567299513/567299513.JPG@s_0,w_20","firstchar":"M","ting_uid":"340290414","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/23372f2991e404fbb9d25b78d41c2af7/567299513/567299513.JPG@s_0,w_120","name":"Mc黑总 bb机登录用户","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"567299514","avatar_small":"http://qukufile2.qianqian.com/data2/pic/23372f2991e404fbb9d25b78d41c2af7/567299513/567299513.JPG@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/23372f2991e404fbb9d25b78d41c2af7/567299513/567299513.JPG@s_0,w_240","albums_total":"0","songs_total":"1"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246668439/246668439.jpg@s_0,w_20","firstchar":"W","ting_uid":"45561","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246668439/246668439.jpg@s_0,w_120","name":"王菲","islocate":0,"gender":"1","country":"香港","piao_id":"0","artist_id":"15","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246668439/246668439.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246668439/246668439.jpg@s_0,w_240","albums_total":"47","songs_total":"715"},{"area":"4","avatar_mini":"","firstchar":"X","ting_uid":"239562591","avatar_middle":"","name":"新雅室内乐","islocate":0,"gender":"3","country":"","piao_id":"0","artist_id":"266641340","avatar_small":"","avatar_big":"","albums_total":"20","songs_total":"222"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/262900349/262900349.jpg@s_0,w_20","firstchar":"X","ting_uid":"1579","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/262900349/262900349.jpg@s_0,w_120","name":"徐佳莹","islocate":0,"gender":"1","country":"台湾","piao_id":"0","artist_id":"1641","avatar_small":"http://qukufile2.qianqian.com/data2/pic/262900349/262900349.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/262900349/262900349.jpg@s_0,w_240","albums_total":"12","songs_total":"123"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246584807/246584807.jpg@s_0,w_20","firstchar":"C","ting_uid":"1224778","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246584807/246584807.jpg@s_0,w_120","name":"崔子格","islocate":1,"gender":"1","country":"中国","piao_id":"0","artist_id":"12381018","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246584807/246584807.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246584807/246584807.jpg@s_0,w_240","albums_total":"30","songs_total":"79"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246708025/246708025.jpg@s_0,w_20","firstchar":"L","ting_uid":"1052","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246708025/246708025.jpg@s_0,w_120","name":"林俊杰","islocate":0,"gender":"0","country":"新加坡","piao_id":"0","artist_id":"45","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246708025/246708025.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246708025/246708025.jpg@s_0,w_240","albums_total":"14","songs_total":"153"},{"area":"4","avatar_mini":"http://qukufile2.qianqian.com/data2/music/A393900E5787D910653060C38E82AA66/252364271/252364271.jpg@s_0,w_20","firstchar":"S","ting_uid":"553065","avatar_middle":"http://qukufile2.qianqian.com/data2/music/A393900E5787D910653060C38E82AA66/252364271/252364271.jpg@s_0,w_120","name":"少儿歌曲","islocate":0,"gender":"3","country":"","piao_id":"0","artist_id":"53155","avatar_small":"http://qukufile2.qianqian.com/data2/music/A393900E5787D910653060C38E82AA66/252364271/252364271.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/music/A393900E5787D910653060C38E82AA66/252364271/252364271.jpg@s_0,w_240","albums_total":"8","songs_total":"119"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/5de4020c33b2404ad589ff5131479e1b/261054551/261054551.jpg@s_0,w_20","firstchar":"S","ting_uid":"1174","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/5de4020c33b2404ad589ff5131479e1b/261054551/261054551.jpg@s_0,w_120","name":"宋祖英","islocate":0,"gender":"1","country":"中国","piao_id":"0","artist_id":"285","avatar_small":"http://qukufile2.qianqian.com/data2/pic/5de4020c33b2404ad589ff5131479e1b/261054551/261054551.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/5de4020c33b2404ad589ff5131479e1b/261054551/261054551.jpg@s_0,w_240","albums_total":"26","songs_total":"191"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246707883/246707883.jpg@s_0,w_20","firstchar":"L","ting_uid":"1095","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246707883/246707883.jpg@s_0,w_120","name":"梁静茹","islocate":0,"gender":"1","country":"马来西亚","piao_id":"0","artist_id":"120","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246707883/246707883.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246707883/246707883.jpg@s_0,w_240","albums_total":"17","songs_total":"214"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246586325/246586325.jpg@s_0,w_20","firstchar":"X","ting_uid":"1557","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246586325/246586325.jpg@s_0,w_120","name":"许嵩","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"1483","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246586325/246586325.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246586325/246586325.jpg@s_0,w_240","albums_total":"22","songs_total":"73"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/07c51ce40aa7ebf333cb40591b00f5b0/542130374/542130374.png@s_0,w_20","firstchar":"L","ting_uid":"1133","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/07c51ce40aa7ebf333cb40591b00f5b0/542130374/542130374.png@s_0,w_120","name":"林忆莲","islocate":0,"gender":"1","country":"香港","piao_id":"0","artist_id":"212","avatar_small":"http://qukufile2.qianqian.com/data2/pic/07c51ce40aa7ebf333cb40591b00f5b0/542130374/542130374.png@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/07c51ce40aa7ebf333cb40591b00f5b0/542130374/542130374.png@s_0,w_240","albums_total":"42","songs_total":"699"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246680442/246680442.jpg@s_0,w_20","firstchar":"L","ting_uid":"2611","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246680442/246680442.jpg@s_0,w_120","name":"刘惜君","islocate":1,"gender":"1","country":"中国","piao_id":"0","artist_id":"1665","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246680442/246680442.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246680442/246680442.jpg@s_0,w_240","albums_total":"8","songs_total":"44"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/8c15bd3358b117fb3469f02be30fc9bc/544173556/544173556.jpg@s_0,w_20","firstchar":"H","ting_uid":"1219","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/8c15bd3358b117fb3469f02be30fc9bc/544173556/544173556.jpg@s_0,w_120","name":"韩红","islocate":0,"gender":"1","country":"中国","piao_id":"0","artist_id":"362","avatar_small":"http://qukufile2.qianqian.com/data2/pic/8c15bd3358b117fb3469f02be30fc9bc/544173556/544173556.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/8c15bd3358b117fb3469f02be30fc9bc/544173556/544173556.jpg@s_0,w_240","albums_total":"8","songs_total":"36"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/ff8a54b6819bc6227465f03a868169c8/274144087/274144087.jpg@s_0,w_20","firstchar":"L","ting_uid":"1314","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/ff8a54b6819bc6227465f03a868169c8/274144087/274144087.jpg@s_0,w_120","name":"老狼","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"537","avatar_small":"http://qukufile2.qianqian.com/data2/pic/ff8a54b6819bc6227465f03a868169c8/274144087/274144087.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/ff8a54b6819bc6227465f03a868169c8/274144087/274144087.jpg@s_0,w_240","albums_total":"4","songs_total":"25"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246585831/246585831.jpg@s_0,w_20","firstchar":"S","ting_uid":"1584151","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246585831/246585831.jpg@s_0,w_120","name":"孙露","islocate":0,"gender":"1","country":"中国","piao_id":"0","artist_id":"1868","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246585831/246585831.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246585831/246585831.jpg@s_0,w_240","albums_total":"6","songs_total":"23"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/music/2CB72577F457B63BA74E7283CC8E9FE1/252055689/252055689.jpg@s_0,w_20","firstchar":"Y","ting_uid":"1457","avatar_middle":"http://qukufile2.qianqian.com/data2/music/2CB72577F457B63BA74E7283CC8E9FE1/252055689/252055689.jpg@s_0,w_120","name":"宇桐非","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"1052","avatar_small":"http://qukufile2.qianqian.com/data2/music/2CB72577F457B63BA74E7283CC8E9FE1/252055689/252055689.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/music/2CB72577F457B63BA74E7283CC8E9FE1/252055689/252055689.jpg@s_0,w_240","albums_total":"9","songs_total":"20"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/51734e72b779b86086052a5da798d56c/269993940/269993940.jpg@s_0,w_20","firstchar":"J","ting_uid":"232949589","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/51734e72b779b86086052a5da798d56c/269993940/269993940.jpg@s_0,w_120","name":"贾乃亮","islocate":0,"gender":"0","country":"","piao_id":"0","artist_id":"134394404","avatar_small":"http://qukufile2.qianqian.com/data2/pic/51734e72b779b86086052a5da798d56c/269993940/269993940.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/51734e72b779b86086052a5da798d56c/269993940/269993940.jpg@s_0,w_240","albums_total":"4","songs_total":"3"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246668318/246668318.jpg@s_0,w_20","firstchar":"L","ting_uid":"1925","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246668318/246668318.jpg@s_0,w_120","name":"李宗盛","islocate":0,"gender":"0","country":"台湾","piao_id":"0","artist_id":"4598","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246668318/246668318.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246668318/246668318.jpg@s_0,w_240","albums_total":"10","songs_total":"231"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246586345/246586345.jpg@s_0,w_20","firstchar":"X","ting_uid":"1226","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246586345/246586345.jpg@s_0,w_120","name":"许巍","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"371","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246586345/246586345.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246586345/246586345.jpg@s_0,w_240","albums_total":"5","songs_total":"33"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/ce52b58848ff456cd4d48765f2f3dd1e/267770218/267770218.jpg@s_0,w_20","firstchar":"A","ting_uid":"1115","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/ce52b58848ff456cd4d48765f2f3dd1e/267770218/267770218.jpg@s_0,w_120","name":"阿杜","islocate":0,"gender":"0","country":"新加坡","piao_id":"0","artist_id":"160","avatar_small":"http://qukufile2.qianqian.com/data2/pic/ce52b58848ff456cd4d48765f2f3dd1e/267770218/267770218.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/ce52b58848ff456cd4d48765f2f3dd1e/267770218/267770218.jpg@s_0,w_240","albums_total":"14","songs_total":"106"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246667743/246667743.jpg@s_0,w_20","firstchar":"G","ting_uid":"617453","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246667743/246667743.jpg@s_0,w_120","name":"高安","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"3699","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246667743/246667743.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246667743/246667743.jpg@s_0,w_240","albums_total":"18","songs_total":"55"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/602a4c8ed96c51a393065793b5c3522e/259858280/259858280.jpg@s_0,w_20","firstchar":"H","ting_uid":"31514359","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/602a4c8ed96c51a393065793b5c3522e/259858280/259858280.jpg@s_0,w_120","name":"好妹妹乐队","islocate":1,"gender":"3","country":"中国","piao_id":"0","artist_id":"20633953","avatar_small":"http://qukufile2.qianqian.com/data2/pic/602a4c8ed96c51a393065793b5c3522e/259858280/259858280.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/602a4c8ed96c51a393065793b5c3522e/259858280/259858280.jpg@s_0,w_240","albums_total":"26","songs_total":"70"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/86540393/86540393.jpg@s_0,w_20","firstchar":"S","ting_uid":"59859914","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/86540393/86540393.jpg@s_0,w_120","name":"宋冬野","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"44805274","avatar_small":"http://qukufile2.qianqian.com/data2/pic/86540393/86540393.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/86540393/86540393.jpg@s_0,w_240","albums_total":"4","songs_total":"16"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/bba625524b5b07c1f71fa6af88aac972/542056761/542056761.jpg@s_0,w_20","firstchar":"C","ting_uid":"1228","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/bba625524b5b07c1f71fa6af88aac972/542056761/542056761.jpg@s_0,w_120","name":"陈淑桦","islocate":0,"gender":"1","country":"台湾","piao_id":"0","artist_id":"374","avatar_small":"http://qukufile2.qianqian.com/data2/pic/bba625524b5b07c1f71fa6af88aac972/542056761/542056761.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/bba625524b5b07c1f71fa6af88aac972/542056761/542056761.jpg@s_0,w_240","albums_total":"10","songs_total":"212"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/music/1FBD613610B4C22B70E141A75F6518D3/252826181/252826181.jpg@s_0,w_20","firstchar":"G","ting_uid":"14674804","avatar_middle":"http://qukufile2.qianqian.com/data2/music/1FBD613610B4C22B70E141A75F6518D3/252826181/252826181.jpg@s_0,w_120","name":"贵族乐团","islocate":0,"gender":"2","country":"","piao_id":"0","artist_id":"16593210","avatar_small":"http://qukufile2.qianqian.com/data2/music/1FBD613610B4C22B70E141A75F6518D3/252826181/252826181.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/music/1FBD613610B4C22B70E141A75F6518D3/252826181/252826181.jpg@s_0,w_240","albums_total":"93","songs_total":"1530"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/e0606a30358b0fc62841a7fdc46accbe/537898718/537898718.jpg@s_0,w_20","firstchar":"L","ting_uid":"247684","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/e0606a30358b0fc62841a7fdc46accbe/537898718/537898718.jpg@s_0,w_120","name":"龙飞","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"2021740","avatar_small":"http://qukufile2.qianqian.com/data2/pic/e0606a30358b0fc62841a7fdc46accbe/537898718/537898718.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/e0606a30358b0fc62841a7fdc46accbe/537898718/537898718.jpg@s_0,w_240","albums_total":"11","songs_total":"14"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246709865/246709865.jpg@s_0,w_20","firstchar":"Y","ting_uid":"1451","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246709865/246709865.jpg@s_0,w_120","name":"杨钰莹","islocate":0,"gender":"1","country":"中国","piao_id":"0","artist_id":"999","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246709865/246709865.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246709865/246709865.jpg@s_0,w_240","albums_total":"15","songs_total":"60"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/8d9ba9b72bc42e865b99296e61e07841/267938148/267938148.jpg@s_0,w_20","firstchar":"P","ting_uid":"1163","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/8d9ba9b72bc42e865b99296e61e07841/267938148/267938148.jpg@s_0,w_120","name":"品冠","islocate":0,"gender":"0","country":"马来西亚","piao_id":"0","artist_id":"268","avatar_small":"http://qukufile2.qianqian.com/data2/pic/8d9ba9b72bc42e865b99296e61e07841/267938148/267938148.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/8d9ba9b72bc42e865b99296e61e07841/267938148/267938148.jpg@s_0,w_240","albums_total":"8","songs_total":"147"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246709908/246709908.jpg@s_0,w_20","firstchar":"Z","ting_uid":"1092","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246709908/246709908.jpg@s_0,w_120","name":"张国荣","islocate":0,"gender":"0","country":"香港","piao_id":"0","artist_id":"117","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246709908/246709908.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246709908/246709908.jpg@s_0,w_240","albums_total":"65","songs_total":"1275"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/music/6E1FB3958EFF8B0EF33AE810B4092CBE/252052235/252052235.jpg@s_0,w_20","firstchar":"T","ting_uid":"1156","avatar_middle":"http://qukufile2.qianqian.com/data2/music/6E1FB3958EFF8B0EF33AE810B4092CBE/252052235/252052235.jpg@s_0,w_120","name":"田震","islocate":0,"gender":"1","country":"中国","piao_id":"0","artist_id":"253","avatar_small":"http://qukufile2.qianqian.com/data2/music/6E1FB3958EFF8B0EF33AE810B4092CBE/252052235/252052235.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/music/6E1FB3958EFF8B0EF33AE810B4092CBE/252052235/252052235.jpg@s_0,w_240","albums_total":"10","songs_total":"76"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246668004/246668004.jpg@s_0,w_20","firstchar":"K","ting_uid":"9295","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246668004/246668004.jpg@s_0,w_120","name":"筷子兄弟","islocate":0,"gender":"2","country":"中国","piao_id":"0","artist_id":"57520","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246668004/246668004.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246668004/246668004.jpg@s_0,w_240","albums_total":"5","songs_total":"7"},{"area":"1","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246667237/246667237.jpg@s_0,w_20","firstchar":"G","ting_uid":"7898","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246667237/246667237.jpg@s_0,w_120","name":"G.E.M.邓紫棋","islocate":0,"gender":"1","country":"香港","piao_id":"0","artist_id":"1814","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246667237/246667237.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246667237/246667237.jpg@s_0,w_240","albums_total":"21","songs_total":"67"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/3986b4dd7b2147804a6424447bf56f89/539814536/539814536.jpg@s_0,w_20","firstchar":"Z","ting_uid":"1378","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/3986b4dd7b2147804a6424447bf56f89/539814536/539814536.jpg@s_0,w_120","name":"郑钧","islocate":0,"gender":"0","country":"中国","piao_id":"0","artist_id":"770","avatar_small":"http://qukufile2.qianqian.com/data2/pic/3986b4dd7b2147804a6424447bf56f89/539814536/539814536.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/3986b4dd7b2147804a6424447bf56f89/539814536/539814536.jpg@s_0,w_240","albums_total":"10","songs_total":"66"},{"area":"2","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246936805/246936805.jpg@s_0,w_20","firstchar":"A","ting_uid":"172072","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246936805/246936805.jpg@s_0,w_120","name":"Adele","islocate":0,"gender":"1","country":"英国","piao_id":"0","artist_id":"1497","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246936805/246936805.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246936805/246936805.jpg@s_0,w_240","albums_total":"9","songs_total":"45"},{"area":"0","avatar_mini":"http://qukufile2.qianqian.com/data2/pic/246668381/246668381.jpg@s_0,w_20","firstchar":"Q","ting_uid":"2374","avatar_middle":"http://qukufile2.qianqian.com/data2/pic/246668381/246668381.jpg@s_0,w_120","name":"群星","islocate":0,"gender":"","country":"中国","piao_id":"0","artist_id":"313607","avatar_small":"http://qukufile2.qianqian.com/data2/pic/246668381/246668381.jpg@s_0,w_48","avatar_big":"http://qukufile2.qianqian.com/data2/pic/246668381/246668381.jpg@s_0,w_240","albums_total":"1291","songs_total":"2248"}]
     * havemore : 1
     * nums : 2000
     * noFirstChar :
     */

    private int havemore;
    private int nums;
    private String noFirstChar;
    private List<ArtistBean> artist;

    public int getHavemore() {
        return havemore;
    }

    public void setHavemore(int havemore) {
        this.havemore = havemore;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public String getNoFirstChar() {
        return noFirstChar;
    }

    public void setNoFirstChar(String noFirstChar) {
        this.noFirstChar = noFirstChar;
    }

    public List<ArtistBean> getArtist() {
        return artist;
    }

    public void setArtist(List<ArtistBean> artist) {
        this.artist = artist;
    }

    public static class ArtistBean implements Serializable{
        /**
         * area : 0
         * avatar_mini : http://qukufile2.qianqian.com/data2/pic/246669444/246669444.jpg@s_0,w_20
         * firstchar : X
         * ting_uid : 2517
         * avatar_middle : http://qukufile2.qianqian.com/data2/pic/246669444/246669444.jpg@s_0,w_120
         * name : 薛之谦
         * islocate : 0
         * gender : 0
         * country : 中国
         * piao_id : 0
         * artist_id : 88
         * avatar_small : http://qukufile2.qianqian.com/data2/pic/246669444/246669444.jpg@s_0,w_48
         * avatar_big : http://qukufile2.qianqian.com/data2/pic/246669444/246669444.jpg@s_0,w_240
         * albums_total : 14
         * songs_total : 85
         */

        private String area;
        private String avatar_mini;
        private String firstchar;
        private String ting_uid;
        private String avatar_middle;
        private String name;
        private int islocate;
        private String gender;
        private String country;
        private String piao_id;
        private String artist_id;
        private String avatar_small;
        private String avatar_big;
        private String albums_total;
        private String songs_total;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAvatar_mini() {
            return avatar_mini;
        }

        public void setAvatar_mini(String avatar_mini) {
            this.avatar_mini = avatar_mini;
        }

        public String getFirstchar() {
            return firstchar;
        }

        public void setFirstchar(String firstchar) {
            this.firstchar = firstchar;
        }

        public String getTing_uid() {
            return ting_uid;
        }

        public void setTing_uid(String ting_uid) {
            this.ting_uid = ting_uid;
        }

        public String getAvatar_middle() {
            return avatar_middle;
        }

        public void setAvatar_middle(String avatar_middle) {
            this.avatar_middle = avatar_middle;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIslocate() {
            return islocate;
        }

        public void setIslocate(int islocate) {
            this.islocate = islocate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPiao_id() {
            return piao_id;
        }

        public void setPiao_id(String piao_id) {
            this.piao_id = piao_id;
        }

        public String getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getAvatar_small() {
            return avatar_small;
        }

        public void setAvatar_small(String avatar_small) {
            this.avatar_small = avatar_small;
        }

        public String getAvatar_big() {
            return avatar_big;
        }

        public void setAvatar_big(String avatar_big) {
            this.avatar_big = avatar_big;
        }

        public String getAlbums_total() {
            return albums_total;
        }

        public void setAlbums_total(String albums_total) {
            this.albums_total = albums_total;
        }

        public String getSongs_total() {
            return songs_total;
        }

        public void setSongs_total(String songs_total) {
            this.songs_total = songs_total;
        }
    }
}
