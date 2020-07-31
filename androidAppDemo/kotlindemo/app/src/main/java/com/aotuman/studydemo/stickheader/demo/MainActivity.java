package com.aotuman.studydemo.stickheader.demo;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.aotuman.studydemo.R;
import com.aotuman.studydemo.stickheader.StickyLinearLayoutManager;
import com.aotuman.studydemo.stickheader.demo.model.HeaderItem;
import com.aotuman.studydemo.stickheader.demo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_main);

        recyclerView = findViewById(R.id.recycler_view);

        adapter = new RecyclerAdapter();
        adapter.setDataList(genDataList(0));
        StickyLinearLayoutManager layoutManager = new StickyLinearLayoutManager(this, adapter) {
            @Override
            public boolean isAutoMeasureEnabled() {
                return true;
            }

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                RecyclerView.SmoothScroller smoothScroller = new TopSmoothScroller(recyclerView.getContext());
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

            class TopSmoothScroller extends LinearSmoothScroller {

                TopSmoothScroller(Context context) {
                    super(context);
                }

                @Override
                public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                    return boxStart - viewStart;
                }
            }
        };
        layoutManager.elevateHeaders(5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        layoutManager.setStickyHeaderListener(new StickyLinearLayoutManager.StickyHeaderListener() {
            @Override
            public void headerAttached(View headerView, int adapterPosition) {
                Log.d("StickyHeader", "Header Attached : " + adapterPosition);
            }

            @Override
            public void headerDetached(View headerView, int adapterPosition) {
                Log.d("StickyHeader", "Header Detached : " + adapterPosition);
            }
        });

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addDataList(genDataList(adapter.getItemCount()));
            }
        }, 5000);
    }

    public static List<Object> genDataList(int start) {
        List<Object> items = new ArrayList<>();
        for (int i = start; i < 100 + start; i++) {
            if (i % 10 == 0) {
                if (i == 20 ) {
                    items.add(new HeaderItem("Header HeaderHeaderHeaderHeaderHeaderHeaderHeaderHeaderHeaderHeaderHeader" + i));
                } else if (i == 40 ){
                    items.add(new HeaderItem("通过 HeaderViewHolder 创建一个额外的悬浮HeaderView，当第一个分组的 Header 刚刚好贴边的时候，是无需展示这个悬浮HeaderView的。当第一个分组的 Header 往上滑了之后，" +
                            "那么就开始展示这个悬浮HeaderView。当第二个分组的 Header快要滑动到顶部的时候，悬浮HeaderView 要跟随逐渐往上退出。" + i));
                }else {
                    items.add(new HeaderItem("Header " + i));
                }
            } else {
                items.add(new Item("Item " + i, "description " + i));
            }
        }
        return items;
    }
}
