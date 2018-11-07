package com.fxyan.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fxYan
 */
@SuppressWarnings("ALL")
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    private final int ITEM_TYPE = 0;
    private final int EMPTY_TYPE = 99;
    private final int BASE_HEADER_TYPE = 100;
    private final int BASE_FOOTER_TYPE = 200;

    protected Context context;
    protected LayoutInflater inflater;
    protected int resId;

    protected List<T> dataSource;

    protected SparseArray<View> headers;
    protected SparseArray<View> footers;
    protected View emptyView;

    protected OnItemClickListener<T> onItemClickListener;
    protected View.OnClickListener onClickListener;

    protected boolean showHeaderFooterWhenEmpty;

    public BaseRecyclerAdapter(@NonNull Context context, @LayoutRes int resId) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.resId = resId;

        this.dataSource = new ArrayList<>();
        this.headers = new SparseArray<>();
        this.footers = new SparseArray<>();
    }

    public void clearDataSource() {
        dataSource.clear();
    }

    public void addAll(List<T> list) {
        dataSource.addAll(list);
    }

    public void addHeaderView(View header) {
        if (headers.indexOfValue(header) < 0) {
            headers.put(BASE_HEADER_TYPE + headers.size(), header);
        }
    }

    public void addFooterView(View footer) {
        if (footers.indexOfValue(footer) < 0) {
            footers.put(BASE_FOOTER_TYPE + footers.size(), footer);
        }
    }

    public int getHeaderCount() {
        return headers.size();
    }

    public int getFooterCount() {
        return footers.size();
    }

    public void setEmptyView(View view) {
        this.emptyView = view;
    }

    public void setShowHeaderFooterWhenEmpty(boolean showHeaderFooterWhenEmpty) {
        this.showHeaderFooterWhenEmpty = showHeaderFooterWhenEmpty;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (emptyView != null && dataSource.isEmpty() && !showHeaderFooterWhenEmpty) {
            return EMPTY_TYPE;
        }

        if (position < headers.size()) {
            return headers.keyAt(position);
        }

        int index = position - headers.size();
        int viewType;
        if (dataSource.isEmpty()) {
            if (emptyView == null) {
                viewType = footers.keyAt(index);
            } else if (index == 0) {
                viewType = EMPTY_TYPE;
            } else {
                index = index - 1;
                viewType = footers.keyAt(index);
            }
        } else {
            if (index < dataSource.size()) {
                viewType = ITEM_TYPE;
            } else {
                index = index - dataSource.size();
                viewType = footers.keyAt(index);
            }
        }
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // empty
        if (viewType == EMPTY_TYPE) {
            return new RecyclerView.ViewHolder(emptyView) {
            };
        }

        // header
        View view = headers.get(viewType);
        if (view != null) {
            return new RecyclerView.ViewHolder(view) {
            };
        }

        // footer
        view = footers.get(viewType);
        if (view != null) {
            return new RecyclerView.ViewHolder(view) {
            };
        }

        // item
        return createItemViewHolder(inflater.inflate(resId, parent, false));
    }

    protected abstract VH createItemViewHolder(View view);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE) {
            final int index = position - headers.size();
            final T obj = dataSource.get(index);
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClicked(obj, index);
                    }
                });
            }
            bindData((VH) holder, dataSource.get(index), index);
        }
    }

    protected abstract void bindData(@NonNull VH h, T obj, int position);

    @Override
    public int getItemCount() {
        if (emptyView != null && dataSource.isEmpty()) {
            if (showHeaderFooterWhenEmpty) {
                return headers.size() + 1 + footers.size();
            } else {
                return 1;
            }
        }
        return headers.size() + dataSource.size() + footers.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            final GridLayoutManager glm = (GridLayoutManager) lm;
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    // fix span count for header and footer and empty view
                    if (type == ITEM_TYPE) {
                        return 1;
                    }
                    return glm.getSpanCount();
                }
            });
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClicked(T obj, int position);
    }
}
