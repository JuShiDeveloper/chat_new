package com.jushi.muisc.chat.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by paocai on 2018/4/23.
 */

public class DateUtils {

    //获得当前日期
    public static String getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    //获得当前时间
    public static String getCurrentTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    //时间戳转换为日期
    public static String timeToDate(long time){
        time = time*1000;
        SimpleDateFormat format =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String d = format.format(time);
        return d;
    }

    /**
     * 日期转为时间戳
     * @return
     */
    public static long dateToTime(String date){
        String[] times = date.substring(0,5).split(":");
        long dateTime = Long.parseLong(times[0]) * 3600  + Long.parseLong(times[1])* 60;
        return dateTime;
    }

    //根据日期获得一周中对应的一天
    @SuppressLint("WrongConstant")
    public static String dateToWeek(String dateTime,Context context){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        String[] week = {context.getResources().getString(R.string.Sunday), context.getResources().getString(R.string.Monday),
//                context.getResources().getString(R.string.Tuesday),context.getResources().getString(R.string.Wednesday),
//                context.getResources().getString(R.string.Thursday),context.getResources().getString(R.string.Friday),
//                context.getResources().getString(R.string.Saturday)};
//        Calendar cal = Calendar.getInstance();
//        Date date = null;
//        try {
//            date = sdf.parse(dateTime);
//            cal.setTime(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
//        if (w < 0)
//            w = 0;
//        return week[w];
        return "";
    }

    /**
     * 时间转换，将时间戳转为 00：00的形式
     * 用于歌曲时间显示
     *
     * @param duration
     * @return
     */
    public static String getGenTimeMS(long duration){
        long timef = (duration / 1000) / 60;
        long time_s = (duration / 1000) % 60;
        String allTime = "";
        if (time_s < 10){
            allTime = timef + ":0"+time_s;
        }else {
            allTime = timef + ":"+time_s;
        }
        return allTime;
    }
}
