package com.morladim.morganrss.base.util;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;

/**
 * <br>创建时间：2017/8/10.
 *
 * @author morladim
 */
public class ImageHolder {

    private WeakReference<Bitmap> bitmap;

    public void save(Bitmap bitmap) {
        this.bitmap = new WeakReference<>(bitmap);
    }

    public Bitmap load() {
        if (bitmap == null) {
            return null;
        }
        return bitmap.get();
    }

//    public
}
