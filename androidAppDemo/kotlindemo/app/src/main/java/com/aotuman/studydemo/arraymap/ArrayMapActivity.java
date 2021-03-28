package com.aotuman.studydemo.arraymap;

import android.app.Activity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.Nullable;

import com.aotuman.studydemo.R;

import java.util.HashMap;

public class ArrayMapActivity extends Activity {

    private static final int MAX = 10000;
    private static final String TAG = "ArrayMapActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arraymap_demo);

        findViewById(R.id.btn_addarraymap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAddSparseArray();
            }
        });

    }

    private void addHashMap() {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        long start_map = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            map.put(i, String.valueOf(i));
        }
        long map_memory = Runtime.getRuntime().totalMemory();
        long end_map = System.currentTimeMillis() - start_map;
        System.out.println("<---Map的插入时间--->" + end_map + "<---Map占用的内存--->" + map_memory);
    }

    private void addSparseArray() {
        SparseArray<String> sparse = new SparseArray<>();
        long start_sparse = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            sparse.put(i, String.valueOf(i));
        }
        long sparse_memory = Runtime.getRuntime().totalMemory();
        long end_sparse = System.currentTimeMillis() - start_sparse;
        System.out.println("<---Sparse的插入时间--->" + end_sparse + "<---Sparse占用的内存--->" + sparse_memory);
    }

    private void queryHashMap() {
        System.out.println("<------------- 数据量100000 Map 查询--------------->");
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < MAX; i++) {
            map.put(i, String.valueOf(i));
        }
        long start_time = System.currentTimeMillis();
        for (int i = 0; i < MAX; i = 100) {
            map.get(i);
        }
        long end_time = System.currentTimeMillis() - start_time;
        System.out.println(end_time);
    }

    private void querySparseArray() {
        System.out.println("<------------- 数据量100000 SparseArray 查询--------------->");
        SparseArray<String> sparse = new SparseArray<String>();
        for (int i = 0; i < 10000; i++) {
            sparse.put(i, String.valueOf(i));
        }
        long start_time = System.currentTimeMillis();
        for (int i = 0; i < MAX; i = 100) {
            sparse.get(i);
        }
        long end_time = System.currentTimeMillis() - start_time;
        System.out.println(end_time);
    }

    /**
     *  (ArrayMap does not avoid the auto-boxing problem)
     */
    private void addArrayMap() {
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        arrayMap.put("Key1", "Value1");
        arrayMap.put("Key2", "Value2");
        arrayMap.put("Key3", "Value3");

        for (int i = 0; i < arrayMap.size(); i++) {
            String key = arrayMap.keyAt(i);
            String value = arrayMap.valueAt(i);
        }
    }

    private void testAddSparseArray() {
        SparseArray<String> stringSparseArray = new SparseArray<>();
        stringSparseArray.put(1,"a");
        stringSparseArray.put(5,"e");
        stringSparseArray.put(4,"d");
        stringSparseArray.put(10,"h");
        stringSparseArray.put(2,null);
        stringSparseArray.put(11,"a");
        stringSparseArray.put(12,"e");
        stringSparseArray.put(13,"d");
        stringSparseArray.put(14,"h");
        stringSparseArray.put(15,"a");
        stringSparseArray.put(16,"e");
        stringSparseArray.put(17,"d");
        stringSparseArray.put(18,"h");
        stringSparseArray.put(19,"h");

        Log.d(TAG, "onCreate() called with: stringSparseArray = [" + stringSparseArray + "]");
    }

}
