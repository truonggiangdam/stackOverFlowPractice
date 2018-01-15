package com.example.giangdam.stackoverflowpractice;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.giangdam.stackoverflowpractice.adapter.LoadMoreLinearAdapter;
import com.example.giangdam.stackoverflowpractice.model.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadMoreLinearAdapter.OnLoadMoreListener {

    RecyclerView recyclerView;
    MyAdapter adapter;
    List<Student> studentsList;

    SwipeRefreshLayout swipeRefreshLayout;

    Handler handler;

    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 1;
                //adapter.setLoaded();
                adapter.refreshData();
                adapter.notifyHasData();
                loadData();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        studentsList = new ArrayList<>();
        handler = new Handler();
        loadData();
        recyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(studentsList, recyclerView, this);

        recyclerView.setAdapter(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              adapter.notifyItemChanged(0);
            }
        }, 1000);

    }

    private void goToAnotherUsingAutovalue() {
        Intent intent = new Intent(MainActivity.this, AnotherActivity.class);
        startActivity(intent);
    }

    private void loadData() {
        for (int i = 1; i <= 20; i++) {
            studentsList.add(new Student("Student " + i, "androidstudent" + i + "@gmail.com"));
        }
    }


    private List<String> getDummyData() {
        List<String> dummy = new ArrayList<>();
        for(int i = 0; i < 100 ; i++) {
            dummy.add(String.valueOf(i));
        }

        return dummy;
    }


    public void logD(String message) {
        Log.d("GDD", message);
    }

    @Override
    public void onLoadMore() {
        if(count > 3){
            adapter.hideLoadMoreItem();
            adapter.notifyOutOfData();
            return;
        }


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               adapter.hideLoadMoreItem();

                // add item one by one
                int start = studentsList.size();
                int end = start + 20;

                for(int i = start + 1; i <= end; i++) {
                    studentsList.add(new Student("Student " + i,
                            "AndroidStudent" + i + "@gmail.com"));
                    adapter.notifyItemInserted(studentsList.size());
                }

                count ++ ;
            }
        }, 2000);
    }
}
