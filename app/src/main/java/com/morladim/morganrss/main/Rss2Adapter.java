package com.morladim.morganrss.main;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.morladim.morganrss.IImageManager;
import com.morladim.morganrss.R;
import com.morladim.morganrss.base.RssApplication;
import com.morladim.morganrss.base.util.DateUtils;
import com.morladim.morganrss.base.util.ImageLoader;
import com.morladim.morganrss.base.util.ImageUtils;
import com.morladim.morganrss.base.database.entity.Item;
import com.morladim.morganrss.base.image.SingleTouchImageViewActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
            String description = item.getDescription().replaceAll("<img.+?>", "");
//            description = description.replaceAll("<(?<style>[^\\s>]+)[^>]*>(.|\\n)*?</\\k<style>>", "");
            // TODO: 2017/8/9 适配好奇心日报
            while (description.startsWith("\n")) {
                description = description.substring(1);
            }

            Spanned spanned = Html.fromHtml(description, imageGetter, null);

            rss2VerticalViewHolder.description.setText(spanned);
            rss2VerticalViewHolder.creator.setText(item.getCreator());
            rss2VerticalViewHolder.date.setText(DateUtils.getTimeToNow(item.getPubDate()));

            ImageLoader.load(item.getImageUrl()).transform(transformation).into(rss2VerticalViewHolder.imageView);
            rss2VerticalViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (manager != null) {
                        Observable.just(rss2VerticalViewHolder.imageView)
                                .subscribeOn(Schedulers.io())
                                .map(new Function<ImageView, byte[]>() {

                                    @Override
                                    public byte[] apply(@NonNull ImageView imageView) throws Exception {
                                        return ImageUtils.imageViewToBytes(imageView);
                                    }
                                }).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<byte[]>() {
                                    @Override
                                    public void accept(@NonNull byte[] bytes) throws Exception {
                                        try {
                                            manager.setBitmap(bytes);
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                        SingleTouchImageViewActivity.startActivityBySceneTrans(item.getImageUrl(), rss2VerticalViewHolder.imageView);
                                    }
                                });
                    }
                }
            });
        }
    }

    private final Html.ImageGetter imageGetter = new Html.ImageGetter() {

        @Override
        public Drawable getDrawable(String s) {
            return null;
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
            if (source.getWidth() <= imageWidth) {
                return source;
            }

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
