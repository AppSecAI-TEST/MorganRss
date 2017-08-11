package com.morladim.morganrss.base.image;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import com.morladim.morganrss.IImageManager;

/**
 * picture线程接受图片service，保存到单例供SingleTouchImageViewActivity使用。
 */
public class ImageService extends Service {

    public ImageService() {
    }

    private Binder binder = new IImageManager.Stub() {

        public void setBitmap(byte[] b) {
            ImageHolder.getInstance().save(getBitmapBytes(b));
        }

    };

    private Bitmap getBitmapBytes(byte[] buff) {
        if (buff == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(buff, 0, buff.length);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}
