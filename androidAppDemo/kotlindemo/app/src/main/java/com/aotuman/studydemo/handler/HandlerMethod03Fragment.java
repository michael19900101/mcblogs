package com.aotuman.studydemo.handler;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aotuman.studydemo.R;

/**
 *  方式3：使用 Handler.post（）
 */
public class HandlerMethod03Fragment extends Fragment {

    private TextView mTextView;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_handler_method03, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_handlerMethod03Fragment_to_handlerMainFragment);
            }
        });

        mTextView = getView().findViewById(R.id.tv_msg);

        // 步骤1：在主线程中创建Handler实例
        mHandler = new Handler();

        // 步骤2：在工作线程中 发送消息到消息队列中 & 指定操作UI内容
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 通过psot（）发送，需传入1个Runnable对象
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 指定操作UI内容
                        mTextView.setText("执行了线程1的UI操作");
                    }

                });
            }
        }.start();
        // 步骤3：开启工作线程（同时启动了Handler）

        // 此处用2个工作线程展示
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("执行了线程2的UI操作");
                    }

                });
            }
        }.start();

    }
}
