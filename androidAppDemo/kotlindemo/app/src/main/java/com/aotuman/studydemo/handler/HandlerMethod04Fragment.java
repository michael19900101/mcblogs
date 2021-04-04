package com.aotuman.studydemo.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aotuman.studydemo.R;

import java.lang.ref.WeakReference;

/**
 * 子线程创建Handler
 */
public class HandlerMethod04Fragment extends Fragment {

    private TextView mTextView;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_handler_method04, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_handlerMethod04Fragment_to_handlerMainFragment);
            }
        });

        mTextView = getView().findViewById(R.id.tv_msg);


        // 采用继承Thread类实现多线程演示
        new Thread() {
            @Override
            public void run() {
                // 步骤1：在子线程中 通过匿名内部类 创建Handler类对象
                mHandler = new Handler(Looper.getMainLooper()){
                    // 通过复写handlerMessage()从而确定更新UI的操作
                    @Override
                    public void handleMessage(Message msg) {
                        // 根据不同线程发送过来的消息，执行不同的UI操作
                        switch (msg.what) {
                            case 1:
                                mTextView.setText("执行了线程1的UI操作");
                                break;
                        }
                    }
                };

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 步骤3：创建所需的消息对象
                Message msg = Message.obtain();
                msg.what = 1; // 消息标识
                msg.obj = "A"; // 消息内存存放

                // 步骤4：在工作线程中 通过Handler发送消息到消息队列中
                mHandler.sendMessage(msg);
            }
        }.start();
        // 步骤5：开启工作线程（同时启动了Handler）




    }

    void test() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                // 步骤1：在子线程中 通过匿名内部类 创建Handler类对象
                mHandler = new Handler(){
                    // 通过复写handlerMessage()从而确定更新UI的操作
                    @Override
                    public void handleMessage(Message msg) {
                        // 根据不同线程发送过来的消息，执行不同的UI操作
                        switch (msg.what) {
                            case 1:
                                Log.d("jjjj","收到消息");
                                break;
                        }
                    }
                };
                Looper.loop();

                Log.d("jjjj","测试程序能否到达这里");

            }
        }.start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 步骤3：创建所需的消息对象
            Message msg = Message.obtain();
            msg.what = 1; // 消息标识
            msg.obj = "A"; // 消息内存存放

            // 步骤4：在工作线程中 通过Handler发送消息到消息队列中
            mHandler.sendMessage(msg);
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mHandler.getLooper().quit();
    }
}
