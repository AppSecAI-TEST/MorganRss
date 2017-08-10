package com.morladim.morganrss.base.util;

import android.content.Context;

/**
 * <br>创建时间：2017/8/10.
 *
 * @author morladim
 */
public class ScreenUtils {

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
