package com.morladim.morganrss.image;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.morladim.morganrss.R;
import com.morladim.morganrss.base.RssApplication;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

/**
 * <br>创建时间：2017/8/10.
 *
 * @author morladim
 */
public class SingleTouchImageViewActivity extends Activity {

    /**
     * 传递图片地址用名称
     */
    public static final String IMAGE_URL = "imageUrl";

    /**
     * 传递图片字节数组
     */
    public static final String IMAGE_BYTE_ARRAY = "imageBytes";

    /**
     * 共享元素名称
     */
    public static final String SHARE_IMAGE_VIEW = "sharedImageView";

    public static void startActivityByBitmap(ImageView view, String imageUrl) {
        Context context = view.getContext();
        Intent postIntent = new Intent(context, SingleTouchImageViewActivity.class);
        postIntent.putExtra(SingleTouchImageViewActivity.IMAGE_URL, imageUrl);
        postIntent.putExtra(SingleTouchImageViewActivity.IMAGE_BYTE_ARRAY, imageViewToBytes(view));
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(((Activity) context), view, SingleTouchImageViewActivity.SHARE_IMAGE_VIEW);
        context.startActivity(postIntent, transitionActivityOptions.toBundle());
    }

    public static void startActivityByBi(String imageUrl, ImageView view) {
        Context context = view.getContext();
        Intent postIntent = new Intent(context, SingleTouchImageViewActivity.class);
        postIntent.putExtra(SingleTouchImageViewActivity.IMAGE_URL, imageUrl);
        postIntent.putExtra(SingleTouchImageViewActivity.IMAGE_BYTE_ARRAY, new byte[1]);
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(((Activity) context), view, SingleTouchImageViewActivity.SHARE_IMAGE_VIEW);
        context.startActivity(postIntent, transitionActivityOptions.toBundle());
    }

    private static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private static byte[] imageViewToBytes(ImageView view) {
        return Bitmap2Bytes(((BitmapDrawable) (view).getDrawable()).getBitmap());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_touch_image_view);
        getWindow().setStatusBarColor(ContextCompat.getColor(RssApplication.getContext(), R.color.colorPrimaryDark));
        final TouchImageView image = findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.resetZoom();
                onBackPressed();
            }
        });
        final ColorDrawable imgDefaultDrawable = new ColorDrawable();
        imgDefaultDrawable.setColor(ContextCompat.getColor(this, R.color.transparent));

        if (ImageService.bitmap != null) {
            image.setImageBitmap(ImageService.bitmap);
        }
//        Bitmap bitmap = getBitmapBytes();
//        if (bitmap != null) {
//            image.setImageBitmap(bitmap);
//        } else {
//            image.setImageDrawable(imgDefaultDrawable);
//        }

        getWindow().getEnterTransition().setDuration(500);
        getWindow().getSharedElementEnterTransition().addListener(
                new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onTransitionEnd(Transition transition) {
                        getWindow().getSharedElementEnterTransition().removeListener(this);
                        Bitmap bitmap = ImageService.bitmap;
//                        Bitmap bitmap = getBitmapBytes();
                        final String imageUrl = getIntent().getStringExtra(IMAGE_URL);
                        if (bitmap == null) {
//                            ImageLoader.showImage(imageUrl, image, 2, null);
                            Picasso.with(RssApplication.getContext()).load(imageUrl).config(Bitmap.Config.RGB_565).into(image);
                        } else {
//                            ImageLoader.showImage(imageUrl, image, bitmap);
//                            image.setImageBitmap(bitmap);
                            Picasso.with(RssApplication.getContext()).load(imageUrl).placeholder(new BitmapDrawable(image.getResources(), bitmap)).config(Bitmap.Config.RGB_565).into(image);

                        }
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {

                    }

                    @Override
                    public void onTransitionPause(Transition transition) {

                    }

                    @Override
                    public void onTransitionResume(Transition transition) {

                    }
                });
    }

    private Bitmap getBitmapBytes() {
        byte[] buff = getIntent().getByteArrayExtra(IMAGE_BYTE_ARRAY);
        if (buff == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(buff, 0, buff.length);
    }

//    /**
//     * 设置页面显示图片
//     *
//     * @param imageView 目标
//     */
//    private void setTouchImageView(final TouchImageView imageView) {
//        final String imageUrl = getIntent().getStringExtra(IMAGE_URL);
//        imageView.post(new Runnable() {
//            @Override
//            public void run() {
//                Picasso.with(RssApplication.getContext()).load(imageUrl).config(Bitmap.Config.RGB_565).into(imageView);
////                ImageLoader.showImage(imageUrl, imageView, 2, null);
//            }
//        });
//    }
}
