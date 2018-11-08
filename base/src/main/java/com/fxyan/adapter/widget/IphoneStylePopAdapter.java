package com.fxyan.adapter.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.fxyan.adapter.BaseRecyclerAdapter;
import com.fxyan.base.R;

/**
 * @author fxYan
 */
public final class IphoneStylePopAdapter extends BaseRecyclerAdapter<String, IphoneStylePopAdapter.ViewHolder> {

    public IphoneStylePopAdapter(@NonNull Context context) {
        super(context, R.layout.listitem_iphone_style_pop);
    }

    @Override
    protected ViewHolder createItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindData(@NonNull ViewHolder h, String obj, int position) {
        h.textView.setText(obj);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
