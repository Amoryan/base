package com.fxyan.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author fxYan
 */
public final class DisplayUtils {
    public static int getScreenWidth(Context context) {
        int width = 0;

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(metrics);
            width = metrics.widthPixels;
        }

        return width;
    }

    public static int getScreenHeight(Context context) {
        int height = 0;

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(metrics);
            height = metrics.heightPixels;
        }

        return height;
    }

    public static float dp2px(Context context, float dp) {
        float px = 0;

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(metrics);
            px = dp * metrics.density;
        }

        return px;
    }
}
