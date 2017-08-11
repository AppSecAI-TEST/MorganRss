package com.morladim.morganrss.base.util;

import android.content.Context;

/**
 * 设备工具类
 * <br>创建时间：2017/8/10.
 *
 * @author morladim
 */
@SuppressWarnings("unused")
public class DeviceUtils {

    private DeviceUtils() {

    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
