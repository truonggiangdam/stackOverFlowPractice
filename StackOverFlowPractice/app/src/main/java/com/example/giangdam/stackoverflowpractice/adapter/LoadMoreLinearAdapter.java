package com.example.giangdam.stackoverflowpractice.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.giangdam.stackoverflowpractice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cpu11326-local on 12/01/2018.
 */

public abstract class LoadMoreLinearAdapter<T> extends RecyclerView.Adapter{
    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROG = 0;
    private static final int VISIBLE_THRESHOLD = 5;

    protected List<T> mDatas;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean outOfData;


    protected LoadMoreLinearAdapter(List<T> datas, RecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        initData(datas);
        this.onLoadMoreListener = onLoadMoreListener;
        handleListener(recyclerView);

    }

    private void handleListener(RecyclerView recyclerView) {
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)
                    recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if(totalItemCount <= VISIBLE_THRESHOLD || outOfData)
                        return;

                    if(!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD )) {
                        // End has been reached
                        // Do something

                        if( onLoadMoreListener != null) {
                            showLoadMoreItem();
                            onLoadMoreListener.onLoadMore();
                        }

                        loading = true;
                    }
                }
            });
        }
    }

    private void showLoadMoreItem() {
        mDatas.add(null);
        notifyItemInserted(mDatas.size() - 1);
    }

    public void hideLoadMoreItem(){
        mDatas.remove(mDatas.size() - 1);
        notifyItemRemoved(mDatas.size());
        setLoaded();
    }


    void initData(List<T> datas) {
        if(datas != null) {
            mDatas = datas;
        }else {
            mDatas = new ArrayList<>();
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        if(viewType == VIEW_ITEM) {
            viewHolder = onCreateViewItem(parent);
        } else {
            viewHolder = onCreateViewLoadMore(parent);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position ){
        if(holder instanceof  LoadMoreLinearAdapter.ItemViewHolder) {
            onBindItemViewHolder((LoadMoreLinearAdapter.ItemViewHolder)holder,position);
        }else if (holder instanceof  LoadMoreLinearAdapter.LoadMoreViewHolder){
            onBindLoadMoreViewHolder((LoadMoreLinearAdapter.LoadMoreViewHolder)holder,position);
        }
    }

    protected abstract void onBindLoadMoreViewHolder(LoadMoreViewHolder holder, int position);

    protected abstract void onBindItemViewHolder(ItemViewHolder holder, int position);

    protected abstract LoadMoreViewHolder onCreateViewLoadMore(ViewGroup parent);

    protected abstract ItemViewHolder onCreateViewItem(ViewGroup parent);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void refreshData(){
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void notifyOutOfData() {
        outOfData = true;
    }

    public void notifyHasData() {
        outOfData = false;
        setLoaded();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    protected abstract class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View viewItem) {
            super(viewItem);
        }
    }

    public List<T> getDatas() {
        return mDatas;
    }

    protected abstract class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View viewLoadMore) {
            super(viewLoadMore);
        }
    }
}
