package com.example.zhang.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewDemo extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter();
        adapter.initList();
        ArrayList<Map<String, String >> list = adapter.getList();


        recyclerView.setAdapter(adapter);
        for (int i = 0; i < 50; i++){
            Map<String, String> map = new HashMap<>();
            map.put("name", "No" + i);
            map.put("detail", "<p>hello world</p><br><a href='baidu.com'>baidu</a>");
            list.add(map);
        }
    }

}
