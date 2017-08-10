package com.morladim.morganrss.base.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.morladim.morganrss.R;

import static android.support.design.widget.Snackbar.SnackbarLayout;

/**
 * 有颜色的snakebar持有者
 * <br>创建时间：2017/7/19.
 *
 * @author morladim
 */
@SuppressWarnings("unused")
public enum SnackbarHolder {

    INFO(R.color.snackbarBackgroundInfo, R.color.snackbarTextInfo, R.drawable.ic_error_outline_black_24dp, null),
    WARNING(R.color.snackbarBackgroundWarn, R.color.snackbarTextWarn, R.drawable.ic_priority_high_black_24dp, null),
    ERROR(R.color.snackbarBackgroundError, R.color.snackbarTextError, R.drawable.ic_highlight_off_black_24dp, "遇到错误"),
    SUCCESS(R.color.snackbarBackgroundSuccess, R.color.snackbarTextSuccess, R.drawable.ic_done_black_24dp, "操作成功");

    private int bgColorId, textColorId, iconId;
    private String text;

    SnackbarHolder(@ColorRes int bgColorId, @ColorRes int textColorId, @DrawableRes int iconId, String text) {
        this.bgColorId = bgColorId;
        this.textColorId = textColorId;
        this.iconId = iconId;
        this.text = text;
    }

    public Snackbar getNew(View view, String text) {
        if (text == null) {
            text = this.text;
        }
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        SnackbarLayout snackbarLayout = (SnackbarLayout) snackbar.getView();
        snackbarLayout.setBackgroundColor(bgColorId);
        TextView textView = snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(textColorId);
        Drawable drawable = view.getResources().getDrawable(iconId, view.getContext().getTheme());
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        textView.setCompoundDrawablePadding(40);
        return snackbar;
    }

    public Snackbar getNew(View view) {
        return getNew(view, text);
    }
}
