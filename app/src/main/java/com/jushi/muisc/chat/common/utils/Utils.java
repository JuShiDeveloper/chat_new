package com.jushi.muisc.chat.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cpx on 2018/1/27.
 */

public class Utils {

    public static DisplayMetrics getMetrics(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }

    /**
     * 保存当前日期 （年月日）
     * @param activity
     * @param date
     */
    public static void saveCurrentDate(Activity activity,String date){
        SharedPreferences sp = activity.getSharedPreferences("currentData",1);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("currentData",date);
        edit.apply();
    }


    /**
     * 每一次进入应用都判断是否是当天的日期，如果不是当天则返回false并将当天的日期保存
     * 反之返回true
     * @return
     */
    public static boolean isCurrentDate(Activity activity) {
        String lastDate = Utils.getLastSaveDate(activity);
        if (lastDate == null) {
            Utils.saveCurrentDate(activity,Utils.getCurrentDate());
            return false;
        }
        if (!lastDate.equals(Utils.getCurrentDate())){
            Utils.saveCurrentDate(activity,Utils.getCurrentDate());
            return false;
        }else {
            return true;
        }
    }

    /**
     * 获取上一次保存的日期 （年月日）
     * @param activity
     * @return
     */
    private static String getLastSaveDate(Activity activity){
        SharedPreferences sp = activity.getSharedPreferences("currentData",1);
        return sp.getString("currentData",null);
    }

    /**
     * 获得当前日期 （年月日）
     * @return
     */
    private static String getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * 圆型bitmap
     * @param bitmap
     * @return
     */
    public static Bitmap getOvalBitmap(Bitmap bitmap){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    /**
     * 圆角bitmap
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap getRectangleBitmap(Bitmap bitmap){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), 200);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRect(rectF,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static void setRecyclerViewParams(Activity activity, RecyclerView gridView){
        int width = Utils.getMetrics(activity).widthPixels;
        int height = (width / 3 + 80) * 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        params.leftMargin = 40;
        gridView.setLayoutParams(params);
    }

    public static void setArtistRecyclerViewParams(Activity activity, RecyclerView gridView){
        int width = Utils.getMetrics(activity).widthPixels;
        int height = (width / 3 + 80) * 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        params.leftMargin = 30;
        params.rightMargin = 30;
        gridView.setLayoutParams(params);
    }

    public static void setZhiBoRecyclerViewParams(Activity activity, RecyclerView gridView){
        int width = Utils.getMetrics(activity).widthPixels;
        int height = (width / 2 + 80) * 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        params.leftMargin = 38;
        gridView.setLayoutParams(params);
    }

    public static void setLatestMvRecyclerViewParams(Activity activity, RecyclerView gridView){
        int width = Utils.getMetrics(activity).widthPixels;
        int height = (int) ((width / 2.2) * 2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
//        params.leftMargin = 40;
        gridView.setLayoutParams(params);
    }

    public static void setArtistImageViewParams(Context context, ImageView imageView){
        int screenWidth = getMetrics((Activity) context).widthPixels;
        int width = screenWidth / 3 - 60;
        int imageHeight = width;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(width,imageHeight);
        params1.setMargins(0,25,0,10);
        imageView.setLayoutParams(params1);
    }

    public static void setRecommendImageLayoutParams(Context context, LinearLayout layout){
        int screenWidth = getMetrics((Activity) context).widthPixels;
        int width = screenWidth / 3 - 60;
        int imageHeight = width;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(width,imageHeight);
        params1.setMargins(0,25,0,10);
        layout.setLayoutParams(params1);
    }

    public static void setZhiBoImageLayoutParams(Context context, RelativeLayout layout){
        int screenWidth = getMetrics((Activity) context).widthPixels;
        int width = screenWidth / 2 - 60;
        int imageHeight = width;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(width,imageHeight);
        params1.setMargins(0,25,0,10);
        layout.setLayoutParams(params1);
    }

    public static void setLatestImageViewParams(Context context, ImageView imageView){
        int screenWidth = getMetrics((Activity) context).widthPixels;
        int width = screenWidth / 2 - 60;
        int imageHeight = (int) (width / 1.6);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,imageHeight);
        imageView.setLayoutParams(params1);
    }

    public static void setLatestLinerParams(Context context, View view){
        int screenWidth = getMetrics((Activity) context).widthPixels;
        int width = screenWidth / 2 - 60;
        int imageHeight = (int) (width / 1.1);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,imageHeight);
        params1.setMargins(0,25,38,10);
        view.setLayoutParams(params1);
    }


    public static void setOvalImageViewParams(Context context, ImageView imageView){
        int screenWidth = getMetrics((Activity) context).widthPixels;
        int width = screenWidth / 3 - 100;
        int imageHeight = width;
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width,imageHeight);
        params1.setMargins(0,40,0,15);
        imageView.setLayoutParams(params1);
    }

    /**
     * 获得位图的背景颜色
     * @param bitmap
     * @return   ImageView
     */
    public static int getARGB(Bitmap bitmap) {
        //获得bitmap的高
        int height = bitmap.getHeight();
        //获得bitmap的宽
        int width = bitmap.getWidth();
        int A = 0, R = 0, G = 0, B = 0;
        int pixelColor;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //获得bitmap的像素值
                pixelColor = bitmap.getPixel(x, y);
                //获得ARGB中的A值(透明度值)
                A = Color.alpha(pixelColor);
                //获得ARGB中的R值(红色值)
                R = Color.red(pixelColor);
                //获得ARGB中的G值(绿色值)
                G = Color.green(pixelColor);
                //获得ARGB中的B值(蓝色值)
                B = Color.blue(pixelColor);
            }
        }
        //将获得的ARGB值转为一个颜色值
        int backgroundColor = Color.argb(A, R, G, B);

        return backgroundColor;
    }
}
