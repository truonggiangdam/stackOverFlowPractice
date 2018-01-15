package com.example.giangdam.stackoverflowpractice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.giangdam.stackoverflowpractice.adapter.LoadMoreLinearAdapter;
import com.example.giangdam.stackoverflowpractice.adapter.StudentListAdapter;
import com.example.giangdam.stackoverflowpractice.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cpu11326-local on 11/01/2018.
 */

public class MyAdapter extends LoadMoreLinearAdapter<Student> {

    MyAdapter(List<Student> datas, RecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        super(datas, recyclerView, onLoadMoreListener);
    }

    @Override
    protected void onBindLoadMoreViewHolder(LoadMoreViewHolder holder, int position) {
        ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
    }

    @Override
    protected void onBindItemViewHolder(ItemViewHolder holder, int position) {
        StudentViewHolder studentViewHolder = (StudentViewHolder) holder;
        Student student = getDatas().get(position);

        studentViewHolder.tvName.setText(student.getName());
        studentViewHolder.tvEmailId.setText(student.getEmailId());
    }

    @Override
    protected LoadMoreViewHolder onCreateViewLoadMore(ViewGroup parent) {
        View viewLoadMore = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.progressbar, parent, false);
        return new ProgressViewHolder(viewLoadMore);
    }

    @Override
    protected ItemViewHolder onCreateViewItem(ViewGroup parent) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_view, parent, false);

        return new StudentViewHolder(viewItem);
    }

    private class ProgressViewHolder extends LoadMoreViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View viewLoadMore) {
            super(viewLoadMore);
            progressBar = viewLoadMore.findViewById(R.id.progressBar);
        }
    }

    private class StudentViewHolder extends ItemViewHolder {
        public TextView tvName;
        public TextView tvEmailId;

        public StudentViewHolder(View viewItem) {
            super(viewItem);

            tvName = viewItem.findViewById(R.id.tvName);
            tvEmailId = viewItem.findViewById(R.id.tvEmailId);
        }
    }
}
