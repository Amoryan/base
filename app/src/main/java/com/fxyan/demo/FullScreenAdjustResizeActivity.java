package com.fxyan.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.gyf.barlibrary.ImmersionBar;

/**
 * @author fxYan
 */
public final class FullScreenAdjustResizeActivity extends AppCompatActivity {

    private FrameLayout contentFl;
    private ViewGroup.LayoutParams lp;
    private Rect rect = new Rect();
    private int visibleHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_adjust_resize);

        ImmersionBar.with(this)
                .statusBarColor(android.R.color.transparent)
                .statusBarDarkFont(true)
                .init();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        contentFl = findViewById(android.R.id.content);
        lp = contentFl.getLayoutParams();
        contentFl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                contentFl.getWindowVisibleDisplayFrame(rect);
                Log.d("fxYan", "" + rect.toString());
                if (visibleHeight == 0) {
                    Log.d("fxYan", "visibleHeight is 0");
                    visibleHeight = rect.bottom;
                } else if (visibleHeight - rect.bottom > 200) {
                    visibleHeight = rect.bottom;
                    Log.d("fxYan", "keyboard show");
                    lp.height = visibleHeight;
                    contentFl.requestLayout();
                } else if (rect.bottom - visibleHeight > 200) {
                    visibleHeight = rect.bottom;
                    Log.d("fxYan", "keyboard hidden");
                    lp.height = visibleHeight;
                    contentFl.requestLayout();
                }
            }
        });
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, FullScreenAdjustResizeActivity.class);
        context.startActivity(intent);
    }
}
