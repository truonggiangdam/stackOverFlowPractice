package com.example.giangdam.stackoverflowpractice.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.giangdam.stackoverflowpractice.R;
import com.example.giangdam.stackoverflowpractice.listener.OnLoadMoreListener;
import com.example.giangdam.stackoverflowpractice.model.Student;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by cpu11326-local on 12/01/2018.
 */

public class StudentListAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Student> studentList;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public StudentListAdapter(List<Student> students, RecyclerView recyclerView) {
        studentList = students;

        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)
                    recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold )) {
                        // End has been reached
                        // Do something

                        if( onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }

                        loading = true;
                    }
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        return studentList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        if(viewType == VIEW_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_view, parent, false);

            viewHolder = new StudentViewHolder(viewItem);
        } else {
            View viewLoadMore = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);
            viewHolder = new ProgressViewHolder(viewLoadMore);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  StudentViewHolder) {
            StudentViewHolder studentViewHolder = (StudentViewHolder) holder;
            Student student = studentList.get(position);

            studentViewHolder.tvName.setText(student.getName());
            studentViewHolder.tvEmailId.setText(student.getEmailId());
        }else {
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }


    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    private class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvEmailId;

        public StudentViewHolder(View viewItem) {
            super(viewItem);

            tvName = viewItem.findViewById(R.id.tvName);
            tvEmailId = viewItem.findViewById(R.id.tvEmailId);
        }
    }


    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View viewLoadMore) {
            super(viewLoadMore);
            progressBar = viewLoadMore.findViewById(R.id.progressBar);
        }
    }
}
