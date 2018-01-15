package com.example.giangdam.stackoverflowpractice.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.giangdam.stackoverflowpractice.model.Employee;

import java.util.List;

/**
 * Created by cpu11326-local on 15/01/2018.
 */

public class EmployeeListAdapter extends LoadMoreLinearAdapter<Employee> {


    protected EmployeeListAdapter(List<Employee> datas, RecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        super(datas, recyclerView, onLoadMoreListener);
    }

    @Override
    protected void onBindLoadMoreViewHolder(LoadMoreViewHolder holder, int position) {

    }

    @Override
    protected void onBindItemViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    protected LoadMoreViewHolder onCreateViewLoadMore(ViewGroup parent) {
        return null;
    }

    @Override
    protected ItemViewHolder onCreateViewItem(ViewGroup parent) {
        return null;
    }
}
