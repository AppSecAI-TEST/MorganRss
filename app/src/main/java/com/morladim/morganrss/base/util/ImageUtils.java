package com.morladim.morganrss.base.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * 图片工具类
 * <br>创建时间：2017/8/11.
 *
 * @author morladim
 */
@SuppressWarnings("WeakerAccess")
public class ImageUtils {

    private ImageUtils() {

    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static byte[] imageViewToBytes(ImageView view) {
        return Bitmap2Bytes(((BitmapDrawable) view.getDrawable()).getBitmap());
    }
}
