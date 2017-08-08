package com.morladim.morganrss;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.morladim.morganlibrary.DateUtils;
import com.morladim.morganrss.base.RssApplication;
import com.morladim.morganrss.database.entity.Item;
import com.squareup.picasso.Picasso;

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

    public Rss2Adapter() {
        data = new ArrayList<>();
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
            Rss2VerticalViewHolder rss2VerticalViewHolder = (Rss2VerticalViewHolder) holder;
            Item item = data.get(position);
            rss2VerticalViewHolder.title.setText(item.getTitle());
//            Spanned spanned = Html.fromHtml(item.getDescription(), imageGetter, null);
            String description = item.getDescription().replaceAll("<img.+?>", "");
            while (description.startsWith("\n")) {
                description = description.substring(2);
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
            Picasso.with(RssApplication.getContext()).load(item.getImageUrl()).config(Bitmap.Config.RGB_565).into(rss2VerticalViewHolder.imageView);
        }
    }

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
//            return null;
            return new BitmapDrawable();
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
