package com.morladim.morganrss;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.morladim.morganrss.base.RssApplication;
import com.morladim.morganrss.base.util.DateUtils;
import com.morladim.morganrss.database.entity.Item;
import com.morladim.morganrss.image.SingleTouchImageViewActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>创建时间：2017/7/24.
 *
 * @author morladim
 */
public class Rss2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private volatile int offset;

    private int limit = 10;

    private List<Item> data;

    private boolean hasMore = true;

    private ImageTransformation transformation;

    private IImageManager manager;

    public Rss2Adapter(int width) {
        transformation = new ImageTransformation(width);
//        this.manager = manager;
        data = new ArrayList<>();
    }


    public void setManager(IImageManager manager) {
        this.manager = manager;
    }

    /**
     * 图片在侧边
     */
    public static final int DEFAULT_ITEM_VIEW_TYPE = 0;

    /**
     * 图片在上边
     */
    public static final int IMAGE_TOP_VIEW_TYPE = 1;

    /**
     * 没有图片
     */
    public static final int NO_IMAGE_VIEW_TYPE = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case IMAGE_TOP_VIEW_TYPE:
                layout = R.layout.vertical_item;
                break;
            case NO_IMAGE_VIEW_TYPE:
                layout = R.layout.no_image_item;
                break;
            default:
                layout = R.layout.item;
                break;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new Rss2VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            final Rss2VerticalViewHolder rss2VerticalViewHolder = (Rss2VerticalViewHolder) holder;
            final Item item = data.get(position);
            rss2VerticalViewHolder.title.setText(item.getTitle());
//            Spanned spanned = Html.fromHtml(item.getDescription(), imageGetter, null);
            String description = item.getDescription().replaceAll("<img.+?>", "");
//            description = description.replaceAll("<(?<style>[^\\s>]+)[^>]*>(.|\\n)*?</\\k<style>>", "");
            // TODO: 2017/8/9 适配好奇心日报
            while (description.startsWith("\n")) {
                description = description.substring(1);
            }

            Spanned spanned = Html.fromHtml(description, imageGetter, null);

//            if (spanned instanceof SpannableStringBuilder) {
//                ImageSpan[] imageSpans = spanned.getSpans(0, spanned.length(), ImageSpan.class);
//                for (ImageSpan imageSpan : imageSpans) {
////                    int start = spanned.getSpanStart(imageSpan);
////                    int end = spanned.getSpanEnd(imageSpan);
////                    Drawable d = imageSpan.getDrawable();
//////                    ImageSpan newImageSpan = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
////                    ((SpannableStringBuilder) spanned).setSpan(null, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                    ((SpannableStringBuilder) spanned).removeSpan(imageSpan);
//                    System.out.println("cccc " + spanned);
//
//                }
//            }
//            rss2VerticalViewHolder.description.setText(spanned);
            rss2VerticalViewHolder.description.setText(spanned);
            rss2VerticalViewHolder.creator.setText(item.getCreator());
            rss2VerticalViewHolder.date.setText(DateUtils.getTimeToNow(item.getPubDate()));

//            Target target = new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                }
//
//                @Override
//                public void onBitmapFailed(Drawable errorDrawable) {
//
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                }
//            };

            System.out.println("===========");

            rss2VerticalViewHolder.description.measure(0, 0);
            rss2VerticalViewHolder.title.measure(0, 0);
            System.out.println("rss2VerticalViewHolder.description " + rss2VerticalViewHolder.description.getWidth());
            System.out.println("rss2VerticalViewHolder.description " + rss2VerticalViewHolder.description.getMeasuredWidth());
            System.out.println("rss2VerticalViewHolder.title " + rss2VerticalViewHolder.title.getWidth());
            System.out.println("rss2VerticalViewHolder.title " + rss2VerticalViewHolder.title.getMeasuredWidth());
            System.out.println("===========");

