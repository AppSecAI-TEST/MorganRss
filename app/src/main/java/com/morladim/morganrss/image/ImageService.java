package com.morladim.morganrss.image;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import com.morladim.morganrss.IImageManager;


public class ImageService extends Service {

    public static Bitmap bitmap;

    public ImageService() {
    }

    private Binder binder = new IImageManager.Stub() {


//        public void aa(Bitmap bitmap) {
//            System.out.println("11");
//        }

        public void aa(byte[] b) {

            bitmap = getBitmapBytes(b);
            System.out.println("22");
        }


        public void a(int c) {
            System.out.println("a " + c);
        }

        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                               double aDouble, String aString) {

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
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return binder;
    }

//    class B implements IImageManager.Stub{
//
//    }

}
