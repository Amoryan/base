package com.fxyan.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.fxyan.base.R;
import com.fxyan.utils.DisplayUtils;

/**
 * @author fxYan
 */
public final class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int divider;

    public DividerItemDecoration(Context context) {
        divider = DisplayUtils.dp2px(context, 1);
        paint.setColor(ContextCompat.getColor(context, R.color.color_e5e5e5));
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            if (childAdapterPosition != 0) {
                c.drawLine(view.getLeft(), view.getTop(), view.getRight(), view.getTop() + divider, paint);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int childAdapterPosition = parent.getChildAdapterPosition(view);

        if (childAdapterPosition != 0) {
            outRect.top = divider;
        }
    }
}