//            Transformation transformation = new Transformation() {
//
//                @Override
//                public Bitmap transform(Bitmap source) {
//                    int targetWidth = ((ViewGroup) rss2VerticalViewHolder.imageView.getParent()).getWidth();
//
////                    if (targetWidth == 0) {
////                        rss2VerticalViewHolder.imageView.measure(0, 0);
////                    }
////                    targetWidth = rss2VerticalViewHolder.imageView.getMeasuredWidth();
//                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
//                    int targetHeight = (int) (targetWidth * aspectRatio);
//                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
//                    if (result != source) {
//                        source.recycle();
//                    }
//                    return result;
//                }
//
//                @Override
//                public String key() {
//                    return "transformation" + " desiredWidth";
//                }
//            };

            Picasso.with(RssApplication.getContext()).load(item.getImageUrl()).config(Bitmap.Config.RGB_565).transform(transformation).into(rss2VerticalViewHolder.imageView, new Callback() {
                @Override
                public void onSuccess() {
                    System.out.println("imageView " + rss2VerticalViewHolder.imageView.getWidth());
                    System.out.println("imageView " + rss2VerticalViewHolder.imageView.getHeight());
//                    Picasso.with(RssApplication.getContext()).load(item.getImageUrl())
//                            .resize(rss2VerticalViewHolder.imageView.getWidth(), rss2VerticalViewHolder.imageView.getHeight())
//                            .placeholder(rss2VerticalViewHolder.imageView.getDrawable()).config(Bitmap.Config.RGB_565).into(rss2VerticalViewHolder.imageView);
                }

                @Override
                public void onError() {

                }
            });
            rss2VerticalViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (manager != null) {
                        try {
                            manager.aa(imageViewToBytes(rss2VerticalViewHolder.imageView));
//                            SingleTouchImageViewActivity.startActivityByBitmap(rss2VerticalViewHolder.imageView,item.getImageUrl());
                            SingleTouchImageViewActivity.startActivityByBi(item.getImageUrl(),rss2VerticalViewHolder.imageView);
//                            manager.aa(null);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
//                    Intent intent = new Intent(rss2VerticalViewHolder.imageView.getContext(), ImageService.class);
//                    rss2VerticalViewHolder.imageView.getContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);

//                    SingleTouchImageViewActivity.startActivityByBi(rss2VerticalViewHolder.imageView, item.getImageUrl());
                }
            });
        }
    }

    private static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private static byte[] imageViewToBytes(ImageView view) {
        return Bitmap2Bytes(((BitmapDrawable) (view).getDrawable()).getBitmap());
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IImageManager manager = IImageManager.Stub.asInterface(iBinder);
            try {
                manager.a(45);
//                manager.aa(BitmapFactory.decodeByteArray(new byte[1],0,1));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

//    public class StickerSpan extends ImageSpan {
//
//        public StickerSpan(Drawable b, int verticalAlignment) {
//            super(b, verticalAlignment);
//
//        }
//
//        @Override
//        public void draw(Canvas canvas, CharSequence text,
//                         int start, int end, float x,
//                         int top, int y, int bottom, Paint paint) {
//            Drawable b = getDrawable();
//            canvas.save();
//            int transY = bottom - b.getBounds().bottom - Utils.dip2px(WApplication.cFontLineSpacingExtra);
//            if (mVerticalAlignment == ALIGN_BASELINE) {
//                int textLength = text.length();
//                for (int i = 0; i < textLength; i++) {
//                    if (Character.isLetterOrDigit(text.charAt(i))) {
//                        transY -= paint.getFontMetricsInt().descent;
//                        break;
//                    }
//                }
//            }
//            canvas.translate(x, transY);
//            b.draw(canvas);
//            canvas.restore();
//        }
//    }

    final Html.ImageGetter imageGetter = new Html.ImageGetter() {

        @Override
        public Drawable getDrawable(String s) {
            return null;
//            return new BitmapDrawable();
        }
    };

    @Override
    public int getItemViewType(int position) {
        Item item = data.get(position);
        if (item.getImageUrl() != null) {
            try {
                if (item.getImageWidth() == null || item.getImageHeight() == null) {
                    return IMAGE_TOP_VIEW_TYPE;
                }
                int width = Integer.parseInt(item.getImageWidth());
                int height = Integer.parseInt(item.getImageHeight());
                System.out.println("width " + width);
                System.out.println("height " + height);
                System.out.println("------------------");
                if (width > height * 1.25f) {
                    return IMAGE_TOP_VIEW_TYPE;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return DEFAULT_ITEM_VIEW_TYPE;
        }
        return NO_IMAGE_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void refresh(List<Item> items) {
        offset = 0;
        data.clear();
        data.addAll(items);
        this.notifyDataSetChanged();
    }

    public void loadMore(List<Item> items) {
        data.addAll(items);
        this.notifyDataSetChanged();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    private static class Rss2VerticalViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, date, creator;
        ImageView imageView;

        Rss2VerticalViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image);
            date = itemView.findViewById(R.id.date);
            creator = itemView.findViewById(R.id.creator);
        }
    }

    private static class ImageTransformation implements Transformation {

        private int imageWidth;

        ImageTransformation(int width) {
            this.imageWidth = width;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (imageWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, imageWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            System.out.println("tar w " + imageWidth);
            System.out.println("tar h " + targetHeight);
            return result;
        }

        @Override
        public String key() {
            return "imageWidth " + imageWidth;
        }
    }
//    private static class Rss2HorizontalViewHolder extends RecyclerView.ViewHolder {
//
//        TextView title, description, date, creator;
//        ImageView imageView;
//
//        Rss2HorizontalViewHolder(View itemView) {
//            super(itemView);
//            title = itemView.findViewById(R.id.title);
//            description = itemView.findViewById(R.id.description);
//            imageView = itemView.findViewById(R.id.image);
//            date = itemView.findViewById(R.id.date);
//            creator = itemView.findViewById(R.id.creator);
//        }
//    }
}
