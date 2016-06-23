package com.Jsu.framework.recyclerview.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.Jsu.framework.R;
import com.Jsu.framework.recyclerview.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by su on 16-6-21.
 */
public class TestRvActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_test);

        final RecyclerView mRecycler = (RecyclerView) findViewById(R.id.rv_test);
        mRecycler.setLayoutManager(new LinearLayoutManager(TestRvActivity.this));
        final QuickAdapter quickAdapter = new QuickAdapter(getData());
        quickAdapter.openLoadAnimation();
        quickAdapter.openLoadMore(3, true);
        quickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        quickAdapter.notifyDataChangedAfterLoadMore(getData(), true);
                    }
                }, 1000);
            }
        });

        mRecycler.setAdapter(quickAdapter);
    }

    private List getData() {
        List<TestBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new TestBean(i));
        }
        return list;
    }
}
