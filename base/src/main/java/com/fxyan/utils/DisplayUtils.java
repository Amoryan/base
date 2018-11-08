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

    public static int dp2px(Context context, float dp) {
        int px = 0;

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(metrics);
            px = (int) (dp * metrics.density);
        }

        return px;
    }

    public static int getStatusBarHeight(Context context) {
        int result = dp2px(context, 25);

        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }
}
