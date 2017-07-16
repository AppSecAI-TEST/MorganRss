package com.morladim.morganrss.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * <br>创建时间：2017/7/13.
 *
 * @author morladim
 */
public class RssApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public RssApplication() {
        context = this;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        context = null;

    }
}
