package com.fxyan.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.fxyan.base.R;

/**
 * @author fxYan
 */
@SuppressWarnings("ALL")
public final class IphoneStyleDialog extends Dialog {

    private TextView titleTv;
    private TextView contentTv;
    private TextView negativeTv;
    private View divider;
    private TextView positiveTv;

    public IphoneStyleDialog(Context context) {
        super(context, R.style.IphoneStyleDialog);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_iphone_style_dialog, null);
        titleTv = root.findViewById(R.id.titleTv);
        contentTv = root.findViewById(R.id.contentTv);
        negativeTv = root.findViewById(R.id.negativeTv);
        divider = root.findViewById(R.id.divider);
        positiveTv = root.findViewById(R.id.positiveTv);

        setContentView(root);
    }

    public static class Builder {
        private Context context;

        private String title;

        private String content;
        private String contentColor;

        private String negativeText;
        private String negativeColor;
        private View.OnClickListener negativeListener;

        private String positionText;
        private String positionColor;
        private View.OnClickListener positionListener;

        private boolean isCancelable;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            return setContent(content, null);
        }

        public Builder setContent(String content, String color) {
            this.content = content;
            this.contentColor = color;
            return this;
        }

        public Builder setContentColor(String color) {
            this.contentColor = color;
            return this;
        }

        public Builder setNegativeButton(String text, View.OnClickListener listener) {
            return setNegativeButton(text, null, listener);
        }

        public Builder setNegativeButton(String text, String color, View.OnClickListener listener) {
            this.negativeText = text;
            this.negativeColor = color;
            this.negativeListener = listener;
            return this;
        }

        public Builder setPositiveButton(String text, View.OnClickListener listener) {
            return setPositiveButton(text, null, listener);
        }

        public Builder setPositiveButton(String text, String color, View.OnClickListener listener) {
            this.positionText = text;
            this.positionColor = color;
            this.positionListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public IphoneStyleDialog build() {
            final IphoneStyleDialog dialog = new IphoneStyleDialog(context);
            if (!TextUtils.isEmpty(title)) {
                dialog.titleTv.setText(title);
                dialog.titleTv.setVisibility(View.VISIBLE);
            }

            dialog.contentTv.setText(content);
            if (!TextUtils.isEmpty(contentColor)) {
                dialog.contentTv.setTextColor(Color.parseColor(contentColor));
            }

            // negative button
            int count = 0;
            if (!TextUtils.isEmpty(negativeText)) {
                count++;
                dialog.negativeTv.setText(negativeText);
                if (!TextUtils.isEmpty(negativeColor)) {
                    dialog.negativeTv.setTextColor(Color.parseColor(negativeColor));
                }
                dialog.negativeTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (negativeListener != null) {
                            negativeListener.onClick(v);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.negativeTv.setVisibility(View.VISIBLE);
            }
            // positive button
            if (!TextUtils.isEmpty(positionText)) {
                count += 2;
                dialog.positiveTv.setText(positionText);
                if (!TextUtils.isEmpty(positionColor)) {
                    dialog.positiveTv.setTextColor(Color.parseColor(positionColor));
                }
                dialog.positiveTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (positionListener != null) {
                            positionListener.onClick(v);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.positiveTv.setVisibility(View.VISIBLE);
            }

            switch (count) {
                case 1:
                    dialog.negativeTv.setBackgroundResource(R.drawable.selector_bg_iphone_dialog_btn);
                    break;
                case 2:
                    dialog.positiveTv.setBackgroundResource(R.drawable.selector_bg_iphone_dialog_btn);
                    break;
                default:// show all button
                    dialog.divider.setVisibility(View.VISIBLE);

            }

            dialog.setCancelable(isCancelable);
            return dialog;
        }

        public void show() {
            build().show();
        }
    }
}
