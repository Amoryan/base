package com.fxyan.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.fxyan.adapter.BaseRecyclerAdapter;
import com.fxyan.adapter.pop.DividerItemDecoration;
import com.fxyan.adapter.pop.IphonePopWindowAdapter;
import com.fxyan.base.R;
import com.fxyan.utils.DisplayUtils;

import java.util.List;

/**
 * @author fxYan
 */
public final class IphonePopWindow extends PopupWindow implements View.OnClickListener {

    private View contentView;

    private IphonePopWindowAdapter adapter;

    private Animation enterAnim;
    private Animation exitAnim;

    public IphonePopWindow(final Context context) {
        super(context);

        contentView = LayoutInflater.from(context).inflate(R.layout.layout_iphone_pop, null);

        contentView.findViewById(R.id.outside).setOnClickListener(this);
        contentView.findViewById(R.id.cancelTv).setOnClickListener(this);

        // max height
        View sizeView = contentView.findViewById(R.id.sizeFl);
        ViewGroup.LayoutParams lp = sizeView.getLayoutParams();
        lp.height = DisplayUtils.getScreenHeight(context) / 2;

        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new IphonePopWindowAdapter(context);
        recyclerView.addItemDecoration(new DividerItemDecoration(context));
        recyclerView.setAdapter(adapter);

        setContentView(contentView);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));

        initAnim(context);
    }

    private void initAnim(Context context) {
        enterAnim = AnimationUtils.loadAnimation(context, R.anim.translate_iphone_pop_enter);
        exitAnim = AnimationUtils.loadAnimation(context, R.anim.translate_iphone_pop_exit);

        exitAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                IphonePopWindow.super.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void show(View view) {
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
        contentView.setAnimation(enterAnim);
        contentView.startAnimation(enterAnim);
    }

    public void show(View view, List<String> data) {
        adapter.clearDataSource();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();

        show(view);
    }

    @Override
    public void dismiss() {
        contentView.setAnimation(exitAnim);
        contentView.startAnimation(exitAnim);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.outside || id == R.id.cancelTv) {
            dismiss();
        }
    }

    public void setOnItemClickListener(final BaseRecyclerAdapter.OnItemClickListener<String> listener) {
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClicked(String obj, int position) {
                if (listener != null) {
                    listener.onItemClicked(obj, position);
                }
                dismiss();
            }
        });
    }
}
