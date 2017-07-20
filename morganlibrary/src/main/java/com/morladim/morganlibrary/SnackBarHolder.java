package com.morladim.morganlibrary;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import static android.support.design.widget.Snackbar.SnackbarLayout;

/**
 * 有颜色的snakebar持有者
 * <br>创建时间：2017/7/19.
 *
 * @author morladim
 */
public enum SnackBarHolder {

    INFO(R.color.snake_bar_bg_info, R.color.snake_bar_text_info, R.drawable.ic_highlight_off_black_24dp),
    WARNING(R.color.snake_bar_bg_warn, R.color.snake_bar_text_warn, R.drawable.ic_priority_high_black_24dp),
    ERROR(R.color.snake_bar_bg_error, R.color.snake_bar_text_error, R.drawable.ic_error_outline_black_24dp),
    SUCCESS(R.color.snake_bar_bg_success, R.color.snake_bar_text_success, R.drawable.ic_done_black_24dp);

    private int bgColorId, textColorId, iconId;

    SnackBarHolder(@ColorRes int bgColorId, @ColorRes int textColorId, @DrawableRes int iconId) {
        this.bgColorId = bgColorId;
        this.textColorId = textColorId;
        this.iconId = iconId;
    }

    public Snackbar getNew(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        SnackbarLayout snackbarLayout = (SnackbarLayout) snackbar.getView();
        snackbarLayout.setBackgroundColor(bgColorId);
        TextView textView = snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(textColorId);
        Drawable drawable = view.getResources().getDrawable(iconId);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        textView.setCompoundDrawablePadding(40);
        return snackbar;
    }
}
