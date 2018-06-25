package com.jushi.muisc.chat.utils;

import android.content.Context;
import android.view.View;

import com.aos.shadowlib.drawable.ShadowDrawable;
import com.aos.shadowlib.utils.DensityUtil;
import com.jushi.muisc.chat.R;

/**
 * Created by cpx on 2018/1/2.
 * 设置背景阴影效果
 */

public class ShadowUtils {

    /**
     * 阴影下偏移
     * @param context
     * @param view
     */
    public static void setShadowDown(Context context, View view){
        ShadowDrawable shadowDrawable = new ShadowDrawable();
        shadowDrawable.setColor(R.color.colorShadow);      //阴影颜色
        shadowDrawable.setOffsetY(DensityUtil.px2dip(context,25));   //阴影下偏移
        shadowDrawable.setRadius(DensityUtil.px2dip(context,60));     //四角半径
        shadowDrawable.setEdgeShadowWidth(DensityUtil.px2dip(context,20));  //四周阴影半径
        shadowDrawable.setFilterColor(0x3fffffff);       //中间值，越接近0xffffffff越接近设置的颜色值
        shadowDrawable.setTopMargin(DensityUtil.px2dip(context,5));  //上间距
        shadowDrawable.setParentHeight(view.getHeight());   //设置阴影要依赖的view的高度
        shadowDrawable.attach(view);      //在哪一个view上加阴影
        shadowDrawable.build();     //显示
    }

    /**
     * 阴影上偏移
     * @param context
     * @param view
     */
    public static void setShadowUp(Context context,View view){
        ShadowDrawable shadowDrawable = new ShadowDrawable();
        shadowDrawable.setColor(R.color.colorShadow);      //阴影颜色
        shadowDrawable.setOffsetY(DensityUtil.px2dip(context,35));   //阴影下偏移
        shadowDrawable.setRadius(0);    //四角半径
        shadowDrawable.setEdgeShadowWidth(DensityUtil.px2dip(context,25));  //四周阴影半径
        shadowDrawable.setFilterColor(0x3fffffff);       //中间值，越接近0xffffffff越接近设置的颜色值
        shadowDrawable.setTopMargin(DensityUtil.px2dip(context,5));  //上间距
        shadowDrawable.setParentHeight(view.getHeight());   //设置阴影要依赖的view的高度
        shadowDrawable.attach(view);      //在哪一个view上加阴影
        shadowDrawable.build();     //显示
    }

    /**
     * 阴影下偏移
     * @param context
     * @param view
     */
    public static void setShadowDown_2(Context context, View view){
        ShadowDrawable shadowDrawable = new ShadowDrawable();
        shadowDrawable.setColor(R.color.colorShadow);      //阴影颜色
        shadowDrawable.setOffsetY(DensityUtil.px2dip(context,15));   //阴影下偏移
        shadowDrawable.setRadius(0);     //四角半径
        shadowDrawable.setEdgeShadowWidth(DensityUtil.px2dip(context,5));  //四周阴影半径
        shadowDrawable.setFilterColor(0x0fffffff);       //中间值，越接近0xffffffff越接近设置的颜色值
        shadowDrawable.setTopMargin(DensityUtil.px2dip(context,1));  //上间距
        shadowDrawable.setParentHeight(view.getHeight());   //设置阴影要依赖的view的高度
        shadowDrawable.attach(view);      //在哪一个view上加阴影
        shadowDrawable.build();     //显示
    }
}
